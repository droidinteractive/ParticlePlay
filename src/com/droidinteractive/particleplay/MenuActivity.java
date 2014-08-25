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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.droidinteractive.particleplay.custom.CustomElementManagerActivity;
import com.droidinteractive.particleplay.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class MenuActivity extends Activity
{
	public static Button start_game_button;
	public static Button custom_elements_button;
	public static Button about_button;
	public static Button fix_me_button;
	public static Button how_to_play_button;
	public static Button exit_button;
	public static boolean loaded = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); //Call the super method
		
		// Check if running the latest Google Play Services
		Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MenuActivity.this);
		if (resultCode == ConnectionResult.SUCCESS) {
		    // Continue with the game
		} else {
		    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, MenuActivity.this, 0);
		    if (dialog != null) {
		    // Assist user with updating to the latest GooglePlayServices
		        dialog.show();
		    }
		}
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); //Get rid of title bar
		
		setContentView(R.layout.menu_activity);
		
		//Define all the objects
		start_game_button = (Button) findViewById(R.id.start_game_button);
		// Temporarily removed this piece of garbage
		//custom_elements_button = (Button) findViewById(R.id.custom_particles_button);
		about_button = (Button) findViewById(R.id.about_button);
		fix_me_button = (Button) findViewById(R.id.fix_me_button);
		how_to_play_button = (Button) findViewById(R.id.how_to_play_button);
		exit_button = (Button) findViewById(R.id.exit_button);
				
		start_game_button.setOnClickListener
		(
			new OnClickListener()
			{
				public void onClick(View v)
				{
					//Start the main app activity
					startActivity(new Intent(MenuActivity.this, SplashActivity.class));
				}
			}
		);
		/*
		custom_elements_button.setOnClickListener
		(
			new OnClickListener()
			{
				public void onClick(View v)
				{
					//Start the CustomElementManagerActivity
					startActivity(new Intent(MenuActivity.this, CustomElementManagerActivity.class));
				}
			}
		);*/
		
		fix_me_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(MenuActivity.this, FixMeActivity.class));
			}
		});
		
		about_button.setOnClickListener
		(
				new OnClickListener()
				{
					public void onClick(View v)
					{
						final SpannableString s = new SpannableString(MenuActivity.this.getText(R.string.about_string));
						Linkify.addLinks(s, Linkify.ALL);
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuActivity.this);
						alertDialog.setTitle("About");
						alertDialog.setMessage(s);						
						alertDialog.setPositiveButton("Cool", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();							
							}
						});
						
						AlertDialog alertdisplayDialog = alertDialog.create();
						alertdisplayDialog.show();
						alertdisplayDialog.getWindow().getAttributes();
						TextView textView = (TextView) alertdisplayDialog.findViewById(android.R.id.message);
						textView.setTextSize(10);
					}
				}
		);
		
		how_to_play_button.setOnClickListener
		(
			new OnClickListener()
			{
				public void onClick(View v)
				{
					//Show the instructions
					how_to_play();
				}
			}
		);
		
		exit_button.setOnClickListener
		(
			new OnClickListener()
			{
				public void onClick(View v)
				{
					System.exit(0);
				}
			}
		);
		
	}
	
	public void how_to_play()
	{
		StringBuffer data = new StringBuffer();
		try
		{
			InputStream stream = getAssets().open("instructions.html");
			BufferedReader in = new BufferedReader(new InputStreamReader(stream), 8192);
			while(true)
			{
				String line = in.readLine();
				if(line == null)
				{
					break;
				}
				data.append(line).append("\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		
		// Real WebView, unlike some people.
		WebView how_to_play = new WebView(this);
		how_to_play.setBackgroundColor(0x00000000);
		how_to_play.loadDataWithBaseURL("file:///android_asset/", data.toString(), "text/html", "ascii", null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(true);
		Resources res = getResources();
		builder.setTitle(res.getText(R.string.how_to_play_title));
		builder.setView(how_to_play);
		builder.show();
	}
	
	protected Dialog onCreateDialog(int id) 
	{
	   	if (id == 1) //Copyright message
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this); //Declare the object
			Resources res = getResources();
			builder.setMessage(res.getText(R.string.exit_text));		      
			AlertDialog alert = builder.create(); //Create object
			return alert; //Return handle
		}
		
		return null; //No need to return anything, just formality
	}
}
