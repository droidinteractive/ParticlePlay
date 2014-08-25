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
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.droidinteractive.particleplay.game.SaveManager;
import com.droidinteractive.particleplay.R;

public class SaveStateActivity extends Activity
{
	public static Button saveButton;
	public static EditText statename;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.save_state_activity);
		
		saveButton = (Button) findViewById(R.id.save_state_button);
		statename = (EditText) findViewById(R.id.save_state_filename);
		
		saveButton.setOnClickListener
		(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(statename.getWindowToken(), 0);
						boolean success = SaveManager.saveState(statename.getText().toString());
						if (!success)
						{
							Toast.makeText(getApplicationContext(), R.string.save_state_failed, Toast.LENGTH_SHORT).show();
						}
						finish();
					}
				}
		);
		
		if(MainActivity.last_state_loaded != null)
		{
			statename.setText(MainActivity.last_state_loaded);
		}
	}
}
