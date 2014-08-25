/*
 * macros.h
 * -------------------------------
 * Some overall macros needed by the JNI code.
 * These are not all of the macros defined, but are ones that are global and may need to be
 * changed often, especially NUM_BASE_ELEMENTS (when adding new elements)
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

#ifndef MACROS_H_INCLUDED
#define MACROS_H_INCLUDED

/*
 * CONSTANTS
 */
#define MAX_POINTS 100000
#define NUM_BASE_ELEMENTS 32
#define MAX_SPECIALS 6
#define MAX_ELEMENTS 256

#define MAX_CE_NAME_LENGTH 256
#define MAX_UDID_LENGTH 256

#define NUM_COLLISIONS 13

#define DEFAULT_BRUSH_SIZE 4

#define TRUE 1
#define FALSE 0

#define FINGER_DOWN 1
#define FINGER_UP 0

#define DEFAULT_ZOOM_FACTOR 4

#define NORMAL_ELEMENT 3

#define SPAWN_ELEMENT 0
#define DRAG_ELEMENT 1
#define ERASER_ELEMENT 2
#define SAND_ELEMENT 3
#define WATER_ELEMENT 4
#define STEAM_ELEMENT 5
#define ICE_ELEMENT 6
#define WALL_ELEMENT 7
#define DRYWALL_ELEMENT 8
#define PLANT_ELEMENT 9
#define FIRE_ELEMENT 10
#define LAVA_ELEMENT 11
#define STONE_ELEMENT 12
#define OIL_ELEMENT 13
#define C4_ELEMENT 14
#define HYDROGEN_ELEMENT 15
#define FUSE_ELEMENT 16
#define ACID_ELEMENT 17
#define SALT_ELEMENT 18
#define SALT_WATER_ELEMENT 19
#define GLASS_ELEMENT 20
#define MUD_ELEMENT 21
#define REPLICATOR_ELEMENT 22
#define COAL_ELEMENT 23
#define ANT_ELEMENT 24
#define GUNPOWDER_ELEMENT 25
#define FLIES_ELEMENT 26
#define WOOD_ELEMENT 27
#define TERMITE_ELEMENT 28
#define INSECTICIDE_ELEMENT 29
#define ELECTRICITY_ELEMENT 30
#define METAL_ELEMENT 31

#define REPLICATOR_SPAWN_PROB 200

#define INERTIA_UNMOVABLE 255

#define DEFAULT_ATMOSPHERE_TEMP 100
#define DEFAULT_ATMOSPHERE_GRAVITY 1

#define DEFAULT_RED 0
#define DEFAULT_GREEN 0
#define DEFAULT_BLUE 0

#define DEFAULT_BORDER_LEFT 1
#define DEFAULT_BORDER_TOP 1
#define DEFAULT_BORDER_RIGHT 1
#define DEFAULT_BORDER_BOTTOM 1

#define SPECIAL_NONE -1
#define SPECIAL_SPAWN 1
#define SPECIAL_BREAK 2
#define SPECIAL_GROW 3
#define SPECIAL_HEAT 4
#define SPECIAL_EXPLODE 5
#define SPECIAL_LIFE 6
#define SPECIAL_WANDER 7
#define SPECIAL_JUMP 8
#define SPECIAL_TUNNEL 9
#define SPECIAL_BURN 10
#define SPECIAL_CONDUCTIVE 11
#define SPECIAL_CONDUCTABLE 12
#define SPECIAL_TRAIL 13
#define SPECIAL_ANT 14

#define ELECTRIC_NO_DIR 0 // 0000
#define ELECTRIC_X1     1 // 0001
#define ELECTRIC_Y1     2 // 0010
#define ELECTRIC_XN     4 // 0100
#define ELECTRIC_YN     8 // 1000
#define ELECTRIC_WAIT  16 //10000
#define SPECIAL_VAL_UNSET -1

#define FILTER_NONE 	0
#define	FILTER_MOTION	1

/*
 * LOGGING
 */
#define LOG_STR "Particle Play"
#define LOGI(...) if (LOGGING) __android_log_print(ANDROID_LOG_INFO, LOG_STR, __VA_ARGS__)
#define LOGD(...) if (LOGGING) __android_log_print(ANDROID_LOG_DEBUG, LOG_STR, __VA_ARGS__)
#define LOGW(...) if (LOGGING) __android_log_print(ANDROID_LOG_WARNING, LOG_STR, __VA_ARGS__)
#define LOGE(...) if (LOGGING) __android_log_print(ANDROID_LOG_ERROR, LOG_STR, __VA_ARGS__)

#endif //!MACROS_H_INCLUDED
