package com.example.gatherthemagic;

import java.io.IOException;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	
	static Button searchButton;
	static Button savedButton;
	static Button randomButton;
	
	static Context context;
	
	static JSONArray arrayOfCards;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(this);
		savedButton = (Button) findViewById(R.id.savedButton);
		savedButton.setOnClickListener(this);
		randomButton = (Button) findViewById(R.id.randomButton);
		randomButton.setOnClickListener(this);
		
		context = this;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		
		if (v.equals(this.searchButton))
		{
			Intent startSearchIntent = new Intent(this, SearchAndBrowseActivity.class);
			startActivity(startSearchIntent);
		}
		else if (v.equals(this.savedButton))
		{
			Intent startSavedIntent = new Intent(this, ResultsActivity.class);
			startActivity(startSavedIntent);
		}
		else if (v.equals(this.randomButton))
		{
			
			arrayOfCards = new JSONArray();
			
			String xmlSetName = "ths.json";
			
			try {
				JSONObject cardSet = new JSONObject(ResultsActivity.jsonToStringFromAssetFolder(xmlSetName, context));
				
				arrayOfCards = cardSet.getJSONArray("cards");
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			Random random = new Random();
			int randomCard = (random.nextInt(arrayOfCards.length()));
			
			try {
				JSONObject selectedCard = arrayOfCards.getJSONObject(randomCard);
				Intent startRandomIntent = new Intent(this, CardDetailsActivity.class);
				startRandomIntent.putExtra("jsonobject", selectedCard.toString());
				startActivity(startRandomIntent);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}

}
