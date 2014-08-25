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

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class Control extends LinearLayout
{
	//The instance of the current activity is stored here and modified through setActvity (call from DemoActivity)
	//private MainActivity activity;

	//Two objects in the control area
	private ImageButton element_picker_button;
	//private ImageButton custom_elements_button;
	private SeekBar brush_size_slider;
	private TextView brush_text;

	//Constructor
	public Control(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	//Sets the current instance of the activity
	public void setActivity(MainActivity act)
	{
		//activity = act;
	}

	//Called once the the xml is finished inflating	
	@Override
	protected void onFinishInflate()
	{
		//Define the ImageButton and SeekBar set before using the res ids
		element_picker_button = (ImageButton) findViewById(R.id.particle_picker_button);
		//custom_elements_button = (ImageButton) findViewById(R.id.custom_particles_button);
		brush_size_slider = (SeekBar) findViewById(R.id.brush_size_slider);
		brush_text = (TextView) findViewById(R.id.brush_size_text);

		//Set a click listener for the button which should pop up element picker dialog when clicked
		element_picker_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
			    MainActivity.setPlayState(false);
			    MainActivity.instance.DialogElementPicker();
				//activity.showDialog(MainActivity.ELEMENT_PICKER); //Run the element picker dialog
			}
		});
		
		// Set a click listener for the button which should open up the CustomElementManagerActivity when clicked
		/*
		custom_elements_button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				FlurryAgent.logEvent("Custom elements button (app)");
				activity.startActivity(new Intent(activity, CustomElementManagerActivity.class));
			}
		});
		*/

		//Set a change listener for the seekbar
		brush_size_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
		{
			@Override
			public void onProgressChanged(SeekBar seekbar, int progress, boolean fromTouch)
			{
				//When it is dragged, set the brush size to 32 * the fraction of the bar dragged
				int p = 32 * progress / seekbar.getMax();
				MainActivity.setBrushSize((char) p);
				if(p==0)
					p=1;
				brush_text.setText(String.valueOf(p));
			}

			//These aren't needed for now
			@Override
			public void onStartTrackingTouch(SeekBar seekbar)
			{}

			@Override
			public void onStopTrackingTouch(SeekBar seekbar)
			{}
		});
		//Start off the progress bar at a brush size of 4
		brush_size_slider.setProgress((int) 4 * brush_size_slider.getMax() / 32);
		brush_text.setText("4");
	}
}
