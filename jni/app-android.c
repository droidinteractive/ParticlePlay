/*
 * app-android.c
 * -----------------------------
 * Contains the entire API for our JNI code. Any access to the native code from Java will be made using these accessible functions.
 *
 * Copyright (c) 2010 Ragdoll Games
 * Copyright (c) 2010-2014 Droid Interactive
 * Copyright (c) 2010-2014 IDKJava Team
 *
 * This file is part of Particle Play.
 *
 * Particle Play is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Particle Play is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Particle Play. If not, see <http://www.gnu.org/licenses/>.
 */

#include <jni.h>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>

#include <android/log.h>

//Include the global variables
#include "app.h"
//Include the global macros
#include "macros.h"
//Include the initializing function
#include "setup.h"
//Include the element characteristics file
#include "elementproperties.h"
//Include the collisions data file
#include "collisions.h"
//Include the saving and loading functions
#include "saveload.h"
//Include the rendering functions
#include "rendergl.h"
//Include pthread functions
#include <pthread.h>

//Called from SandViewRenderer
void Java_com_droidinteractive_particleplay_game_SandViewRenderer_nativeResize(JNIEnv* env, jobject this, jint width, jint height)
{
    screenWidth = width;
    screenHeight = height;
    workWidth = screenWidth / zoomFactor;
    workHeight = screenHeight / zoomFactor;

    //Finds nearest power of 2 to work Width
    stupidTegra = 1;
    while((stupidTegra = stupidTegra << 1) < workWidth);
    dimensionsChanged = TRUE;

    arraySetup();
    glInit();
    gameSetup();
    startUpdateThread();
}
// TODO: I think this should be removed, but I don't have the time to figure it out right now
void Java_com_droidinteractive_particleplay_game_SandViewRenderer_nativeLoadState(JNIEnv* env, jobject this, jboolean shouldLoadDemo)
{
    char loadLoc[256];

    strcpy(loadLoc, ROOT_FOLDER);
    strcat(loadLoc, SAVES_FOLDER);
    if(shouldLoadDemo)
    {
        strcat(loadLoc, DEMO_SAVE);
    }
    else
    {
        strcat(loadLoc, TEMP_SAVE);
    }
    strcat(loadLoc, SAVE_EXTENSION);
    loadState(loadLoc);
}
void Java_com_droidinteractive_particleplay_game_SandViewRenderer_nativeRender(JNIEnv* env, jobject this)
{
    glRenderThreaded();
}

//Save/load functions
char Java_com_droidinteractive_particleplay_game_SaveManager_saveState(JNIEnv* env, jobject this, jbyteArray saveLoc)
{
#ifdef USE_PROFILING
    // Stop profiling
    // This saves to /sdcard/gmon.out
    moncleanup();
#endif

    jsize len = (*env)->GetArrayLength(env, saveLoc);
    jbyte* saveLoc2 = (jbyte*) malloc(len * sizeof(jbyte));
    (*env)->GetByteArrayRegion(env, saveLoc, 0, len, saveLoc2);
    char* saveLoc3 = (char*) malloc((len+1) * sizeof(char));
    int i;
    char buffer[100];
    for(i = 0; i < len; i++)
    {
        saveLoc3[i] = saveLoc2[i];
    }
    saveLoc3[len] = 0;

    if(saveTempToFile(saveLoc3))
    {
        return TRUE;
    }

    return FALSE;
}
char Java_com_droidinteractive_particleplay_game_SaveManager_loadState(JNIEnv* env, jobject this, jbyteArray loadLoc)
{
    jsize len = (*env)->GetArrayLength(env, loadLoc);
    jbyte* loadLoc2 = (jbyte*) malloc(len * sizeof(jbyte));
    (*env)->GetByteArrayRegion(env, loadLoc, 0, len, loadLoc2);
    char* loadLoc3 = (char*) malloc((len+1) * sizeof(char));
    int i;
    char buffer[100];
    for(i = 0; i < len; i++)
    {
        loadLoc3[i] = loadLoc2[i];
    }
    loadLoc3[len] = 0;

    if(loadFileToTemp(loadLoc3))
    {
        return TRUE;
    }

    return FALSE;
}
char Java_com_droidinteractive_particleplay_MainActivity_saveTempState(JNIEnv* env, jobject this)
{
    char saveLoc[256];
    strcpy(saveLoc, ROOT_FOLDER);
    strcat(saveLoc, SAVES_FOLDER);
    strcat(saveLoc, TEMP_SAVE);
    strcat(saveLoc, SAVE_EXTENSION);
    return saveState(saveLoc);
}
char Java_com_droidinteractive_particleplay_MainActivity_removeTempSave(JNIEnv* env, jobject this)
{
    return removeTempSave();
}
char Java_com_droidinteractive_particleplay_MainActivity_loadDemoState(JNIEnv* env, jobject this)
{
    char loadLoc[256];
    strcpy(loadLoc, ROOT_FOLDER);
    strcat(loadLoc, SAVES_FOLDER);
    strcat(loadLoc, DEMO_SAVE);
    strcat(loadLoc, SAVE_EXTENSION);
    return loadState(loadLoc);
}

