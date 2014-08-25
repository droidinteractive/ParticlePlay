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
import java.util.ArrayList;

import com.droidinteractive.particleplay.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class CustomElementActivity extends TabActivity
{
	//TODO: 1) Depending on the Intent, ACTION_EDIT or ACTION_NEW, set up the UI with
	//either default or loaded data, and allow the user to edit and save
	// 2) Make sure no duplicate names can be entered.
	/*
	 * All elements held in same file, accessed by name.
	 */
	private String filename;
	public boolean newElement;
	public CustomElement mCustomElement;
	
	// Variables for passing data to basic activity
	public ArrayList<Integer> collisions;
	public ArrayList<Integer> specials;
	public ArrayList<Integer> specialVals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Try loading the element
		filename = getIntent().getStringExtra("filename");
		if(filename != null)
		{
			newElement = false;
			mCustomElement = new CustomElement(filename);
			if(!mCustomElement.loadPropertiesFromFile())
			{
				// If loading fails, we need to quit and show a message
				Toast.makeText(getApplicationContext(), R.string.ce_load_failed_msg, Toast.LENGTH_LONG).show();
				finish();
			}
		}
		else
		{
			newElement = true;
			mCustomElement = new CustomElement();
		}
		
		// Set the content view
		setContentView(R.layout.custom_element_activity);
		// Set up some variables
		Intent intent;
		TabSpec spec;
		TabHost tabHost = getTabHost();
		Resources res = getResources();
		// Create the tabs
		intent = new Intent(CustomElementActivity.this, CustomElementBasicActivity.class);
		spec = tabHost.newTabSpec("basic").setIndicator(res.getString(R.string.basic_tab)).setContent(intent);
		tabHost.addTab(spec);
		intent = new Intent(CustomElementActivity.this, CustomElementAdvancedActivity.class);
		spec = tabHost.newTabSpec("advanced").setIndicator(res.getString(R.string.advanced_tab)).setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);
	}
}
