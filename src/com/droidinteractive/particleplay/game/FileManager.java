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
import com.droidinteractive.particleplay.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;

public class FileManager
{
	//File and directory constants
	public static final String ROOT_DIR = "/sdcard/particleplay/";
	public static final String SAVES_DIR = "saves/";
	public static final String ELEMENTS_DIR = "particles/";
	public static final String ATMOSPHERES_DIR = "atmospheres/";
	public static final String SAVE_EXT = ".sav";
	public static final String ELEMENT_EXT = ".par";
	public static final String ATMOSPHERE_EXT = ".atm";
	public static final String BACKUP_EXT = ".bak";
	public static final String DEMO_SAVE = "pplay";
	public static final String ELEMENT_LIST_NAME = "partList";
	public static final String LIST_EXT = ".lst";
	
	public static void intialize(Context context)
	{		
		try
		{
			//Try to create the folders
			(new File(ROOT_DIR)).mkdir();
			(new File(ROOT_DIR + SAVES_DIR)).mkdir();
			(new File(ROOT_DIR + ELEMENTS_DIR)).mkdir();
			(new File(ROOT_DIR + ATMOSPHERES_DIR)).mkdir();

			//Set the input stream to the demo save resource
			InputStream in = context.getResources().openRawResource(R.raw.pplay);
			//Set the output stream to the demo save file location
			OutputStream out = new FileOutputStream(ROOT_DIR + SAVES_DIR + DEMO_SAVE + SAVE_EXT);
			//Create a 256 byte buffer
			byte[] buf = new byte[256];
			//Read in 256 byte chunks from in and output to out
			int len;
			while ((len = in.read(buf)) != -1)
			{
				out.write(buf, 0, len);
			}
			//Close both streams
			in.close();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			//FileNotFoundException is normal, ignore it
			e.printStackTrace();
		}
		catch (IOException e)
		{
			//IOException is also fine, ignore
			e.printStackTrace();
		}
	}
}