//General utility functions
void Java_com_droidinteractive_particleplay_MainActivity_nativeInit(JNIEnv* env, jobject this, jstring udidString, jint jversionCode)
{
    // Set some global variables
    int jstringLen = (*env)->GetStringUTFLength(env, udidString);
    if (jstringLen > MAX_UDID_LENGTH-1)
    {
        jstringLen = MAX_UDID_LENGTH-1;
    }
    (*env)->GetStringUTFRegion(env, udidString, 0, jstringLen, udid);
    udid[jstringLen] = 0;
    versionCode = jversionCode;

    // Initialization
    importGLInit();
    atmosphereSetup();
    elementSetup();
    particleSetup();

    // Profiling
#ifdef USE_PROFILING
    monstartup("libparticleplay.so");
#endif
}
void Java_com_droidinteractive_particleplay_MainActivity_clearScreen(JNIEnv* env, jobject this)
{
    shouldClear = TRUE;
}

//Setter functions
void Java_com_droidinteractive_particleplay_preferences_Preferences_setBorderState(JNIEnv* env, jobject this, jboolean leftBorderState, jboolean topBorderState, jboolean rightBorderState, jboolean bottomBorderState)
{
    cAtmosphere->borderLeft = leftBorderState;
    cAtmosphere->borderTop = topBorderState;
    cAtmosphere->borderRight = rightBorderState;
    cAtmosphere->borderBottom = bottomBorderState;
}
void Java_com_droidinteractive_particleplay_preferences_Preferences_setAccelState(JNIEnv* env, jobject this, jboolean accelState)
{
    accelOn = (char) accelState;
}
void Java_com_droidinteractive_particleplay_preferences_Preferences_setFlippedState(JNIEnv* env, jobject this, jboolean flippedState)
{
    flipped = (char) flippedState;
}
void Java_com_droidinteractive_particleplay_preferences_Preferences_setBackgroundColor(JNIEnv* env, jobject this, jchar redValue, jchar greenValue, jchar blueValue)
{
    //Set the eraser color to the background color, used as the reference whenever background color is needed
    cAtmosphere->backgroundRed = redValue;
    cAtmosphere->backgroundGreen = greenValue;
    cAtmosphere->backgroundBlue = blueValue;

    char buffer[100];
    sprintf(buffer, "blue: %d", blueValue);
}
void Java_com_droidinteractive_particleplay_preferences_Preferences_setAtmosphereTemp(JNIEnv* env, jobject this, jchar temp)
{
    cAtmosphere->heat = temp;
}
void Java_com_droidinteractive_particleplay_preferences_Preferences_setAtmosphereGravity(JNIEnv* env, jobject this, jfloat gravity)
{
    cAtmosphere->gravity = gravity;
}
void Java_com_droidinteractive_particleplay_preferences_Preferences_setZoom(JNIEnv* env, jobject this, jint zoom)
{
    if(zoom != zoomFactor)
    {
        zoomFactor = zoom;
        zoomChanged = TRUE;
    }
}
void Java_com_droidinteractive_particleplay_MainActivity_setPlayState(JNIEnv* env, jobject this, jboolean playState)
{
    play = (char) playState;
}
void Java_com_droidinteractive_particleplay_MainActivity_setElement(JNIEnv* env, jobject this, jchar element)
{
    cElement = elements[element];
}
void Java_com_droidinteractive_particleplay_MainActivity_setBrushSize(JNIEnv* env, jobject this, jchar brushSizeValue)
{
    brushSize = brushSizeValue;
}
void Java_com_droidinteractive_particleplay_game_SandView_setMouseLocation(JNIEnv* env, jobject this, jchar state, jint x, jint y)
{
    pthread_mutex_lock(&mouse_mutex);

    // Translate x and y coords to zoomed coords
    mouseX = x / zoomFactor;
    mouseY = y / zoomFactor;

    if (state != 2 && fingerDown != state)
    {
        fingerDown = state;

        // If we're got ACTION_DOWN, then we don't want to continue drawing
        // from previous mouse location.
        if (fingerDown)
        {
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    pthread_mutex_unlock(&mouse_mutex);
}

//Getter functions
void Java_com_droidinteractive_particleplay_MainActivity_setFilterMode(JNIEnv* env, jobject this, jchar mode)
{
	filterType = mode;
}
char Java_com_droidinteractive_particleplay_MainActivity_getElement(JNIEnv* env, jobject this)
{
    return cElement->index;
}
char Java_com_droidinteractive_particleplay_MainActivity_getElementRed(JNIEnv* env, jobject this, int i)
{    
    return elements[i]->red;
}
char Java_com_droidinteractive_particleplay_MainActivity_getElementGreen(JNIEnv* env, jobject this, int i)
{
    return elements[i]->green;
}
char Java_com_droidinteractive_particleplay_MainActivity_getElementBlue(JNIEnv* env, jobject this, int i)
{
    return elements[i]->blue;
}
jstring Java_com_droidinteractive_particleplay_MainActivity_getElementInfo(JNIEnv* env, jobject this, int i)
{
#define BUFFER_SIZE 1000
    char buffer[BUFFER_SIZE];
    int length = snprintf(buffer, BUFFER_SIZE,
                          "%s\n" // Name
                          "%d\n" // State
                          "%d\n" // Starting temp
                          "%d\n" // Lowest temp
                          "%d\n" // Highest temp
                          "%d\n" // Lower element index
                          "%d\n" // Highest element index
                          "%d\n" // Red
                          "%d\n" // Green
                          "%d\n" // Blue
                          "%d\n" // Density
                          "%d\n" // fallVel
                          "%d\n", // inertia
                          baseName[i],
                          (int)baseState[i],
                          (int)baseStartingTemp[i],
                          (int)baseLowestTemp[i],
                          (int)baseHighestTemp[i],
                          (int)baseLowerElement[i],
                          (int)baseHigherElement[i],
                          (int)baseRed[i],
                          (int)baseGreen[i],
                          (int)baseBlue[i],
                          (int)baseDensity[i],
                          (int)baseFallVel[i],
                          (int)baseInertia[i]);
    int j;
    for (j = 0; j < NUM_BASE_ELEMENTS-NORMAL_ELEMENT; j++)
    {
        length += snprintf(&(buffer[length]), BUFFER_SIZE-length,
                           "%d\n", // Collision number j
                           collision[i][j+NORMAL_ELEMENT]);
    }

    for (j = 0; j < MAX_SPECIALS; j++)
    {
        length += snprintf(&(buffer[length]), BUFFER_SIZE-length,
                           "%d %d\n", // Special number j
                           baseSpecial[i][j],
                           baseSpecialValue[i][j]);
    }

    jstring retVal;
    retVal = (*env)->NewStringUTF(env, buffer);
    return retVal;
}
int Java_com_droidinteractive_particleplay_MainActivity_getMaxSpecials(JNIEnv* env, jobject this)
{
    return MAX_SPECIALS;
}

//Accelerometer related
void Java_com_droidinteractive_particleplay_MainActivity_setXGravity(JNIEnv* env, jobject this, float xGravityIn)
{
    xGravity = xGravityIn;
}
void Java_com_droidinteractive_particleplay_MainActivity_setYGravity(JNIEnv* env, jobject this, float yGravityIn)
{
    yGravity = yGravityIn;
}

//Custom elements related
char Java_com_droidinteractive_particleplay_game_CustomElementManager_loadCustomElement(JNIEnv* env, jobject this, char* loadLoc)
{
    return loadCustomElement(loadLoc);
}



