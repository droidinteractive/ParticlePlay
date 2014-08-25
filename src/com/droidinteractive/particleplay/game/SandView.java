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
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.droidinteractive.particleplay.MainActivity;

public class SandView extends GLSurfaceView
{
    private static final char FINGER_MOVE = 2;
	private static final char FINGER_DOWN = 1;
	private static final char FINGER_UP = 0;

	private SandViewRenderer mRenderer; //Declare the renderer
	
	private static boolean editMode = false;

	//Constructor
	public SandView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setEGLConfigChooser(8,8,8,8,16,0); //Set the EGLConfigChooser
		mRenderer = new SandViewRenderer(); //Set up the Renderer for the View
		setRenderer(mRenderer); //Associate it with this view
	}

	//When a touch screen event occurs
	public boolean onTouchEvent(final MotionEvent event)
	{
		//Set the touch state in JNI
	    char fingerState;
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
            fingerState = FINGER_DOWN;
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
		    fingerState = FINGER_UP;
		}
		else
		{
		    fingerState = FINGER_MOVE;
		}

		//Pass raw mouse state into native code
		setMouseLocation(fingerState, (int) event.getX(), (int) event.getY());

		return true;
	}

	private static native void setMouseLocation(char state, int x, int y);

	static
	{
			System.loadLibrary("particleplay");
	}
}

class SandViewRenderer implements GLSurfaceView.Renderer
{
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		//Do nothing
	}

	public void onSurfaceChanged(GL10 gl, int w, int h)
	{
		nativeResize(w, h);
		nativeLoadState(MainActivity.shouldLoadDemo);

		MainActivity.shouldLoadDemo = false;
	}

	public void onDrawFrame(GL10 gl)
	{
	    nativeRender();
	}

	private static native void nativeResize(int width, int height); //Jni resize
	private static native void nativeLoadState(boolean shouldLoadDemo); //Jni load initial state
	private static native void nativeRender();

	static
	{
		System.loadLibrary("particleplay");
	}
}