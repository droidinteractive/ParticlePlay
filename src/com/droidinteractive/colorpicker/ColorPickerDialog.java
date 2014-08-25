package com.droidinteractive.colorpicker;
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

import android.annotation.TargetApi;
import android.app.*;
import android.content.*;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.*;
import android.os.Build;
import android.view.*;
import android.widget.*;

public class ColorPickerDialog {
	public interface OnColorPickerListener {
		void onCancel(ColorPickerDialog dialog);
		void onOk(ColorPickerDialog dialog, int color);
	}

	final AlertDialog dialog;
	final OnColorPickerListener listener;
	final View viewHue;
	final ColorPickerBox viewSatVal;
	final ImageView viewCursor;
	final View viewOldColor;
	final View viewNewColor;
	final ImageView viewTarget;
	final ViewGroup viewContainer;
	float[] currentColorHsv = new float[3];

	/**
	 * create an ColorPickerDialog. call this only from OnCreateDialog() or from a background thread.
	 * 
	 * @param context
	 *            current context
	 * @param color
	 *            current color
	 * @param listener
	 *            an OnColorPickerListener, allowing you to get back error or
	 */
	public ColorPickerDialog(final Context context, int color, OnColorPickerListener listener) {
		this.listener = listener;
		Color.colorToHSV(color, currentColorHsv);

		final View view = LayoutInflater.from(context).inflate(R.layout.colorpicker_dialog, null);
		viewHue = view.findViewById(R.id.colorpicker_viewHue);
		viewSatVal = (ColorPickerBox) view.findViewById(R.id.colorpicker_viewSatBri);
		viewCursor = (ImageView) view.findViewById(R.id.colorpicker_cursor);
		viewOldColor = view.findViewById(R.id.colorpicker_warnaLama);
		viewNewColor = view.findViewById(R.id.colorpicker_warnaBaru);
		viewTarget = (ImageView) view.findViewById(R.id.colorpicker_target);
		viewContainer = (ViewGroup) view.findViewById(R.id.colorpicker_viewContainer);

		viewSatVal.setHue(getHue());
		viewOldColor.setBackgroundColor(color);
		viewNewColor.setBackgroundColor(color);

		viewHue.setOnTouchListener(new View.OnTouchListener() {
			@Override public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_UP) {

					float y = event.getY();
					if (y < 0.f) y = 0.f;
					if (y > viewHue.getMeasuredHeight()) y = viewHue.getMeasuredHeight() - 0.001f; // to avoid looping from end to start.
					float hue = 360.f - 360.f / viewHue.getMeasuredHeight() * y;
					if (hue == 360.f) hue = 0.f;
					setHue(hue);

					// update view
					viewSatVal.setHue(getHue());
					moveCursor();
					viewNewColor.setBackgroundColor(getColor());

					return true;
				}
				return false;
			}
		});
		viewSatVal.setOnTouchListener(new View.OnTouchListener() {
			@Override public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE
						|| event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_UP) {

					float x = event.getX(); // touch event are in dp units.
					float y = event.getY();

					if (x < 0.f) x = 0.f;
					if (x > viewSatVal.getMeasuredWidth()) x = viewSatVal.getMeasuredWidth();
					if (y < 0.f) y = 0.f;
					if (y > viewSatVal.getMeasuredHeight()) y = viewSatVal.getMeasuredHeight();

					setSat(1.f / viewSatVal.getMeasuredWidth() * x);
					setVal(1.f - (1.f / viewSatVal.getMeasuredHeight() * y));

					// update view
					moveTarget();
					viewNewColor.setBackgroundColor(getColor());

					return true;
				}
				return false;
			}
		});

		dialog = new AlertDialog.Builder(context)
			.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override public void onClick(DialogInterface dialog, int which) {
					if (ColorPickerDialog.this.listener != null) {
						ColorPickerDialog.this.listener.onOk(ColorPickerDialog.this, getColor());
					}
				}
			})
			.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
				@Override public void onClick(DialogInterface dialog, int which) {
					if (ColorPickerDialog.this.listener != null) {
						ColorPickerDialog.this.listener.onCancel(ColorPickerDialog.this);
					}
				}
			})
			.setOnCancelListener(new OnCancelListener() {
				// if back button is used, call back our listener.
				@Override public void onCancel(DialogInterface paramDialogInterface) {
					if (ColorPickerDialog.this.listener != null) {
						ColorPickerDialog.this.listener.onCancel(ColorPickerDialog.this);
					}

				}
			})
			.create();
		// kill all padding from the dialog window
		dialog.setView(view, 0, 0, 0, 0);

		// move cursor & target on first draw
		ViewTreeObserver vto = view.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override public void onGlobalLayout() {
				moveCursor();
				moveTarget();
				if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
			}
		});
	}

	protected void moveCursor() {
		float y = viewHue.getMeasuredHeight() - (getHue() * viewHue.getMeasuredHeight() / 360.f);
		if (y == viewHue.getMeasuredHeight()) y = 0.f;
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewCursor.getLayoutParams();
		layoutParams.leftMargin = (int) (viewHue.getLeft() - Math.floor(viewCursor.getMeasuredWidth() / 2) - viewContainer.getPaddingLeft());
		;
		layoutParams.topMargin = (int) (viewHue.getTop() + y - Math.floor(viewCursor.getMeasuredHeight() / 2) - viewContainer.getPaddingTop());
		;
		viewCursor.setLayoutParams(layoutParams);
	}

	protected void moveTarget() {
		float x = getSat() * viewSatVal.getMeasuredWidth();
		float y = (1.f - getVal()) * viewSatVal.getMeasuredHeight();
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewTarget.getLayoutParams();
		layoutParams.leftMargin = (int) (viewSatVal.getLeft() + x - Math.floor(viewTarget.getMeasuredWidth() / 2) - viewContainer.getPaddingLeft());
		layoutParams.topMargin = (int) (viewSatVal.getTop() + y - Math.floor(viewTarget.getMeasuredHeight() / 2) - viewContainer.getPaddingTop());
		viewTarget.setLayoutParams(layoutParams);
	}
	
	/**
	 * Added by IDKJava
	 * Sets specified color as old color ( like in instatation)
	 * @param color color to set to
	 */
	public void setColor(int color) {
		viewSatVal.setHue(getHue());
		viewOldColor.setBackgroundColor(color);
		viewNewColor.setBackgroundColor(color);		
		Color.colorToHSV(color, currentColorHsv);
	}

	private int getColor() {
		return Color.HSVToColor(currentColorHsv);
	}

	private float getHue() {
		return currentColorHsv[0];
	}

	private float getSat() {
		return currentColorHsv[1];
	}

	private float getVal() {
		return currentColorHsv[2];
	}

	private void setHue(float hue) {
		currentColorHsv[0] = hue;
	}

	private void setSat(float sat) {
		currentColorHsv[1] = sat;
	}

	private void setVal(float val) {
		currentColorHsv[2] = val;
	}

	public void show() {
		dialog.show();
	}

	public AlertDialog getDialog() {
		return dialog;
	}
}
