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
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.droidinteractive.particleplay.game.SaveManager;
import com.droidinteractive.particleplay.R;

public class LoadStateActivity extends Activity
{
	private static ImageButton actionButton;
	private static TableRow tr;
	private static LinearLayout buttonContainer;
	private static Resources res;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_state_activity);
		
		//Get the associated resources
		res = getResources();
		
		//Refresh the files list
		SaveManager.refresh(this);
		//Go through and find all the save files and dynamically load them
		int length = SaveManager.getNumSaves();
		if(length != 0)
		{
			for(int i = 0; i < length; i++)
			{
				addEntity(SaveManager.getSaveName(i));
			}
		}
		else
		{
			TableLayout tl = (TableLayout)findViewById(R.id.loads_container);
			tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			tr.setGravity(Gravity.CENTER);
			
			TextView tv = new TextView(this);
			tv.setText(res.getText(R.string.no_saves));
			
			tr.addView(tv);
			tl.addView(tr);
		}
	}
	
    public void addEntity(String entityName)
    {
    	final String entityNameFinal = entityName;
        
        //Create a TableLayout object associated with the TableLayout in the .xml file
        TableLayout tl = (TableLayout)findViewById(R.id.loads_container);
        //Create a LinearLayout to contain our row
        buttonContainer = new LinearLayout(this);
		buttonContainer.setBackgroundResource(R.drawable.load_state_tr_bg);
		buttonContainer.setLongClickable(true);
		buttonContainer.setClickable(true);
		buttonContainer.setPadding(25, 10, 25, 10);
		buttonContainer.setGravity(Gravity.RIGHT);
		
		//Create a TextView to hold the filename
		TextView filename = new TextView(this);
		filename.setText(entityName);
		filename.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		
		buttonContainer.addView(filename);

        //Create a button to be the action invoker
        actionButton = new ImageButton(this);
        actionButton.setBackgroundResource(R.drawable.load_state_select);
        actionButton.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, 
                        LayoutParams.WRAP_CONTENT));
        actionButton.setOnClickListener
        (
    		new OnClickListener()
    		{
    			public void onClick(View v)
    			{
                	boolean success = SaveManager.loadState(entityNameFinal);
                	if (!success)
                	{
                		Toast.makeText(getApplicationContext(), R.string.load_state_failed, Toast.LENGTH_SHORT).show();
                	}
                	finish();
    			}
    		}
        );
        buttonContainer.addView(actionButton);
        
        actionButton = new ImageButton(this);
        actionButton.setBackgroundResource(R.drawable.load_state_delete);
        actionButton.setLayoutParams(new LayoutParams(
                LayoutParams.WRAP_CONTENT, 
                LayoutParams.WRAP_CONTENT));
		actionButton.setOnClickListener
		(
			new OnClickListener()
			{
				public void onClick(View v)
				{
					SaveManager.deleteState(entityNameFinal);
					ViewGroup parent = (ViewGroup) v.getParent().getParent();
					parent.removeView((View) v.getParent());
					SaveManager.refresh(LoadStateActivity.this);
					if(SaveManager.getNumSaves() == 0)
					{
						TableLayout tl = (TableLayout)findViewById(R.id.loads_container);
						tr = new TableRow(getBaseContext());
						tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
						tr.setGravity(Gravity.CENTER);
						
						TextView tv = new TextView(getBaseContext());
						tv.setText(res.getText(R.string.no_saves));
						
						tr.addView(tv);
						tl.addView(tr);
					}
				}
			}
		);
		buttonContainer.addView(actionButton);
        
        //Add a test click event
        buttonContainer.setOnClickListener
        (
        	new OnClickListener()
	        {
                public void onClick(View viewParam)
                {
                	boolean success = SaveManager.loadState(entityNameFinal);
                	if (!success)
                	{
                		Toast.makeText(getApplicationContext(), R.string.load_state_failed, Toast.LENGTH_SHORT).show();
                	}
                	finish();
                }
	        }
        );
        
        //Add the created row to our TableLayout
        tl.addView(buttonContainer, new LayoutParams(
                LayoutParams.MATCH_PARENT, 
                LayoutParams.WRAP_CONTENT));
    }
}