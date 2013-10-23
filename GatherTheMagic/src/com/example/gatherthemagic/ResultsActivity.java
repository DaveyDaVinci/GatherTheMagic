package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import spinnerClasses.SetSpinner;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class ResultsActivity extends Activity implements OnItemClickListener, OnClickListener{
	
	static ListView listView;
	
	static int retrievedSpinnerLocation;
	static int PAGE_CODE;
	
	static EditText deckNameEditText;
	
	static Context context;
	
	static ArrayAdapter<String> populatedAdapter;
	
	static JSONArray arrayOfCards;
	static JSONArray sortedCardsArray;
	
	static boolean savedDecks;
	static boolean savedCardsInDeck;
	
	static String searchedCard;
	static String DECK_NAMES;
	static ArrayList<String> savedCards;
	static String setName;
	
	static Button addDecksButton;
	static Button addDecksPopUpButton;
	static Button deleteDecksButton;
	
	static PopupWindow addMenu;
	
	static LinearLayout thisLinearLayout;
	static RelativeLayout thisRelativeLayout;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		context = this;
		DECK_NAMES = "decknames";
		
		listView = (ListView) findViewById(R.id.resultsList);
		
		addDecksButton = (Button) findViewById(R.id.resultsActivityNewDeckButton);
		addDecksButton.setOnClickListener(this);
		deleteDecksButton = (Button) findViewById(R.id.resultsActivityDeleteDeckButton);
		deleteDecksButton.setOnClickListener(this);
		
		Bundle retrievedData = getIntent().getExtras();
		retrievedSpinnerLocation = retrievedData.getInt("spinnerposition");
		PAGE_CODE = retrievedData.getInt("pagecode");
		searchedCard = retrievedData.getString("cardname");
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
		if (PAGE_CODE == 3)
		{
			Bitmap backgroundBM = BitmapFactory.decodeResource(getResources(), R.drawable.background1);
			LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View pview = inflater.inflate(R.layout.add_deck_results_popview,(ViewGroup)findViewById(R.layout.activity_results));
			addMenu = new PopupWindow(pview);
			addMenu.setFocusable(true);
			addMenu.setOutsideTouchable(true);
			addMenu.setBackgroundDrawable(new BitmapDrawable(getResources(), backgroundBM));
			
			
			
			
			if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
			{
				thisRelativeLayout = new RelativeLayout(this);
			}
			else
			{
				thisLinearLayout = new LinearLayout(this);
			}
			
			
			deckNameEditText = (EditText) pview.findViewById(R.id.addDeckEditText);
			
			addDecksPopUpButton = (Button) pview.findViewById(R.id.addDeckPopUpButton);
			
			addDecksPopUpButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					String deckName = deckNameEditText.getText().toString();
					
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
					SharedPreferences.Editor editor = preferences.edit();
					
					JSONObject deckAsJSON = new JSONObject();
					try {
						deckAsJSON.put(DECK_NAMES, deckName);
						
						JSONObject defaultErrorObject = new JSONObject();
						defaultErrorObject.put("error", "error");
						
						JSONArray loadedJson = new JSONArray(preferences.getString(DECK_NAMES, "[]"));
						
						JSONArray editedArray = new JSONArray();
						
						if (loadedJson.equals("[]"))
						{
							
							editedArray.put(deckAsJSON);
							editor.putString(DECK_NAMES, editedArray.toString());
						}
						else
						{
							
							loadedJson.put(deckAsJSON);
							editor.putString(DECK_NAMES, loadedJson.toString());
						}
						
						
						editor.commit();
						
						
						Log.i(deckName, preferences.getString(DECK_NAMES, "error"));
						
						
						populatedAdapter = getAdapter(retrievedSpinnerLocation, PAGE_CODE);
						populatedAdapter.notifyDataSetChanged();
						listView.setAdapter(populatedAdapter);
						
						addMenu.dismiss();
						
						deckNameEditText.setText("");
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
				
			});
			
			
			
			
		}
		
		
		populatedAdapter = getAdapter(retrievedSpinnerLocation, PAGE_CODE);
		
		listView.setAdapter(populatedAdapter);
		
		listView.setOnItemClickListener(this);
		
		
		
		
		
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
					
					String jsonString = sortedCardsArray.toString();
					saveclasses.SaveSingleton.storeJSONStringData(context, "browsecache", jsonString);
					
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
					
					
					String jsonString = sortedCardsArray.toString();
					saveclasses.SaveSingleton.storeJSONStringData(context, "browsecache", jsonString);
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
						
						String jsonString = sortedCardsArray.toString();
						saveclasses.SaveSingleton.storeJSONStringData(context, "browsecache", jsonString);
						
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
				
				else if (PAGE_CODE == 3)
				{
					addDecksButton.setVisibility(View.VISIBLE);
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
					
					try {
						JSONArray loadedJson = new JSONArray(preferences.getString(DECK_NAMES, "[]"));
						
						ArrayList<String> savedCards = new ArrayList<String>();
						
						if (!loadedJson.equals("[]"))
						{
							for (int i = 0; i < loadedJson.length(); i++)
							{
								JSONObject singleCard = loadedJson.getJSONObject(i);
								savedCards.add(singleCard.getString(DECK_NAMES));
							}
							
							String[] theList = savedCards.toArray(new String[savedCards.size()]);
							
							listViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, theList);
							return listViewAdapter;
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else if (PAGE_CODE == 4)
				{
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
					
					try {
						JSONArray loadedSets = new JSONArray(preferences.getString(DECK_NAMES, "[]"));
						if (!loadedSets.toString().equals("[]"))
						{
							JSONObject loadedSet = loadedSets.getJSONObject(location);
							setName = loadedSet.getString(DECK_NAMES);
							
							JSONArray loadedCardsForSet = new JSONArray(preferences.getString(setName, "[]"));
							if (!loadedCardsForSet.equals("[]"))
							{
								savedCards = new ArrayList<String>();
								
								for (int i = 0; i < loadedCardsForSet.length(); i++)
								{
									JSONObject singleCard = loadedCardsForSet.getJSONObject(i);
									savedCards.add(singleCard.getString("name"));
								}
								
								String[] theList = savedCards.toArray(new String[savedCards.size()]);
								
								listViewAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, theList);
								return listViewAdapter;
							}
						}
						
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				
				if (PAGE_CODE < 3)
				{
					try {
						
						String jsonString = saveclasses.SaveSingleton.readStringData(context, "browsecache");
						
						JSONArray storedData = new JSONArray(jsonString);
						JSONObject selectedCard = storedData.getJSONObject(pos);
						
						sortedCardsArray = new JSONArray();
						
						Intent detailsActivity = new Intent(context, CardDetailsActivity.class);
						detailsActivity.putExtra("jsonobject", selectedCard.toString());
						startActivity(detailsActivity);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (PAGE_CODE == 3)
				{
					PAGE_CODE = 4;
					addDecksButton.setVisibility(View.INVISIBLE);
					deleteDecksButton.setVisibility(View.VISIBLE);
					populatedAdapter = getAdapter(pos, PAGE_CODE);
					populatedAdapter.notifyDataSetChanged();
					listView.setAdapter(populatedAdapter);
					
				}
				else if (PAGE_CODE == 4)
				{
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
					
					//String selectedSet = savedCards.get(pos);
					try {
						JSONArray loadedSets = new JSONArray(preferences.getString(setName, "[]"));
						if (!loadedSets.toString().equals("[]"))
						{
							Log.i("TESTIFCLICKED", "TESTSUCCESSFUL");
							JSONObject loadedSet = loadedSets.getJSONObject(pos);
							
							Intent detailsActivity = new Intent(context, CardDetailsActivity.class);
							detailsActivity.putExtra("jsonobject", loadedSet.toString());
							detailsActivity.putExtra("setname", setName);
							detailsActivity.putExtra("camefromdecks", true);
							startActivity(detailsActivity);
						}
						
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
				
				
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (v.equals(ResultsActivity.addDecksButton))
				{
					if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
					{
						addMenu.showAtLocation(thisRelativeLayout, Gravity.CENTER, 20, 20);
						addMenu.update(10,10,700,150);
						
						
					}
					else
					{
						addMenu.showAtLocation(thisLinearLayout, Gravity.CENTER, 20, 20);
						
						addMenu.update(10,10,700,150);
						
						
					}
				}
				else if (v.equals(ResultsActivity.deleteDecksButton))
				{
					
					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
					SharedPreferences.Editor editor = preferences.edit();
					
					try {
						JSONArray loadedCardsFromSets = new JSONArray(preferences.getString(setName, "[]"));
						JSONArray loadedSets = new JSONArray(preferences.getString(DECK_NAMES, "[]"));
						JSONArray newSets = new JSONArray();
						if (!loadedSets.toString().equals("[]"))
						{
							for (int i = 0; i < loadedSets.length(); i++)
							{
								JSONObject loadedSet = loadedSets.getJSONObject(i);
								
								if (!loadedSet.getString(DECK_NAMES).equals(setName))
								{
									newSets.put(loadedSet);
								}
							}
							
							editor.remove(setName);
							editor.remove(DECK_NAMES);
							editor.commit();
							editor.putString(DECK_NAMES, newSets.toString());
							editor.commit();
							
							Intent detailsActivity = new Intent(context, ResultsActivity.class);
							
							detailsActivity.putExtra("pagecode", 3);
							startActivity(detailsActivity);
						}
						
						
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}

}
