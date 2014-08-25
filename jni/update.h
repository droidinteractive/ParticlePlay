/*
 * update.h
 * -----------------------------------
 * Declares the function UpdateView(), which is called every frame to update all the particles' positions. Also provides all
 * the necessary includes and defines.
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

#ifndef UPDATE_H_INCLUDED
#define UPDATE_H_INCLUDED

#include <stdlib.h>
#include <math.h>

//Include the global variables
#include "app.h"
//Include the points functions
#include "points.h"
//Include the collision function
#include "collide.h"
//Include the clear funtion (rsetup)
#include "setup.h"

void startUpdateThread();
void killUpdateThread();

#endif //!UPDATE_H_INCLUDED
