package com.example.gatherthemagic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LifeTrackerActivity extends Activity implements OnClickListener{
	
	static TextView player1;
	static TextView player2;
	static TextView player3;
	static TextView player4;
	static TextView player1Life;
	static TextView player2Life;
	static TextView player3Life;
	static TextView player4Life;
	
	static Button addButton1;
	static Button addButton2;
	static Button addButton3;
	static Button addButton4;
	
	static Button subtractButton1;
	static Button subtractButton2;
	static Button subtractButton3;
	static Button subtractButton4;
	
	static Button addPlayersButton;
	static Button deleteButton;
	static Button savePlayersButton;
	
	static Boolean player1Visible;
	static Boolean player2Visible;
	static Boolean player3Visible;
	static Boolean player4Visible;
	
	static Boolean player1Active;
	static Boolean player2Active;
	static Boolean player3Active;
	static Boolean player4Active;
	
	static int numberOfPlayers;
	
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_life_tracker);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		player1 = (TextView) findViewById(R.id.player1);
		player2 = (TextView) findViewById(R.id.player2);
		player3 = (TextView) findViewById(R.id.player3);
		player4 = (TextView) findViewById(R.id.player4);
		player1Life = (TextView) findViewById(R.id.player1Life);
		player2Life = (TextView) findViewById(R.id.player2Life);
		player3Life = (TextView) findViewById(R.id.player3Life);
		player4Life = (TextView) findViewById(R.id.player4Life);
		
		addButton1 = (Button) findViewById(R.id.addButton1);
		addButton1.setOnClickListener(this);
		addButton2 = (Button) findViewById(R.id.addButton2);
		addButton2.setOnClickListener(this);
		addButton3 = (Button) findViewById(R.id.addButton3);
		addButton3.setOnClickListener(this);
		addButton4 = (Button) findViewById(R.id.addButton4);
		addButton4.setOnClickListener(this);
		
		subtractButton1 = (Button) findViewById(R.id.subtractButton1);
		subtractButton1.setOnClickListener(this);
		subtractButton2 = (Button) findViewById(R.id.subtractButton2);
		subtractButton2.setOnClickListener(this);
		subtractButton3 = (Button) findViewById(R.id.subtractButton3);
		subtractButton3.setOnClickListener(this);
		subtractButton4 = (Button) findViewById(R.id.subtractButton4);
		subtractButton4.setOnClickListener(this);
		
		addPlayersButton = (Button) findViewById(R.id.addPlayerButton);
		addPlayersButton.setOnClickListener(this);
		deleteButton = (Button) findViewById(R.id.deletePlayers);
		deleteButton.setOnClickListener(this);
		savePlayersButton = (Button) findViewById(R.id.savePlayerInformation);
		savePlayersButton.setOnClickListener(this);
		
		context = this;
		
		numberOfPlayers = 1;
		
		player1Visible = true;
		player2Visible = false;
		player3Visible = false;
		player4Visible = false;
		
		player1Active = false;
		player2Active = false;
		player3Active = false;
		player4Active = false;
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		try {
			
			String savedValues = preferences.getString("savedvalues", "[]");
			JSONArray storedValues = new JSONArray(savedValues);
			
			Log.i("test", storedValues.toString());
			
			if (storedValues.equals("[]"))
			{
				
			}
			else
			{
				for (int i = 0; i < storedValues.length(); i++)
				{
					JSONObject selectedObject = storedValues.getJSONObject(i);
					if (i == 0)
					{
						player1Visible = selectedObject.getBoolean("player1visible");
						player1Life.setText(selectedObject.getString("player1life"));
						player1Active = selectedObject.getBoolean("player1active");
						numberOfPlayers = selectedObject.getInt("numberofplayers");
						
					}
					else if (i == 1)
					{
						
						player2Visible = selectedObject.getBoolean("player2visible");
						player2Life.setText(selectedObject.getString("player2life"));
						player2Active = selectedObject.getBoolean("player2active");
						numberOfPlayers = selectedObject.getInt("numberofplayers");
						
						player2.setVisibility(View.VISIBLE);
						player2Life.setVisibility(View.VISIBLE);
						addButton2.setVisibility(View.VISIBLE);
						subtractButton2.setVisibility(View.VISIBLE);
					}
					else if (i == 2)
					{
						player3Visible = selectedObject.getBoolean("player3visible");
						player3Life.setText(selectedObject.getString("player3life"));
						player3Active = selectedObject.getBoolean("player3active");
						numberOfPlayers = selectedObject.getInt("numberofplayers");
						
						player3.setVisibility(View.VISIBLE);
						player3Life.setVisibility(View.VISIBLE);
						addButton3.setVisibility(View.VISIBLE);
						subtractButton3.setVisibility(View.VISIBLE);
					}
					else if (i == 3)
					{
						player4Visible = selectedObject.getBoolean("player4visible");
						player4Life.setText(selectedObject.getString("player4life"));
						player4Active = selectedObject.getBoolean("player4active");
						numberOfPlayers = selectedObject.getInt("numberofplayers");
						
						player4.setVisibility(View.VISIBLE);
						player4Life.setVisibility(View.VISIBLE);
						addButton4.setVisibility(View.VISIBLE);
						subtractButton4.setVisibility(View.VISIBLE);
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.life_tracker, menu);
		return true;
	}
	
	//Creates a switch case conditional to handle whether the user selects items in the action bar
			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
			    // Handle presses on the action bar items
			    switch (item.getItemId()) {
			        case R.id.homeButton:
			        	Intent startIntent = new Intent(this, MainActivity.class);
						startActivity(startIntent);
						
			            return true;
			            
			        case R.id.searchButton:
			        	Intent startSearchIntent = new Intent(this, SearchAndBrowseActivity.class);
						startActivity(startSearchIntent);
					
						return true;
						
			        case R.id.savedButton:
			        	Intent startSavedIntent = new Intent(this, ResultsActivity.class);
						startSavedIntent.putExtra("pagecode", 3);
						startActivity(startSavedIntent);
						return true;
						
			        case R.id.mapButton:
			        	Intent startMapIntent = new Intent(this, MapActivity.class);
						startActivity(startMapIntent);
					
						return true;
						
			        case R.id.priceButton:
			        	Intent startPriceIntent = new Intent(this, PriceActivity.class);
						startActivity(startPriceIntent);
					
						return true;
						
			        case R.id.lifeTrackerButton:
			        	Intent startLifeIntent = new Intent(this, LifeTrackerActivity.class);
						startActivity(startLifeIntent);
					
						return true;
			        	
			        
			        default:
			            return super.onOptionsItemSelected(item);
			    }
			    
			}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(LifeTrackerActivity.addButton1))
		{
			int player1LifeInt = Integer.parseInt(player1Life.getText().toString());
			player1LifeInt ++;
			player1Life.setText(String.valueOf(player1LifeInt));
			player1Active = true;
			
		} 
		else if (v.equals(LifeTrackerActivity.addButton2))
		{
			int player2LifeInt = Integer.parseInt(player2Life.getText().toString());
			player2LifeInt ++;
			player2Life.setText(String.valueOf(player2LifeInt));
		}
		else if (v.equals(LifeTrackerActivity.addButton3))
		{
			int player3LifeInt = Integer.parseInt(player3Life.getText().toString());
			player3LifeInt ++;
			player3Life.setText(String.valueOf(player3LifeInt));
		}
		else if (v.equals(LifeTrackerActivity.addButton4))
		{
			int player4LifeInt = Integer.parseInt(player4Life.getText().toString());
			player4LifeInt ++;
			player4Life.setText(String.valueOf(player4LifeInt));
		}
		
		
		else if (v.equals(LifeTrackerActivity.subtractButton1))
		{
			int player1LifeInt = Integer.parseInt(player1Life.getText().toString());
			if (player1LifeInt > 0)
			{
				player1LifeInt --;
				player1Life.setText(String.valueOf(player1LifeInt));
			}
		}
		else if (v.equals(LifeTrackerActivity.subtractButton2))
		{
			int player2LifeInt = Integer.parseInt(player2Life.getText().toString());
			if (player2LifeInt > 0)
			{
				player2LifeInt --;
				player2Life.setText(String.valueOf(player2LifeInt));
			}
		}
		else if (v.equals(LifeTrackerActivity.subtractButton3))
		{
			int player3LifeInt = Integer.parseInt(player3Life.getText().toString());
			if (player3LifeInt > 0)
			{
				player3LifeInt --;
				player3Life.setText(String.valueOf(player3LifeInt));
			}
		}
		else if (v.equals(LifeTrackerActivity.subtractButton4))
		{
			int player4LifeInt = Integer.parseInt(player4Life.getText().toString());
			if (player4LifeInt > 0)
			{
				player4LifeInt --;
				player4Life.setText(String.valueOf(player4LifeInt));
			}
		}
		else if (v.equals(LifeTrackerActivity.addPlayersButton))
		{
			
			
			
			if (player1Visible == true)
			{
				player2.setVisibility(View.VISIBLE);
				player2Life.setVisibility(View.VISIBLE);
				addButton2.setVisibility(View.VISIBLE);
				subtractButton2.setVisibility(View.VISIBLE);
				player2Visible = true;
				player1Visible = false;
				player2Active = true;
				numberOfPlayers = 2;
				return;
			}
			

			if (player2Visible == true)
			{
				player3.setVisibility(View.VISIBLE);
				player3Life.setVisibility(View.VISIBLE);
				addButton3.setVisibility(View.VISIBLE);
				subtractButton3.setVisibility(View.VISIBLE);
				player3Visible = true;
				player2Visible = false;
				player3Active = true;
				numberOfPlayers = 3;
				return;
			}
			
			if (player3Visible == true)
			{
				
				player4.setVisibility(View.VISIBLE);
				player4Life.setVisibility(View.VISIBLE);
				addButton4.setVisibility(View.VISIBLE);
				subtractButton4.setVisibility(View.VISIBLE);
				player4Visible = true;
				player3Visible = false;
				player4Active = true;
				numberOfPlayers = 4;
				return;
				
			}
			
			
			
		}
		else if (v.equals(LifeTrackerActivity.deleteButton))
		{
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	        SharedPreferences.Editor editor = preferences.edit();
	        
	        editor.remove("savedvalues");
	        
	        editor.commit();
			
			Intent activityRefresh = new Intent(this, LifeTrackerActivity.class);
			finish();
			startActivity(activityRefresh);
		}
		
		else if (v.equals(LifeTrackerActivity.savePlayersButton))
		{
			
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
	        SharedPreferences.Editor editor = preferences.edit();
	        
	        JSONObject player1 = new JSONObject();
	        JSONObject player2 = new JSONObject();
	        JSONObject player3 = new JSONObject();
	        JSONObject player4 = new JSONObject();
	        
	        if (numberOfPlayers == 1)
	        {
	        	
	        	try {
					player1.put("player1life", player1Life.getText().toString());
					player1.put("player1visible", true);
					player1.put("player1active", true);
					player1.put("numberofplayers", numberOfPlayers);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        else if (numberOfPlayers == 2)
	        {
	        	
	        	
	        	try {
					player1.put("player1life", player1Life.getText().toString());
					player1.put("player1visible", false);
					player1.put("player1active", true);
					player1.put("numberofplayers", numberOfPlayers);
					
					player2.put("player2life", player2Life.getText().toString());
					player2.put("player2visible", true);
					player2.put("player2active", true);
					player2.put("numberofplayers", numberOfPlayers);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        		
	        }
	        
	        else if (numberOfPlayers == 3)
	        {
	        	try {
	        		
	        		
	        		
					player1.put("player1life", player1Life.getText().toString());
					player1.put("player1visible", false);
					player1.put("player1active", true);
					player1.put("numberofplayers", numberOfPlayers);
					
					player2.put("player2life", player2Life.getText().toString());
					player2.put("player2visible", false);
					player2.put("player2active", true);
					player2.put("numberofplayers", numberOfPlayers);
					
					player3.put("player3life", player3Life.getText().toString());
					player3.put("player3visible", true);
					player3.put("player3active", true);
					player3.put("numberofplayers", numberOfPlayers);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        }
	        
	        
	        else if (numberOfPlayers == 4)
	        {
	        	try {
	        		
					player1.put("player1life", player1Life.getText().toString());
					player1.put("player1visible", false);
					player1.put("player1active", true);
					player1.put("numberofplayers", numberOfPlayers);
					
					player2.put("player2life", player2Life.getText().toString());
					player2.put("player2visible", false);
					player2.put("player2active", true);
					player2.put("numberofplayers", numberOfPlayers);
					
					player3.put("player3life", player3Life.getText().toString());
					player3.put("player3visible", false);
					player3.put("player3active", true);
					player3.put("numberofplayers", numberOfPlayers);
					
					player4.put("player4life", player4Life.getText().toString());
					player4.put("player4visible", true);
					player4.put("player4active", true);
					player4.put("numberofplayers", numberOfPlayers);
	        } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	        
	        JSONArray tempArray = new JSONArray();
	        JSONArray saveJSONInformation = new JSONArray();
	        
	        tempArray.put(player1);
	        tempArray.put(player2);
	        tempArray.put(player3);
	        tempArray.put(player4);
	        
	        for (int i = 0; i < tempArray.length(); i++)
	        {
	        	try {
					JSONObject targetObject = tempArray.getJSONObject(i);
					if (!targetObject.toString().equals("{}"))
					{
						saveJSONInformation.put(targetObject);
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        
	        
	        
	        editor.putString("savedvalues", saveJSONInformation.toString());
	        
	        editor.commit();
	        
	        
			
		}
		
		
	}
	
	@Override
    protected void onStop() {
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
        
        
        
	}

}
