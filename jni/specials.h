/*
 * specials.h
 * --------------------------
 * Header file for specials.c. Contains functions and declarations related to special values and functions on the various elements.
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

#ifndef SPECIALS_H_INCLUDED
#define SPECIALS_H_INCLUDED

#include "app.h"
#include "stdbool.h"
#include <stdlib.h>
#include <sys/time.h>

bool collisionSpecials(int firstParticle, int secondParticle);
void specialSpawn(int particle);
void specialBreak(int particle);
void specialGrow(int particle);
void specialHeat(int particle);
void specialExplode(int particle);
void specialLife(int particle);
void specialWander(int particle);
void specialTunnel(int particle);
void specialAnt(int particle);
int specialBurn(int particle);
void specialConductive(int particle);
void specialTrail(int particle);

#endif
