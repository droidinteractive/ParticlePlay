package com.droidinteractive.slidingdrawer;
/*
 * Copyright (c) Igor Morais
 * Copyright (c) 2010 Ragdoll Games
 * Copyright (c) 2010-2014 Droid Interactive
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
/**
 * Listener invoked when the drawer is opened.
 * <p>
 * This class has ported and improved from the Android Open Source Project.
 * 
 * @author Igor Morais
 * @author Mor41s.1gor@gmail.com
 *         <p>
 * @see <a
 *      href="http://http://developer.android.com/reference/android/widget/SlidingDrawer.OnDrawerOpenListener.html">
 *      SlidingDrawer.OnDrawerOpenListener</a>
 */
public interface OnDrawerOpenListener {

	/**
	 * Invoked when the drawer becomes fully open.
	 */
	void onDrawerOpened();
}