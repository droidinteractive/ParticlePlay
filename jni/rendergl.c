/*
 * gl.c
 * --------------------------
 * Defines the gl rendering and initialization functions appInit, appDeinit, and appRender.
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

#include "rendergl.h"
#include <android/log.h>

#ifndef NDEBUG
#define LOGGING 0 // Debug
#else
#define LOGGING 0 // Release
#endif

#if LOGGING
// Timing of frames
#include <sys/time.h>

static struct timeval time1;
#endif

unsigned int textureID;

float vertices[] =
{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
float texture[] =
{0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
unsigned char indices[] =
{0, 1, 3, 0, 3, 2};
int texWidth, texHeight, stupidTegra;


void glInit()
{
	//Set some properties
	glShadeModel(GL_FLAT);
	glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST);

	//Generate the new texture
	glGenTextures(1, &textureID);
	//Bind the texture
	glBindTexture(GL_TEXTURE_2D, textureID);

	//Enable 2D texturing
	glEnable(GL_TEXTURE_2D);
	//Disable depth testing
	glDisable(GL_DEPTH_TEST);

	//Enable the vertex and coord arrays
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);


	//Set tex params
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

	//Set up texWidth and texHeight
	texWidth = 1;
	texHeight = 1;
	while((texWidth = texWidth << 1) < (screenWidth-screenWidth%zoomFactor));
	while((texHeight = texHeight << 1) < (screenHeight-screenHeight%zoomFactor));

	//Allocate the dummy array
	char* emptyPixels = malloc(3 * texWidth*texHeight * sizeof(char));
	//Generate the tex image
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, texWidth, texHeight, 0, GL_RGB, GL_UNSIGNED_BYTE, emptyPixels);
	//Free the dummy array
	free(emptyPixels);

	//Set the pointers
	glVertexPointer(2, GL_FLOAT, 0, vertices);
	glTexCoordPointer(2, GL_FLOAT, 0, texture);
}

void glRender()
{
#if LOGGING
    struct timeval time2;
    struct timeval time3;
    char buffer[20];
    double useconds;

    // TODO: First frame is wrong. We probably don't care...
    gettimeofday(&time2, NULL);
    useconds = (time2.tv_sec - time1.tv_sec)*1000000 + time2.tv_usec - time1.tv_usec;
    snprintf(buffer, 20, "aFPS: %f", 1000000/useconds);
    __android_log_write(ANDROID_LOG_INFO, "Particle Play", buffer);
    time1.tv_sec = time2.tv_sec;
    time1.tv_usec = time2.tv_usec;
#endif

	if(dimensionsChanged)
	{
		__android_log_write( ANDROID_LOG_INFO, "Particle Play", "dimensions changed" );
		vertices[2] = (float) screenWidth;
		vertices[5] = (float) screenHeight;
		vertices[6] = (float) screenWidth;
		vertices[7] = (float) screenHeight;

		texture[2] = (float) workWidth/texWidth;
		texture[5] = (float) workHeight/texHeight;
		texture[6] = (float) workWidth/texWidth;
		texture[7] = (float) workHeight/texHeight;

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		if (!flipped)
		{
			glOrthof(0, (float)screenWidth, (float)screenHeight, 0, -1.0f, 1.0f); //--Device
		}
		else
		{
			glOrthof(0, (float)screenWidth, 0, (float)-screenHeight, -1.0f, 1.0f); //--Emulator
		}

		dimensionsChanged = FALSE;
		zoomChanged = FALSE;
	}
	else if(zoomChanged)
	{
		texture[2] = (float) workWidth/texWidth;
		texture[5] = (float) workHeight/texHeight;
		texture[6] = (float) workWidth/texWidth;
		texture[7] = (float) workHeight/texHeight;

		zoomChanged = FALSE;
	}

	//Sub the work portion of the tex(~.025s -- Droid)
#if LOGGING
    gettimeofday(&time2, NULL);
#endif
	glTexSubImage2D(GL_TEXTURE_2D, 0, 0, 0, stupidTegra, workHeight, GL_RGB, GL_UNSIGNED_BYTE, colorsFrameBuffer);
#if LOGGING
    gettimeofday(&time3, NULL);
    useconds = (time3.tv_sec - time2.tv_sec)*1000000 + time3.tv_usec - time2.tv_usec;
    snprintf(buffer, 20, "tFPS: %f", 1000000/useconds);
    __android_log_write(ANDROID_LOG_INFO, "Particle Play", buffer);
#endif

    //Actually draw the rectangle with the text on it (~.015s -- Droid)
    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_BYTE, indices);
}


void glRenderThreaded()
{
    // Synchronization
    pthread_mutex_lock(&frame_ready_mutex);
    while (!frameReady)
    {
        pthread_cond_wait(&frame_ready_cond, &frame_ready_mutex);
    }
    frameReady = FALSE;
    pthread_mutex_unlock(&frame_ready_mutex);

    glRender();
    pthread_mutex_lock(&buffer_free_mutex);
    if (!bufferFree)
    {
        bufferFree = TRUE;
        pthread_cond_signal(&buffer_free_cond);
    }
    pthread_mutex_unlock(&buffer_free_mutex);
}
