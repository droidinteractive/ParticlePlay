/*
 * elementproperties.h
 * ----------------------------------------
 * Declares all the arrays that define the element properties.
 * When adding a new element, you need to add it to all of these arrays as well.
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

#ifndef ELEMENTPROPERTIES_H_INCLUDED
#define ELEMENTPROPERTIES_H_INCLUDED

//Include the global macros
#include "macros.h"
//Include the app variables
#include "app.h"

// Define the names of the elements
extern char* baseName[];

//Define the color of the element
extern unsigned char baseRed[];
extern unsigned char baseGreen[];
extern unsigned char baseBlue[];

//Define each base element's fall velocity
extern signed char baseFallVel[];

//Defines the density of each base element
extern char baseDensity[];

//Defines the state of each base element (0 = Solid, 1 = Liquid, 2 = Gaseous)
extern char baseState[];

//Defines the special for each base element
extern int baseSpecial[][MAX_SPECIALS];

//Defines the special value for each base element
extern int baseSpecialValue[][MAX_SPECIALS];

//Defines the inertia of each element
extern char baseInertia[];

//Defines the starting temp of the element (-1 = atmosphere)
extern char baseStartingTemp[];

//Defines the highest and lowest temps that the element exists at
extern char baseHighestTemp[];
extern char baseLowestTemp[];

//Defines the higher and lower elements to change to outside of the correct temp range
extern char baseHigherElement[];
extern char baseLowerElement[];

#endif //!ELEMENTPROPERTIES_H_INCLUDED
