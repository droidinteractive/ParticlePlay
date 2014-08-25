package com.droidinteractive.particleplay.game;
/*
 * Copyright (c) 2010 Ragdoll Games
 * Copyright (c) 2010-2014 Droid Interactive
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
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.droidinteractive.particleplay.MainActivity;
import com.droidinteractive.particleplay.R;
import com.droidinteractive.particleplay.preferences.Preferences;

public class Temperature extends LinearLayout{
	
	//private MainActivity activity;
	
	private ImageView temp_icon;
	private TextView temp_text;
	private SeekBar temperature_slider;
	//private int weather = 0;
	private int wR, wG, wB;
	
	public Temperature(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	public void setActivity(MainActivity act)
	{
		//activity = act;
	}
	
	@Override
	protected void onFinishInflate()
	{
		temp_icon = (ImageView) findViewById(R.id.temp_icon);
		temp_text = (TextView) findViewById(R.id.temp_text);
		temperature_slider = (SeekBar) findViewById(R.id.temperature_slider);
		
		temp_icon.setImageResource(R.drawable.temperature);
		temperature_slider.setMax(255);
		temperature_slider.setProgress(Preferences.sharedPreferences.getInt("atmoshpere_temp", 100));
		temp_text.setText(String.valueOf(temperature_slider.getProgress()));
		
		temperature_slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				
				temp_text.setText(String.valueOf(temperature_slider.getProgress()));
				Preferences.setAtmosphereTemp((char) Preferences.sharedPreferences.getInt("atmoshpere_temp", progress));
				Preferences.setBackgroundColor((char) Preferences.sharedPreferences.getInt("background_red", wR), (char) Preferences.sharedPreferences.getInt("background_green", wG), (char) Preferences.sharedPreferences.getInt("background_blue", wB));
				
			}
		});
	}

}
