package com.droidinteractive.particleplay.custom;
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
import java.io.File;
import java.util.ArrayList;

import com.droidinteractive.particleplay.game.FileManager;
import com.droidinteractive.particleplay.R;

import android.content.Context;
import android.widget.Toast;

public class CustomElementManager
{
	private static ArrayList<CustomElement> sCustomElements = new ArrayList<CustomElement>();
	private static final File elementDir = new File(FileManager.ROOT_DIR + FileManager.ELEMENTS_DIR);
	
	public static void refresh(Context c)
	{
		// Clear the old array
		sCustomElements.clear();
		//Get the array of filenames
		String[] elementFiles = elementDir.list();
		if (elementFiles == null)
		{
			//Log.w("Particle Play", "Warning: No sdcard found!");
			Toast.makeText(c.getApplicationContext(), R.string.sdcard_not_found, Toast.LENGTH_SHORT).show();
			elementFiles = new String[0];
		}
		//Log.v("Particle Play", "CustomElementManager refreshed, files found: " + elementFiles.length);
		for(int i = 0; i < elementFiles.length; i++)
		{
			if (elementFiles[i].endsWith(FileManager.ELEMENT_EXT))
			{
				//Log.v("Particle Play", "..." + elementFiles[i]);
				// Cut off the element extension when saving the filename
				sCustomElements.add(new CustomElement(elementFiles[i].substring(0, elementFiles[i].length()-FileManager.ELEMENT_EXT.length())));
			}
		}
	}
	public static ArrayList<CustomElement> getElementList()
	{
		return sCustomElements;
	}
}
