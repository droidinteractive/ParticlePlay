/*
 * points.h
 * ----------------------------
 * Declares functions for manipulating data for individual points, such as adding and deleting points.
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

#ifndef POINTS_H_INCLUDED
#define POINTS_H_INCLUDED

//Include global variables
#include "app.h"
//Include macros
#include "macros.h"

void createPoint(int xCoord, int yCoord, struct Element* element);
void deletePoint(int particle);
void unSetPoint(int particle);
void setElement(int particle, struct Element* newElement);
void setBitmapColor(int xCoord, int yCoord, struct Element* element);
void clearBitmapColor(int xCoord, int yCoord);
void createBitmapFromPoints(void);
void unFreezeParticles(int xCoord, int yCoord);
void changeHeat(char *heat, int heatChange);
int hasSpecial(int tempParticle, int special);
int getParticleSpecialVal(int tempParticle, int special);
void setParticleSpecialVal(int tempParticle, int special, int val);
int getElementSpecialVal(struct Element* tempElement, int special);
void clearSpecialVals(int tempParticle);

#endif //!POINTS_H_INCLUDED
