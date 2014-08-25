/*
 * collisions.h
 * -------------------------------
 * Contains all the collision data in a table. At some point hopefully we can make some code that will load
 * this collision data from a file. When adding an element, it must be added here.
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

#ifndef COLLISIONS_H_INCLUDED
#define COLLISIONS_H_INCLUDED

//Include the global macros
#include "macros.h"

char reciprocals[NUM_COLLISIONS] =
{
		0,
		1,
		3,
		2,
		5,
		4,
		7,
		6,
		8,
		9,
		10,
		12,
		11
};

char collision[NUM_BASE_ELEMENTS][NUM_BASE_ELEMENTS] =
{
			//Spawn 0
			//Sp,Dr,Er,Sd,Wa,Sm,Ic,Wl,Dw,Pl,Fi,La,St,Oi,C4,Hy,Fu,Ac,Sa,Sw,Gl,Mu,Re,Co,An,Gp,Fl,Wo,Te,In,El,Me
			{0, 0, 0,  0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,0,0,0},
			//Drag 1
			{0, 0, 0,  0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,0,0,0},
			//Eraser 2
			{0, 0, 0,  0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0,0,0,0},
			//Sand 3
			{0, 0, 0,  0, 10, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Water 4
			{1, 1, 1, 10, 1, 1, 1, 1, 1, 1, 12, 1, 1, 1, 1, 1, 1, 7, 8, 1, 1, 1, 2, 10, 1, 1, 1, 1, 1,1,4,0},
			//Steam 5
			{1, 1, 1, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,1,4,0},
			//Ice 6
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 8, 8, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Wall 7
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Drywall 8
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Plant 9
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 9, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,7,0},
			//Fire 10
			{1, 1, 1, 1, 12, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,1,4,1},
			//Lava 11
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,1,4,1},
			//Stone 12
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Oil 13
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,1,4,1},
			//C4 14
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,0,0},
			//Hydrogen 15
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,0,0},
			//Fuse 16
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Acid 17
			{1, 1, 1, 1, 6, 6, 1, 1, 4, 4, 4, 4, 1, 4, 4, 4, 4, 1, 1, 6, 1, 6, 2, 1, 4, 1, 4, 4, 4,1,4,1},
			//Salt 18
			{0, 0, 0, 0, 8, 1, 8, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 9, 0, 0, 0, 0,0,4,0},
			//Salt-Water 19
			{1, 1, 1, 1, 1, 1, 8, 1, 1, 9, 1, 1, 1, 1, 1, 1, 1, 7, 8, 1, 1, 1, 2, 1, 9, 1, 1, 9, 9,1,0,1},
			//Glass 20
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,4,0},
			//Mud 21
			{1, 1, 1,  1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1,0, 4,1},
			//Replicator 22
			{0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3,3,3,3},
			//Coal 23
			{0, 0, 0, 0, 10, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0, 4,0},
			//Ant 24
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 9, 9, 0, 1, 2, 0, 0, 0, 0, 0, 0,5,5,0},
			//Gunpowder 25
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,0,0},
			//Flies 26
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 9, 9, 0, 1, 2, 0, 0, 0, 0, 0, 0,5, 5,0},
			//Wood 27
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 9, 0, 1, 2, 0, 0, 0, 0, 0, 0,0,4,0},
			//Termite 28
			{0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 5, 0, 9, 0, 1, 2, 0, 0, 0, 0, 0, 0,5,5,0},
			//Insecticide 29
			{0, 0, 0, 0, 10, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 4, 0, 4, 0, 4,0,4,0},
			//Electricity 30
			{0, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0, 5, 5, 2, 5, 5, 5, 5, 5, 5,5,11,0},
			//Metal 31
			{0, 0, 0, 0, 10, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 0, 4, 0, 4, 0, 4,0,0,0}
};

#endif //!COLLISIONS_H_INCLUDED
