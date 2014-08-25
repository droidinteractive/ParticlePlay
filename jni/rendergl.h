/*
 * gl.h
 * -----------------------------
 * Declares the gl rendering and initialization functions appInit, appDeinit, and appRender.
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

#ifndef GL_H_INCLUDED
#define GL_H_INCLUDED

//Include the Gl importation functions
#include "importgl.h"
//Include the global variables
#include "app.h"
//Include the update function
#include "update.h"
//Include pthread functions
#include <pthread.h>


void glInit(void);
void glRender(void);

#endif //!GL_H_INCLUDED
