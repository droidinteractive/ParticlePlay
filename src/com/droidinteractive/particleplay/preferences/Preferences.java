package com.droidinteractive.particleplay.preferences;
/*
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
import com.droidinteractive.particleplay.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences
{
	public static SharedPreferences sharedPreferences;

	//Initialize the sharedPreferences based on the context
	public static void initSharedPreferences(Context context)
	{
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	//Set the ui state in MainActivity based on sharedPreferences
	public static void loadUIState()
	{
		//MainActivity.ui = sharedPreferences.getBoolean("ui_state", true);
	}
	public static void savePreferences()
	{
		SharedPreferences.Editor prefEditor = sharedPreferences.edit();
		//prefEditor.putBoolean("accel_state", MainActivity.accel);
		prefEditor.commit();
	}

	//Set everything except UI based on the preferences that are in existence
	public static void loadPreferences()
	{
		//Log.v("Particle Play", "setPreferences");
		//int backgroundColor = sharedPreferences.getInt("background_color",0);
		//setBackgroundColor((char) Color.red(backgroundColor), (char) Color.green(backgroundColor), (char) Color.blue(backgroundColor));
		setFlippedState(sharedPreferences.getBoolean("flipped_state", false));
		//setAccelState(sharedPreferences.getBoolean("accel_state", false));
		//setAtmosphereTemp((char) sharedPreferences.getInt("atmosphere_temp", 100));
		//setAtmosphereGravity(sharedPreferences.getFloat("atmosphere_gravity", 1));
		setBorderState(sharedPreferences.getBoolean("border_left", true), sharedPreferences.getBoolean("border_top", true), sharedPreferences.getBoolean("border_right", true), sharedPreferences.getBoolean("border_bottom", true));
		setZoom(Integer.parseInt(sharedPreferences.getString("screen_zoom", "4")));
	}

	public static void loadScreenState()
	{
		MainActivity.sand_view.setKeepScreenOn(sharedPreferences.getBoolean("screen_state", true));
	}

	//@formatter:off
    public static native void setBorderState(boolean leftBorderState, boolean topBorderState, boolean rightBorderState, boolean bottomBorderState);
	public static native void setFlippedState(boolean flippedState);
	public static native void setAccelState(boolean accelState);
	public static native void setBackgroundColor(char red, char green, char blue);
	public static native void setAtmosphereTemp(char temp);
	public static native void setAtmosphereGravity(float gravity);
    public static native void setZoom(int zoom);
	//@formatter:on

	static
	{
		System.loadLibrary("particleplay"); // Load the JNI library (libparticleplay.so)
	}
}
