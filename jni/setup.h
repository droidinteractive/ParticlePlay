/*
 * setup.h
 * -----------------------------
 * Declares the rsetup function, which initializes all of the arrays and variables necessary. Also includes necessary headers.
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

#ifndef SETUP_H_INCLUDED
#define SETUP_H_INCLUDED

//Include the global variables
#include "app.h"
//Include the global macros
#include "macros.h"
//Include the Element properties (yes this is obvious)
#include "elementproperties.h"
//Include the free function
#include <stdlib.h>
//Include saveload for custom element loading
#include "saveload.h"

//Set up the workspace
void gameSetup(void);
//Set up the variable sized arrays
void arraySetup(void);
//Create all the elements
void elementSetup(void);
//Create all the particle structs
void particleSetup(void);

#endif //!SETUP_H_INCLUDED
