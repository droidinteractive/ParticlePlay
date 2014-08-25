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
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.droidinteractive.particleplay.custom.CustomElementManagerActivity;
import com.droidinteractive.particleplay.game.ActionItem;
import com.droidinteractive.particleplay.game.Control;
import com.droidinteractive.particleplay.game.FileManager;
import com.droidinteractive.particleplay.game.MenuBar;
import com.droidinteractive.particleplay.game.QuickAction;
import com.droidinteractive.particleplay.game.SandView;
import com.droidinteractive.particleplay.game.SaveManager;
import com.droidinteractive.particleplay.preferences.Preferences;
import com.droidinteractive.particleplay.preferences.PreferencesActivity;
import com.droidinteractive.particleplay.R;
import com.droidinteractive.slidingdrawer.SlidingDrawer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity implements DialogInterface.OnCancelListener
{
	public static MainActivity instance = null;
	
    //Constants for dialog ids
    private static final int INTRO_MESSAGE = 1;
    public static final int ELEMENT_PICKER = 2;
    private static final int BRUSH_SIZE_PICKER = 3;

    //Constants for particles
    public static final char ERASER_ELEMENT = 2;
    public static final char NORMAL_ELEMENT = 3;
    public static final int NUM_BASE_ELEMENTS = 32;
    
    // Particles
 	public static final char pSand = 3;
 	public static final char pWater = 4;
 	public static final char pSteam = 5;	
 	public static final char pIce = 6;
 	public static final char pWall = 7;
 	public static final char pDrywall = 8;
 	public static final char pPlant = 9;
 	public static final char pFire = 10;
 	public static final char pLava = 11;
 	public static final char pStone = 12;
 	public static final char pOil = 13;
 	public static final char pC4 = 14;
 	public static final char pGunpowder = 25;
 	public static final char pFuse = 16;
 	public static final char pAcid = 17;
 	public static final char pSalt = 18;
 	public static final char pSaltWater = 19;
 	public static final char pGlass = 20;
 	public static final char pMud = 21;
 	public static final char pReplicator = 22;
 	public static final char pCoal = 23;
 	public static final char pAnts = 24;
 	public static final char pHydrogen = 15; 
 	public static final char pFlies = 26;
 	public static final char pWood = 27;
 	public static final char pTermite = 28;
 	public static final char pInsecticide = 29;
 	public static final char pElectricity = 30;
 	public static final char pMetal = 31;
 	
 	private int rotationIndex = 0;
 	
    //Constants for intents
    public static final char SAVE_STATE_ACTIVITY = 0;

    //Request code constants
    public static final int REQUEST_CODE_SELECT_SAVE = 0;
        
    //Constants for specials, collisions
    public static final int MAX_SPECIALS = 6;
    public static final int NUM_COLLISIONS = 12;

    static CharSequence[] baseElementsList;
    static ArrayList<String> elementsList;
    
    // Accelerometer support
    public static boolean accel = false;
    
    private static final int COLOR_SQUARE_SIZE = 40;

    public static boolean play;

    private SensorManager mSensorManager;

    public static final String PREFS_NAME = "MyPrefsfile";
    public static boolean shouldLoadDemo = false;

    public static boolean ui;

    public static ImageView image_header;
	public static AdView ad_view;
    public static MenuBar menu_bar;
    public static Control control;
    public static SandView sand_view;
    public static Button slideButton;
	public static SlidingDrawer slidingDrawer;
        
    public static String last_state_loaded = null;

    private SensorManager myManager;
    private List<Sensor> sensors;
    private Sensor accSensor;

    private static float mDPI; 
    
    private static final int ID_SAVE     = 1;
    private static final int ID_LOAD   = 2;
    private static final int ID_CLEAR = 3;
    private static final int ID_PREF   = 4;    
    private static final int ID_OK     = 5;

    /**
	 * Accessor
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends MainActivity> T get() {
		return (T) instance;
	}
	
    protected void onCreate(Bundle savedInstanceState) 
    {
        //Uses onCreate from the general Activity
        super.onCreate(savedInstanceState);
        
        MainActivity.instance = this;
                

        //Init the shared preferences and set the ui state
        Preferences.initSharedPreferences(this);
        Preferences.loadUIState();
            
        //Set Sensor + Manager
        WindowManager windowMgr = (WindowManager)this.getSystemService(WINDOW_SERVICE); 
        rotationIndex = windowMgr.getDefaultDisplay().getRotation(); 

        myManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensor = myManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setUpViews();
        
        AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        .addTestDevice("")
        .build();
        
        ad_view.loadAd(adRequest);
        //ad_view.setVisibility(View.VISIBLE);
        
        ad_view.setAdListener(new AdListener(){
        	public void onAdLoaded() {
        		ad_view.setVisibility(View.VISIBLE);
        	}
        	public void onAdFailedToLoad(int errorcode) {
        		ad_view.setVisibility(View.GONE);
        	}
        });
        

        elementsList = new ArrayList<String>();

        //Start unpaused
        play = true;
        menu_bar.setPlayState(play);

        // Get DPI from screen -- Sometimes this lies, add custom function to do this with hardcoded values
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        mDPI = dm.densityDpi;
        
        ActionItem saveItem     = new ActionItem(ID_SAVE, "Save Game", getResources().getDrawable(R.drawable.menu_down_arrow));
        ActionItem loadItem     = new ActionItem(ID_LOAD, "Load Game", getResources().getDrawable(R.drawable.menu_up_arrow));
        ActionItem clearItem    = new ActionItem(ID_CLEAR, "Clear Level", getResources().getDrawable(R.drawable.trash));
        ActionItem prefItem     = new ActionItem(ID_PREF, "Preferences", getResources().getDrawable(R.drawable.settings));
        ActionItem okItem		= new ActionItem(ID_OK, "OK", getResources().getDrawable(R.drawable.menu_ok));

        final QuickAction quickAction = new QuickAction(this, QuickAction.VERTICAL);
        
        //add action items into QuickAction
        quickAction.addActionItem(saveItem);
        quickAction.addActionItem(loadItem);
        quickAction.addActionItem(clearItem);
        quickAction.addActionItem(prefItem);
        quickAction.addActionItem(okItem);

        //Set listener for action item clicked
        quickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {                        
        	@Override
        	public void onItemClick(QuickAction source, int pos, int actionId) {                                
        		if (actionId == ID_SAVE) {
        			saveState();
        		} else if (actionId == ID_LOAD) {
        			loadState();
        		} else if (actionId == ID_PREF) {
        			startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
        		} else if (actionId == ID_CLEAR) {
        			clearScreen();        			
        		}
        	}
        });
        
        quickAction.setOnDismissListener(new QuickAction.OnDismissListener() {                        
                @Override
                public void onDismiss() {
                }
        });
        
        Button menubtn = (Button) this.findViewById(R.id.popup_menu_button);
        menubtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				quickAction.show(v);
				
			}
		});
    }

    private final SensorEventListener mySensorListener = new SensorEventListener()
    {
    	
        public void onSensorChanged(SensorEvent event)
        {     
        	if (rotationIndex == 0)
        	{
        		setXGravity(event.values[0]);
        		setYGravity(event.values[1]);
        	}
        	else
        	{
        		setXGravity(event.values[1]);
        		setYGravity(event.values[0] * -1);
        	}
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {}
    };

    @Override
    protected void onPause()
    {
        //Use the normal onPause
        super.onPause();
        
        // Pause our ads
        if(ad_view != null)
        	ad_view.pause();
        //Call onPause for the view
        sand_view.onPause();
                
        //Do a temp save
        saveTempState();
        //Set the preferences to indicate paused
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putBoolean("paused", true);
        editor.commit();

    }

    @Override
    protected void onResume()
    {
        //Use the super onResume
        super.onResume();
        
        // Resume ads
        if(ad_view != null)
        	ad_view.resume();
                
        //Load the settings shared preferences which deals with if we're resuming from pause or not
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        //Load the regular preferences into JNI
        Preferences.loadPreferences();

        //Register the accelerometer listener
        myManager.registerListener(mySensorListener, accSensor, SensorManager.SENSOR_DELAY_GAME);
                
        //Set up the elements list
        Resources res = getResources();
        baseElementsList = res.getTextArray(R.array.particles_list);
        elementsList.clear();
                
        // Add the base elements
        for (int i = 0; i < baseElementsList.length; i++)
        {
            elementsList.add(baseElementsList[i].toString());
        }
                
        // Load the custom elements
        try
        {
            // Open the file that is the first command line parameter
            FileInputStream fstream = new FileInputStream(FileManager.ROOT_DIR + FileManager.ELEMENTS_DIR + FileManager.ELEMENT_LIST_NAME + FileManager.LIST_EXT);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read file line by line
            while ((strLine = br.readLine()) != null)
            {
                FileInputStream tstream = new FileInputStream(FileManager.ROOT_DIR + FileManager.ELEMENTS_DIR + strLine);
                DataInputStream in2 = new DataInputStream(tstream);
                BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
                if ((strLine = br2.readLine()) != null)
                {
                    elementsList.add(strLine);
                }
                br2.close(); // Fixed resource leak on buffer
            }
            baseElementsList = elementsList.toArray(new CharSequence[elementsList.size()]);
            //Close the input stream
            
            in.close();
        }
        //Catch any exceptions
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
        }
                
                
        //Set up the file manager for saving and loading
        FileManager.intialize(this);

        //If we're resuming from a pause (not when it starts)
        if (settings.getBoolean("paused", false))
        {
            //Check to see if UI changed
            boolean oldui = ui;
            Preferences.loadUIState();
            if (ui != oldui)
            {
                setUpViews();
            }

            //Set the preferences to indicate unpaused
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("paused", false);
            editor.commit();
        }
        else if (settings.getBoolean("firstrun", true))
        {
            //Indicate that the demo should be loaded by nativeLoadState()
            shouldLoadDemo = true;
            //Unset firstrun
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstrun", false);
            editor.commit();

            //Also show the intro message
            DialogIntro();
                        
            //Finally, delete the temp save, in case there were save format changes
            SaveManager.deleteState("temp");
        }
        
        if (MainActivity.accel)
        {
        	MenuBar.accelerometer_button.setImageResource(R.drawable.accelerometer_on);
        }
        else
        {
        	MenuBar.accelerometer_button.setImageResource(R.drawable.accelerometer);
        }

        //Set the activity for Control so that we can call showDialog() from it
        control.setActivity(this);
        menu_bar.setActivity(this);

        //Call onResume() for view too
        sand_view.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
        
    public void DialogIntro() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        WebView wv = new WebView(this);
        wv.loadData(getResources().getString(R.string.app_intro), "text/html", "utf-8");
        wv.setBackgroundColor(Color.GRAY);
        builder.setView(wv).setCancelable(false).setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create(); // Actually create the message
        alert.show();
    	
    }
    
    public void DialogElementPicker() {
    	 AlertDialog.Builder builder = new AlertDialog.Builder(this); // Create a new one

         ListAdapter adapter = new ElementAdapter( this, (String[]) elementsList.toArray(new String[elementsList.size()]));

         builder.setTitle(R.string.particle_picker); // Set the title
         builder.setOnCancelListener(this);
         builder.setSingleChoiceItems( adapter, -1, new OnClickListener() {
                 
             public void onClick(DialogInterface dialog, int item)
             {
                 if (MenuBar.eraserOn)
                 {
                     MenuBar.setEraserOff();
                 }
                 
                 switch(item){
					case 0:
					{
						setElement((char) (pAcid));
						break;
					}
					case 1:
					{
						setElement((char) (pAnts));
						break;
					}
					case 2:
					{
						setElement((char) (pC4));
						break;
					}
					case 3:
					{
						setElement((char) (pCoal));
						break;
					}
					case 4:
					{
						setElement((char) (pDrywall));
						break;
					}
					case 5:
					{
						setElement((char) (pElectricity));
						break;
					}
					case 6:
					{
						setElement((char) (pFire));
						break;
					}
					case 7:
					{
						setElement((char) (pFlies));
						break;
					}
					case 8:
					{
						setElement((char) (pFuse));
						break;
					}
					case 9:
					{
						setElement((char) (pGlass));
						break;
					}
					case 10:
					{
						setElement((char) (pGunpowder));
						break;
					}
					case 11:
					{
						setElement((char) (pHydrogen));
						break;
					}
					case 12:
					{
						setElement((char) (pIce));
						break;
					}
					case 13:
					{
						setElement((char) (pInsecticide));
						break;
					}
					case 14:
					{
						setElement((char) (pLava));
						break;
					}
					case 15:
					{
						setElement((char) (pMetal));
						break;
					}
					case 16:
					{
						setElement((char) (pMud));
						break;
					}
					case 17:
					{
						setElement((char) (pOil));
						break;
					}
					case 18:
					{
						setElement((char) (pPlant));
						break;
					}
					case 19:
					{
						setElement((char) (pReplicator));
						break;
					}
					case 20:
					{
						setElement((char) (pSalt));
						break;
					}
					case 21:
					{
						setElement((char) (pSaltWater));
						break;
					}
					case 22:
					{
						setElement((char) (pSand));
						break;
					}
					case 23:
					{
						setElement((char) (pSteam));
						break;
					}
					case 24:
					{
						setElement((char) (pStone));
						break;
					}
					case 25:
					{
						setElement((char) (pTermite));
						break;
					}
					case 26:
					{
						setElement((char) (pWall));
						break;
					}
					case 27:
					{
						setElement((char) (pWater));
						break;
					}
					case 28:
					{
						setElement((char) (pWood));
						break;
					}
					default:
					{
						setElement((char) (item + NORMAL_ELEMENT));
						break;
					}
                 }
                 
                 setPlayState(play);
                 dialog.dismiss();
                 slidingDrawer.close();
             }
         });

         AlertDialog alert = builder.create(); // Create the dialog
         alert.show();
    }

    public void DialogBrushPicker() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this); // Declare the object
        builder.setTitle(R.string.brush_size_picker);
        builder.setOnCancelListener(this);
        builder.setItems(R.array.brush_size_list, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                if (item == 0)
                {
                    setBrushSize((char) 0);
                }
                else
                {
                    setBrushSize((char) java.lang.Math.pow(2, item - 1));
                }
                setPlayState(play);
            }
        });
        AlertDialog alert = builder.create(); // Create object
        alert.show();
    }
    @Override
    public void onCancel(DialogInterface dialog)
    {
        setPlayState(play);
    }

    public boolean onPrepareOptionsMenu(Menu menu) // Pops up when you press Menu
    {
        MenuInflater inflater = getMenuInflater();
        menu.clear();
        inflater.inflate(R.menu.options_menu_small, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
        case R.id.particle_picker:
        {
            setPlayState(false);
            DialogElementPicker();
            return true;
        }
        case R.id.brush_size_picker:
        {
            setPlayState(false);
            DialogBrushPicker();
            return true;
        }
        case R.id.clear_screen:             
        {           
            clearScreen();          
            return true;            
        }   
        case R.id.play_pause:
        {
            play = !play;
            setPlayState(play);
            return true;
        }
        case R.id.eraser:
        {
            setElement(ERASER_ELEMENT);
            return true;
        }
        case R.id.save:
        {
            saveState();
            return true;
        }
        case R.id.load:
        {
            loadState();
            return true;
        }
        case R.id.custom_particle_editor:
        {
            startActivity(new Intent(MainActivity.this, CustomElementManagerActivity.class));
            return true;
        }
        case R.id.preferences:
        {
            startActivity(new Intent(MainActivity.this, PreferencesActivity.class));
            return true;
        }
        case R.id.exit:
        {
            System.exit(0);
            return true;
        }
        }
        return false;
    }

    //Set up the views based on the state of ui
    private void setUpViews()
    {
        // Initialize the native library (SandView needs to make calls)
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        int versionCode;
        try
        {
            versionCode = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
        }
        catch (NameNotFoundException e)
        {
            versionCode = -1;
            e.printStackTrace();
        }
        nativeInit(androidId, versionCode);
                
        //Set the content view based on this variable
        setContentView(R.layout.main_activity_ui);

        //Set the new view and control box and menu bar to the stuff defined in layout
        image_header = (ImageView) findViewById(R.id.ppheader);
		ad_view = (AdView) findViewById(R.id.banner_adview);
        menu_bar = (MenuBar) findViewById(R.id.menu_bar);
        sand_view = (SandView) findViewById(R.id.sand_view);
        control = (Control) findViewById(R.id.control);
        slidingDrawer = (SlidingDrawer) this.findViewById(R.id.slidingDrawer);

        //Set the screen state for sand_view now that it's defined
        Preferences.loadScreenState();
    }

    //Trigger the SaveStateActivity
    public void saveState()
    {
        Intent tempIntent = new Intent(this, SaveStateActivity.class);
        startActivity(tempIntent);
    }

    //Trigger the LoadStateActivity
    public void loadState()
    {
        Intent tempIntent = new Intent(this, LoadStateActivity.class);
        startActivity(tempIntent);
    }
        
        
    /**
     * Definition of the list adapter...uses the View Holder pattern to
     * optimize performance.
     */
    private static class ElementAdapter extends ArrayAdapter<Object> {

        private static final int RESOURCE = R.layout.row;
        private LayoutInflater inflater;

        static class ViewHolder {
            TextView nameTxVw;
        }

        public ElementAdapter(Context context, String[] elements)
        {
            super(context, RESOURCE, elements);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;

            if ( convertView == null ) {
                // inflate a new view and setup the view holder for future use
                convertView = inflater.inflate( RESOURCE, null );

                holder = new ViewHolder();
                holder.nameTxVw =
                    (TextView) convertView.findViewById(R.id.particlename);
                convertView.setTag( holder );
            }  else {
                // view already defined, retrieve view holder
                holder = (ViewHolder) convertView.getTag();
            }

            String name = (String) getItem(position);
            int realElementPosition = position + NORMAL_ELEMENT;
            //int realElementPosition = 0;
            switch(position){
			case 0:
			{
				realElementPosition = pAcid;
				break;
			}
			case 1:
			{
				realElementPosition = pAnts;
				break;
			}
			case 2:
			{
				realElementPosition = pC4;
				break;
			}
			case 3:
			{
				realElementPosition = pCoal;
				break;
			}
			case 4:
			{
				realElementPosition = pDrywall;
				break;
			}
			case 5:
			{
				realElementPosition = pElectricity;
				break;
			}
			case 6:
			{
				realElementPosition = pFire;
				break;
			}
			case 7:
			{
				realElementPosition = pFlies;
				break;
			}
			case 8:
			{
				realElementPosition = pFuse;
				break;
			}
			case 9:
			{
				realElementPosition = pGlass;
				break;
			}
			case 10:
			{
				realElementPosition = pGunpowder;
				break;
			}
			case 11:
			{
				realElementPosition = pHydrogen;
				break;
			}
			case 12:
			{
				realElementPosition = pIce;
				break;
			}
			case 13:
			{
				realElementPosition = pInsecticide;
				break;
			}
			case 14:
			{
				realElementPosition = pLava;
				break;
			}
			case 15:
			{
				realElementPosition = pMetal;
				break;
			}
			case 16:
			{
				realElementPosition = pMud;
				break;
			}
			case 17:
			{
				realElementPosition = pOil;
				break;
			}
			case 18:
			{
				realElementPosition = pPlant;
				break;
			}
			case 19:
			{
				realElementPosition = pReplicator;
				break;
			}
			case 20:
			{
				realElementPosition = pSalt;
				break;
			}
			case 21:
			{
				realElementPosition = pSaltWater;
				break;
			}
			case 22:
			{
				realElementPosition = pSand;
				break;
			}
			case 23:
			{
				realElementPosition = pSteam;
				break;
			}
			case 24:
			{
				realElementPosition = pStone;
				break;
			}
			case 25:
			{
				realElementPosition = pTermite;
				break;
			}
			case 26:
			{
				realElementPosition = pWall;
				break;
			}
			case 27:
			{
				realElementPosition = pWater;
				break;
			}
			case 28:
			{
				realElementPosition = pWood;
				break;
			}
			

            }
            holder.nameTxVw.setText(name);
            int theColor = Color.rgb(getElementRed(realElementPosition), 
                                     getElementGreen(realElementPosition), getElementBlue(realElementPosition));
            ColorDrawable elementColor = new ColorDrawable(theColor);
                
            elementColor.setBounds(0, 0, toPx(COLOR_SQUARE_SIZE), toPx(COLOR_SQUARE_SIZE));
            holder.nameTxVw.setCompoundDrawables( elementColor, null, null, null );

            return convertView;
        }
    }

    //Converts dp to pixels
    public static int toPx(int dp) {
        return (int)((dp*mDPI)/160f);
    }        

    //JNI Functions
    //Save/load functions
    public static native char saveTempState();
    public static native char loadDemoState();
    public static native char removeTempSave();
        
    //General utility functions
    private static native void nativeInit(String udidString, int versionCode);
    public native void clearScreen();
        
    //Setters
    public static native void setPlayState(boolean playState);
    public static native void setElement(char element);
    public static native void setBrushSize(char brushSize);
    public static native void setFilterMode(char mode);
        
    //Getters
    public static native char getElement();
    public static native String getElementInfo(int index);
    public static native int getElementRed(int index);
    public static native int getElementGreen(int index);
    public static native int getElementBlue(int index);
        
    //Accelerometer related
    public static native void setXGravity(float xGravity);
    public static native void setYGravity(float yGravity);
        
    //Network related
    public static native void setUsername(char[] username);
    public static native void setPassword(char[] password);
    public static native char login();
    public static native char register();
    public static native void viewErr(); // Figure this out

    static
    {
        System.loadLibrary("particleplay");
    }
	
	// From NVIDIA http://developer.download.nvidia.com/tegra/docs/tegra_android_accelerometer_v5f.pdf
	private void canonicalOrientationToScreenOrientation(int displayRotation, float[] canVec, float[] screenVec) 
	{ 
	    final int axisSwap[][] = 
	    { 
	        { 1, -1, 0, 1 },   // ROTATION_0 
	        {-1, -1, 1, 0 },   // ROTATION_90 
	        {-1,  1, 0, 1 },   // ROTATION_180 
	        { 1,  1, 1, 0 }    // ROTATION_270 
	    };

	    final int[] as = axisSwap[displayRotation]; 
	    screenVec[0] = (float)as[0] * canVec[ as[2] ]; 
	    screenVec[1] = (float)as[1] * canVec[ as[3] ]; 
	    screenVec[2] = canVec[2]; 
	}
}
