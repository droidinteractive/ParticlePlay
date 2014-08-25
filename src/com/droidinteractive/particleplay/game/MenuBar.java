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
import com.droidinteractive.particleplay.MainActivity;

import com.droidinteractive.particleplay.R;
import com.droidinteractive.particleplay.preferences.Preferences;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MenuBar extends LinearLayout
{
	//Used when exit is called because we need the specific instance of the activity to end
	private MainActivity activity;
	
	private Context context;

	private static ImageButton eraser_button;
	private static ImageButton play_pause_button;
	public static Button gravity_button;
	public static ImageButton accelerometer_button;
	//private static ImageButton load_demo_button;
	//private static ImageButton clear_button;
	private static ImageButton exit_button;
	private static ImageButton fade_button;

	//Used for eraser
	public static boolean eraserOn = false;
	private static char tempElement = 0;

	//Constructor
	public MenuBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		setGravity(Gravity.CENTER_HORIZONTAL);
	}
	
	//Used to get specific instance of activity
	public void setActivity(MainActivity act)
	{
		activity = act;
	}

	//Set the eraser to the off position
	public static void setEraserOff()
	{
		eraserOn = false;
		eraser_button.setImageResource(R.drawable.eraser);

		MainActivity.setElement(tempElement);
	}
	//Set the eraser to the off position
	public void setPlayState(boolean state)
	{
		
		if (state)
		{
			play_pause_button.setImageResource(R.drawable.pause);
		}
		else
		{
			play_pause_button.setImageResource(R.drawable.play);
		}
	}	

	//Called when it's finished inflating the XML layout
	@Override
	protected void onFinishInflate()
	{
		//Set up all the variables for the objects
		eraser_button = (ImageButton) findViewById(R.id.eraser_button);
		play_pause_button = (ImageButton) findViewById(R.id.play_pause_button);
		gravity_button = (Button) findViewById(R.id.popup_menu_button);
		fade_button = (ImageButton) findViewById(R.id.fade_button);
		accelerometer_button = (ImageButton) findViewById(R.id.accelerometer_button);
		//load_demo_button = (ImageButton) findViewById(R.id.load_demo_button);
		//clear_button = (ImageButton) findViewById(R.id.clear_button);
		exit_button = (ImageButton) findViewById(R.id.exit_button);

		//Set up the OnClickListener for the eraser button
		eraser_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//If it was on eraser, swap back to regular element
				if (eraserOn)
				{
					MainActivity.setElement(tempElement);

					//Change the button to look unclicked
					eraser_button.setImageResource(R.drawable.eraser);
				}
				//If it is on a normal element, go to eraser and store that element for later
				else
				{
					tempElement = MainActivity.getElement();
					MainActivity.setElement(MainActivity.ERASER_ELEMENT);

					//Change the button to look clicked
					eraser_button.setImageResource(R.drawable.eraser_on);
				}
				eraserOn = !eraserOn;
			}
		});
		fade_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fade_button.isSelected()) {
					fade_button.setSelected(false);
					MainActivity.setFilterMode((char)0);
				} else {
					fade_button.setSelected(true);
					MainActivity.setFilterMode((char)1);
				}
			}
		});
		
		if (MainActivity.getElement() == MainActivity.ERASER_ELEMENT) //If the current element is eraser
		{
			//Start off the button to being on
			eraser_button.setImageResource(R.drawable.eraser_on);
		}
		else
		{
			//Start off the eraser in "off" position
			eraser_button.setImageResource(R.drawable.eraser);
		}

		//Set up the OnClickListener for the play/pause button
		play_pause_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				MainActivity.play = !MainActivity.play;
				MainActivity.setPlayState(MainActivity.play);

				if (MainActivity.play)
				{
					play_pause_button.setImageResource(R.drawable.pause);
				}
				else
				{
					play_pause_button.setImageResource(R.drawable.play);
				}
			}

		});
		if (MainActivity.play)
		{
			play_pause_button.setImageResource(R.drawable.pause);
		}
		else
		{
			play_pause_button.setImageResource(R.drawable.play);
		}

		//Set up the OnClickListener for the load button
				accelerometer_button.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						MainActivity.accel = !MainActivity.accel;
						Preferences.setAccelState(MainActivity.accel);
						Preferences.savePreferences();
						if (MainActivity.accel)
						{
							
							accelerometer_button.setImageResource(R.drawable.accelerometer_on);
							Toast.makeText(context, "Device Tilt Gravity", Toast.LENGTH_SHORT).show();
						}
						else
						{
							accelerometer_button.setImageResource(R.drawable.accelerometer);
							Toast.makeText(context, "Standard Gravity", Toast.LENGTH_SHORT).show();
						}
						
					}
				});
				if (MainActivity.accel)
				{
					accelerometer_button.setImageResource(R.drawable.accelerometer_on);
				}
				else
				{
					accelerometer_button.setImageResource(R.drawable.accelerometer);
				}

		//Set up the OnClickListener for the exit button
				/*
		clear_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				((MainActivity) context).clearScreen();
			}
		});
		*/
		
		//Set up the OnClickListener for the exit button
				exit_button.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						activity.finish();
					}
				});
	}
}