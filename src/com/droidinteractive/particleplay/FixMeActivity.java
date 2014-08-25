package com.droidinteractive.particleplay;
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

import com.droidinteractive.particleplay.game.FileManager;
import com.droidinteractive.particleplay.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class FixMeActivity extends Activity
{
	private TextView logOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.fix_me_activity);
		
		logOutput = (TextView) findViewById(R.id.fix_me_textview);
		
		// Thread this later if it becomes processor intensive
		fixMe();
	}
	
	// Core function -- does all the fixing
	private void fixMe()
	{
		// Try to fix crashing by moving temp.sav to temp.sav.bak
		logOutput.setText(logOutput.getText() + "\n\n" + getResources().getString(R.string.fix_me_temp_sav));
		
		File file = new File(FileManager.ROOT_DIR + FileManager.SAVES_DIR + "temp" + FileManager.SAVE_EXT);
		boolean success = file.renameTo(new File(FileManager.ROOT_DIR + FileManager.SAVES_DIR + "temp" + FileManager.SAVE_EXT + FileManager.BACKUP_EXT));
		if (success)
		{
			logOutput.setText(logOutput.getText() + "\n" + getResources().getString(R.string.fix_me_temp_sav_succeeded));
		}
		else
		{
			logOutput.setText(logOutput.getText() + "\n" + getResources().getString(R.string.fix_me_temp_sav_failed));
		}
	
		// Finally
		logOutput.setText(logOutput.getText() + "\n\n" + getResources().getString(R.string.fix_me_done));
	}
}
