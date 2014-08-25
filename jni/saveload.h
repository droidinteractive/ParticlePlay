/*
 * saveload.h
 * --------------------------
 * Declares the function definitions for saver and loader, the two functions which save and
 * load an element setup. Also defines macros for file locations, and includes needed headers.
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

#ifndef SAVELOAD_H_INCLUDED
#define SAVELOAD_H_INCLUDED

//File/folder locations
#define ROOT_FOLDER "/sdcard/droidinteractive/particleplay/"
#define SAVES_FOLDER "saves/"
#define ELEMENTS_FOLDER "particles/"
#define ATMOSPHERES_FOLDER "atmospheres/"
#define TEMP_SAVE "temp"
#define DEMO_SAVE "pplay"
#define LIST_SAVE "partList"
//File extensions
#define LIST_EXTENSION ".lst"
#define SAVE_EXTENSION ".sav"
#define ELEMENT_EXTENSION ".par"
#define ATMOSPHERE_EXTENSION ".atm"
//Saveload version code (update this when a change occurs, so we can identify old formats)
#define SAVELOAD_VERSION_CODE "$$VC3$$"

//Include the FILE type
#include <stdio.h>
//Include the date and time functions
#include <time.h>
//Include the global variables
#include "app.h"
//Include the global macros
#include "macros.h"
//Include points functions
#include "points.h"
//Include the initializer function
#include "setup.h"
//Include the string functions
#include <string.h>
//Include the directory functions
#include <dirent.h>
#include <sys/stat.h>
//Include error reporting functions
#include <errno.h>

char saveState(char* saveLoc);
char saveStateLogic(FILE* fp);
char loadState(char* loadLoc);
char loadStateLogicV0(FILE* fp);
char loadStateLogicV1(FILE* fp);
char removeTempSave(void);
char saveCustomElement(struct Element* createdCustomElement);
char loadCustomElement(char* loadLoc);
unsigned long hashElement(struct Element* element);
char* stringifyElement(struct Element* element);
unsigned long hashStr(unsigned char* str);
int findElementFromHash(unsigned long hash);

#endif //!SAVELOAD_H_INCLUDED
