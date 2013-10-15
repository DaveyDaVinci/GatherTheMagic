package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResultsActivity extends Activity implements OnItemClickListener{
	
	static ListView listView;
	
	static int retrievedSpinnerLocation;
	
	static int PAGE_CODE;
	
	static Context context;
	
	static ArrayAdapter<String> populatedAdapter;
	
	static JSONArray arrayOfCards;
	
	static JSONArray sortedCardsArray;
	
	static boolean savedDecks;
	
	static boolean savedCardsInDeck;
	
	static String searchedCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		context = this;
		
		listView = (ListView) findViewById(R.id.resultsList);
		
		Bundle retrievedData = getIntent().getExtras();
		retrievedSpinnerLocation = retrievedData.getInt("spinnerposition");
		PAGE_CODE = retrievedData.getInt("pagecode");
		searchedCard = retrievedData.getString("cardname");
		
		
		populatedAdapter = getAdapter(retrievedSpinnerLocation, PAGE_CODE);
		
		listView.setAdapter(populatedAdapter);
		
		listView.setOnItemClickListener(this);
		
		
		
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.results, menu);
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
			
	
			
			
			public static ArrayAdapter<String> getAdapter(int location, int pageCode)
			{
				
				
				ArrayAdapter<String> listViewAdapter = null;
				 
				if(pageCode == 0)
				{
					String[] rarityCodes = context.getResources().getStringArray(R.array.rarity);
					
					ArrayList<String> codesList = new ArrayList<String>(Arrays.asList(rarityCodes));
					
					
					arrayOfCards = SetSpinner.sortBySets(location, codesList, context);
					
					String testRarity = codesList.get(location);
					
					ArrayList<String> listOfCardNames = new ArrayList<String>();
					
					for (int i = 0; i < arrayOfCards.length(); i++)
					{
						try {
							JSONObject singleCard = arrayOfCards.getJSONObject(i);
							String cardRarity = singleCard.getString("rarity");
							String cardName = singleCard.getString("name");
							
							if (cardRarity.equals(testRarity))
							{
								listOfCardNames.add(cardName);
								if (sortedCardsArray == null)
								{
									sortedCardsArray = new JSONArray().put(singleCard);
								}
								else
								{
									sortedCardsArray.put(singleCard);
								}
							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					String[] theList = listOfCardNames.toArray(new String[listOfCardNames.size()]);
					
					listViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, theList);
					return listViewAdapter;
					
				}
				else if (pageCode == 1)
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
					
					
					ArrayList<String> listOfCards = new ArrayList<String>();
					
					
					for (int i = 0; i < arrayOfCards.length(); i++)
					{
						try {
							JSONObject singleCard = arrayOfCards.getJSONObject(i);
							String cardName = singleCard.getString("name");
							
							
							listOfCards.add(cardName);
							if (sortedCardsArray == null)
							{
								sortedCardsArray = new JSONArray().put(singleCard);
							}
							else
							{
								sortedCardsArray.put(singleCard);
							}
							
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
					
					String[] theList = listOfCards.toArray(new String[listOfCards.size()]);
					
					listViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, theList);
					return listViewAdapter;
						
				}
				else if (pageCode == 2)
				{
					
					JSONObject cardSet;
					try {
						cardSet = new JSONObject(ResultsActivity.jsonToStringFromAssetFolder("ths.json", context));
						arrayOfCards = cardSet.getJSONArray("cards");
						
						ArrayList<String> listOfCardNames = new ArrayList<String>();
						
						for (int i = 0; i < arrayOfCards.length(); i++)
						{
							try {
								JSONObject singleCard = arrayOfCards.getJSONObject(i);
								String cardName = singleCard.getString("name");
								
								if (cardName.equals(searchedCard))
								{
									listOfCardNames.add(cardName);
									if (sortedCardsArray == null)
									{
										sortedCardsArray = new JSONArray().put(singleCard);
									}
									else
									{
										sortedCardsArray.put(singleCard);
									}
								}
								
								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						String[] theList = listOfCardNames.toArray(new String[listOfCardNames.size()]);
						
						listViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, theList);
						return listViewAdapter;
						
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
				
				
				return listViewAdapter;
				
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
			public void onItemClick(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				
				Log.i("YAY", Integer.toString(pos));
				
				try {
					JSONObject selectedCard = sortedCardsArray.getJSONObject(pos);
					
					sortedCardsArray = new JSONArray();
					
					Intent detailsActivity = new Intent(context, CardDetailsActivity.class);
					detailsActivity.putExtra("jsonobject", selectedCard.toString());
					startActivity(detailsActivity);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

}
