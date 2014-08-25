/*
 * app.h
 * --------------------------------
 * Contains all the extern declarations for our variables, so that they are essentially
 * global external variables accessible from all files necessary.
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

#ifndef APP_H_INCLUDED
#define APP_H_INCLUDED

#ifdef __cplusplus
extern "C" {
#endif

//Include the global macros
#include "macros.h"
//Include the pthread functions
#include <pthread.h>

/*
 * FUNCTIONS
 */

    inline int getIndex(int x, int y);
    inline int getColorIndex(int x, int y);

/*
 * STRUCTS
 */
 
    struct Element
    {
        //Index
        unsigned char index;
        //Name
        char* name;

        //Dealing with states
        char state;
        char startingTemp, lowestTemp, highestTemp;
        struct Element* lowerElement;
        struct Element* higherElement;

        //Dealing with drawing
        char red, green, blue;

        //Properties
        int specials[MAX_SPECIALS];
        int specialVals[MAX_SPECIALS];
        char* collisions;  // Only for customs
        char base; //Only for customs
        char density;
        signed char fallVel;
        char inertia;
    };

    struct Atmosphere
    {
        char heat;
        char gravity;

        unsigned char backgroundRed, backgroundGreen, backgroundBlue;

        char borderLeft, borderTop, borderRight, borderBottom;
    };

/*
 * VARIABLES
 */

    //Variables to track the user/app version
    extern char udid[];
    extern int versionCode;

    //An array of all the elements
    extern struct Element** elements;

    //The number of elements available
    extern unsigned char numElements;

    extern char a_set[];
    extern float a_x[];
    extern float a_y[];
    extern float a_oldX[];
    extern float a_oldY[];
    extern short a_xVel[];
    extern short a_yVel[];
    extern char a_heat[];
    extern int* a_specialVals[];
    extern struct Element* a_element[];
    extern char a_frozen[];
    extern char a_hasMoved[];

    //A stack of available particles
    extern int avail[];

    //Points to the index AFTER the top of the stack
    extern int loq;
    
    //Current element selected
    extern struct Element* cElement;

    //Atmosphere in use
    extern struct Atmosphere* cAtmosphere;

    //State variables
    extern char play;
    extern char flipped;
    extern char fingerDown;
    extern char accelOn;
    extern char dimensionsChanged;
    extern char zoomChanged;
    extern int shouldClear;

    extern unsigned char brushSize;
    extern unsigned char zoomFactor;

    extern unsigned char filterType;

    //A map of all the points (a two-dimensional variable-size array)
    extern int* allCoords;

    //Mouse positions
    extern short mouseX;
    extern short mouseY;

    //Old mouse positions
    extern short lastMouseX;
    extern short lastMouseY;

    extern int randOffset;

    //Array for bitmap drawing
    extern unsigned char* colors;
    extern unsigned char* colorsFrameBuffer;

    //Screen dimensions
    extern int screenWidth;
    extern int screenHeight;

    //Workspace dimensions
    extern int workWidth;
    extern int workHeight;

    //Nearest power of 2 to workWidth - needed due to stupid Tegra 2.
    extern int stupidTegra;

    //Collision matrix
    extern char collision[NUM_BASE_ELEMENTS][NUM_BASE_ELEMENTS];
    extern char reciprocals[NUM_COLLISIONS];

    //Set when a mouse update is requested, unset when updated
    extern char shouldUpdateMouse;

    //Gravity values
    extern float xGravity;
    extern float yGravity;

/*
 * THREADS
 */

    extern int threadsInitialized;
    extern int bufferFree;
    extern int frameReady;

    extern pthread_mutex_t update_mutex;
    extern pthread_mutex_t frame_ready_mutex;
    extern pthread_cond_t frame_ready_cond;
    extern pthread_mutex_t buffer_free_mutex;
    extern pthread_cond_t buffer_free_cond;

    extern pthread_mutex_t mouse_mutex;

#ifdef __cplusplus
}
#endif
#endif // !APP_H_INCLUDED
