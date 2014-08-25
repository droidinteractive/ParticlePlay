package com.droidinteractive.particleplay.game;
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
import java.io.FilenameFilter;
import java.util.ArrayList;

public class CustomElementManager
{
	public static ArrayList<CharSequence> customElementList;
	public static String[] customElementFiles;
	public static final File customElementsDir = new File(FileManager.ROOT_DIR + FileManager.ELEMENTS_DIR);
	
	public static void loadCustomElements()
	{
		//Clear everything first
		customElementList.clear();
		clearCustomElements();
		
		//Create the FilenameFilter to accept only *.ele files
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String filename)
			{
				return filename.endsWith(FileManager.ELEMENT_EXT);
			}
		};
		//TODO(UNIMPORTANT): not sure if this creates a small memory leak or not
		customElementFiles = customElementsDir.list(filter);
		
		for(int i = 0; i < customElementFiles.length; i++)
		{
			//TODO: get the custom element name
			//Tell JNI to load the custom element
			loadCustomElement(customElementFiles[i].toCharArray());
		}
	}
	
	public native static void loadCustomElement(char[] filename);
	public native static void clearCustomElements();
	
	static
	{
		System.loadLibrary("libparticleplay.so");
	}
}
