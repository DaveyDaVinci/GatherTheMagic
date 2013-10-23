package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import spinnerClasses.SetSpinner;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchAndBrowseActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	
	static Spinner browseSpinner;
	static Spinner browseSpinner2;
	
	static EditText cardSearch;
	static Button cardSearchButton;
	
	static Button browseButton;
	
	static Context context;
	
	static int SPINNER_MARKER;
	
	static int SPINNER_POSITION;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_and_browse);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		context = this;
		
		cardSearch = (EditText) findViewById(R.id.searchBrowseSearchBox);
		cardSearchButton = (Button) findViewById(R.id.searchBrowseButton);
		cardSearchButton.setOnClickListener(this);
		
		browseSpinner = (Spinner) findViewById(R.id.browse);
		browseSpinner2 = (Spinner) findViewById(R.id.browse2);
		browseSpinner2.setVisibility(View.INVISIBLE);
		
		browseButton = (Button) findViewById(R.id.browseButton);
		browseButton.setOnClickListener(this);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.browseTypes, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		browseSpinner.setAdapter(adapter);
		browseSpinner.setOnItemSelectedListener(this);
		
		
		//JSON Test
		try {
			JSONObject test1 = new JSONObject(jsonToStringFromAssetFolder("lea.json", this));
			
			JSONArray array = test1.getJSONArray("cards");
			
			ArrayList<String> listOfCardNames = new ArrayList<String>();
			
			for (int i = 0; i < array.length(); i++)
			{
				JSONObject testCardName = array.getJSONObject(i);
				String testName = testCardName.getString("name");
				
				listOfCardNames.add(testName);
				
			}
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_and_browse, menu);
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
			
			public static String jsonToStringFromAssetFolder(String fileName,Context context) throws IOException {
		        AssetManager manager = context.getAssets();
		        InputStream file = manager.open(fileName);

		        byte[] data = new byte[file.available()];
		        file.read(data);
		        file.close();
		        return new String(data);
		    }

			
			
			
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				
				 
				
				
				if (parent.getItemIdAtPosition(pos) == 0)
				{
					
					browseSpinner2.setVisibility(View.VISIBLE);
					ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.rarity, android.R.layout.simple_spinner_item);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					browseSpinner2.setAdapter(adapter);
					browseSpinner2.setOnItemSelectedListener(new SetSpinner());
					SPINNER_MARKER = 0;
					
					
				} 
				else if (parent.getItemIdAtPosition(pos) == 1)
				{
					
					browseSpinner2.setVisibility(View.INVISIBLE);
					SPINNER_MARKER = 1;
				} 
				
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}

			
			
			//Button 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (v.equals(SearchAndBrowseActivity.browseButton))
				{
					
					
					if (SPINNER_MARKER == 0)
					{
						Log.i("Spinner Position", Integer.toString(SPINNER_POSITION));
						
						Intent resultsActivity = new Intent(context, ResultsActivity.class);
						resultsActivity.putExtra("spinnerposition", SPINNER_POSITION);
						resultsActivity.putExtra("pagecode", 0);
						startActivity(resultsActivity);
						
						
					}
					
					if (SPINNER_MARKER == 1)
					{
						Intent resultsActivity = new Intent(context, ResultsActivity.class);
						resultsActivity.putExtra("pagecode", 1);
						startActivity(resultsActivity);
					}
				}
				else if (v.equals(SearchAndBrowseActivity.cardSearchButton))
				{
					String searchedCardName = cardSearch.getText().toString();
					
					Intent resultsActivity = new Intent(context, ResultsActivity.class);
					resultsActivity.putExtra("spinnerposition", 0);
					resultsActivity.putExtra("pagecode", 2);
					resultsActivity.putExtra("cardname", searchedCardName);
					startActivity(resultsActivity);
				}
				
			}
			
			public static int getSpinnerPosition(int position)
			{
				SPINNER_POSITION = position;
				return SPINNER_POSITION;
			}

}
