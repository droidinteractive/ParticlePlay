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
import java.io.UnsupportedEncodingException;

import com.droidinteractive.particleplay.MainActivity;
import com.droidinteractive.particleplay.R;

import android.content.Context;
import android.widget.Toast;

public class SaveManager
{
	public static String[] saveFiles;
	public static final File saveDir = new File(FileManager.ROOT_DIR + FileManager.SAVES_DIR);
	
	public static void refresh(Context c)
	{
		//Filter the files in the directory to exclude the demo and temp saves
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				return !name.contentEquals("temp.sav");
			}
		};
		//Get the array of filenames
		saveFiles = saveDir.list(filter);
		if (saveFiles == null)
		{
			Toast.makeText(c.getApplicationContext(), R.string.sdcard_not_found, Toast.LENGTH_SHORT).show();
		}
		else
		{
			
		}
	}
	public static int getNumSaves()
	{
		// Was crashing in the emulator until I put this fix in.
		if(saveFiles == null)
			return 0;
		return saveFiles.length;
	}
	public static String getSaveName(int index)
	{
		return saveFiles[index].replace(FileManager.SAVE_EXT, "");
	}
	
	//Overloading these functions -- be careful
	public static boolean saveState(String statename)
	{
		try
		{
			char retVal = saveState((FileManager.ROOT_DIR + FileManager.SAVES_DIR + statename + FileManager.SAVE_EXT).getBytes("ISO-8859-1"));
			if(retVal == 0)
			{
				return false;
			}
			
			return true;
		}
		catch (UnsupportedEncodingException e)
		{
			//Hopefully this doesn't happen :P
			e.printStackTrace();
		}
		
		return false;
	}
	public static boolean loadState(String statename)
	{
		try
		{
			char retVal = loadState((FileManager.ROOT_DIR + FileManager.SAVES_DIR + statename + FileManager.SAVE_EXT).getBytes("ISO-8859-1"));
			if(retVal == 0)
			{
				return false;
			}
			MainActivity.last_state_loaded = statename;
			
			return true;
		}
		catch (UnsupportedEncodingException e)
		{
			//Hopefully this doesn't happen :P
			e.printStackTrace();
		}
		
		return false;
	}
	public static void deleteState(String statename)
	{
		String filename = statename + FileManager.SAVE_EXT;
		if(fileExists(filename))
		{
			File file = new File(saveDir, filename);
			file.delete();
		}
	}
	
	public static boolean fileExists(String filename)
	{
		File file = new File(saveDir, filename);
		return file.exists();
	}
	
	public native static char saveState(byte[] saveLoc);
	public native static char loadState(byte[] loadLoc);
	
	static
	{
		System.loadLibrary("particleplay");
	}
}
