/*
 * setup.c
 * -----------------------------
 * Defines the setup function, which initializes all of the arrays and variables necessary.
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

#include "setup.h"
#include <android/log.h>

void gameSetup()
{
    int i, j;
    loq = MAX_POINTS;
    unsigned char backgroundRed = cAtmosphere->backgroundRed;
    unsigned char backgroundGreen = cAtmosphere->backgroundGreen;
    unsigned char backgroundBlue = cAtmosphere->backgroundBlue;

    if (!cElement)
    {
        cElement = elements[NORMAL_ELEMENT];
    }

    //Unset all the particles
    for(i = 0; i < MAX_POINTS; i++)
    {
        avail[i] = i;
        a_set[i] = FALSE;
    }

    //Clear allCoords and our pixels array
    //TODO: This could be faster with pointers
    for(i = 0; i < stupidTegra; i++)
    {
        for(j = 0; j < workHeight; j++)
        {
            if(i < workWidth)
            {
                allCoords[getIndex(i, j)] = -1;
            }
            colors[3 * getColorIndex(i, j)] = backgroundRed;
            colors[3 * getColorIndex(i, j) + 1] = backgroundGreen;
            colors[3 * getColorIndex(i, j) + 2] = backgroundBlue;
        }
    }
}

//Set up all the variable sized arrays
void arraySetup()
{
    //Make sure everything is deallocated
    free(colors);
    free(colorsFrameBuffer);
    free(allCoords);

    //Allocate memory
    colors = malloc(3 * stupidTegra * workHeight * sizeof(char));
    colorsFrameBuffer = malloc(3 * stupidTegra * workHeight * sizeof(char));
    allCoords = malloc(workWidth * workHeight * sizeof(int*)); //Two dimensional array, so when calling use allcoords[getIndex(x, y)];
}

void atmosphereSetup()
{
    free(cAtmosphere);
    cAtmosphere = (struct Atmosphere*) malloc (sizeof(struct Atmosphere));

    cAtmosphere->heat = DEFAULT_ATMOSPHERE_TEMP;
    cAtmosphere->gravity = DEFAULT_ATMOSPHERE_GRAVITY;
    cAtmosphere->backgroundRed = DEFAULT_RED;
    cAtmosphere->backgroundGreen = DEFAULT_GREEN;
    cAtmosphere->backgroundBlue = DEFAULT_BLUE;
    cAtmosphere->borderLeft = DEFAULT_BORDER_LEFT;
    cAtmosphere->borderTop = DEFAULT_BORDER_TOP;
    cAtmosphere->borderRight = DEFAULT_BORDER_RIGHT;
    cAtmosphere->borderBottom = DEFAULT_BORDER_BOTTOM;
}

void elementSetup()
{
    numElements = NUM_BASE_ELEMENTS; //Changed later

    //Free and reallocate the elements array
    free(elements);

    elements = malloc(numElements * sizeof(struct Element*)); // we  will realloc later for custom elements if needed

    //Allocate and initialize all the elements
    struct Element* tempElement;
    int i, j;
    for(i = 0; i < numElements; i++)
    {
        if(i < NUM_BASE_ELEMENTS)
        {
            tempElement = (struct Element*) malloc(sizeof(struct Element));
            elements[i] = tempElement;
            tempElement->index = i;
            tempElement->name = baseName[i];
            tempElement->red = baseRed[i];
            tempElement->green = baseGreen[i];
            tempElement->blue = baseBlue[i];
            tempElement->fallVel = baseFallVel[i];
            tempElement->density = baseDensity[i];
            tempElement->state = baseState[i];
            memcpy(tempElement->specials, baseSpecial[i], MAX_SPECIALS * sizeof(int));
            memcpy(tempElement->specialVals, baseSpecialValue[i], MAX_SPECIALS * sizeof(int));
            tempElement->inertia = baseInertia[i];
            tempElement->startingTemp = baseStartingTemp[i];
            tempElement->highestTemp = baseHighestTemp[i];
            tempElement->lowestTemp = baseLowestTemp[i];
        }
    }

    cElement = elements[NORMAL_ELEMENT];

    //Resolve heat pointers
    for(i = 0; i < numElements; i++)
    {
        if(i < NUM_BASE_ELEMENTS)
        {
            elements[i]->lowerElement = elements[baseLowerElement[i]];
            elements[i]->higherElement = elements[baseHigherElement[i]];
        }
    }
    loadCustomElements();
}

void particleSetup()
{
    //TODO decrement instead
    int i;
    for(i = 0; i < MAX_POINTS; i++)
    {
        a_specialVals[i] = (int*) malloc(MAX_SPECIALS * sizeof(int));
    }
}
