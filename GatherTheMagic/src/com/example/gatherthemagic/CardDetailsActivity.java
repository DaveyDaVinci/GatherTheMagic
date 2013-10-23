package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CardDetailsActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	static TextView cardTitle;
	static TextView cardCost;
	static TextView cardType;
	static TextView cardRarity;
	static TextView cardText;
	static TextView cardFlavorText;
	static TextView cardArtist;
	static TextView cardPower;
	
	static PopupWindow addMenu;
	
	static ImageView retrievedCardImage;
	
	static JSONObject returnedCard;
	
	static String multiId;
	
	static Button addCardButton;
	static Button deleteCardButton;
	
	static URL imageUrl;
	
	static Context context;
	
	static String DECK_NAMES = "decknames";
	static String CARDS_LIST;
	
	static ListView deckListView;
	
	static LinearLayout thisLinearLayout;
	//static LinearLayout popUpView;
	static RelativeLayout thisRelativeLayout;
	
	static ArrayAdapter<String> populatedAdapter;
	
	static Boolean cameFromDeck;
	static String setName;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_details);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		Bundle retrievedData = getIntent().getExtras();
		
		context = this;
		
		
		
		
		
		Bitmap backgroundBM = BitmapFactory.decodeResource(getResources(), R.drawable.background1);
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pview = inflater.inflate(R.layout.pop_up_view,(ViewGroup)findViewById(R.layout.activity_card_details));
		addMenu = new PopupWindow(pview);
		addMenu.setFocusable(true);
		addMenu.setOutsideTouchable(true);
		addMenu.setBackgroundDrawable(new BitmapDrawable(getResources(), backgroundBM));
		
		deckListView = (ListView) pview.findViewById(R.id.popUpListview);
		
		populatedAdapter = getAdapter();
		deckListView.setAdapter(populatedAdapter);
		deckListView.setOnItemClickListener(this);
		
		
		if ((getResources().getConfiguration().screenLayout &      Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
		{
			thisRelativeLayout = new RelativeLayout(this);
		}
		else
		{
			thisLinearLayout = new LinearLayout(this);
		}
		
		
		
		try {
			returnedCard = new JSONObject(retrievedData.getString("jsonobject"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addCardButton = (Button) findViewById(R.id.addCard);
		addCardButton.setOnClickListener(this);
		
		deleteCardButton = (Button) findViewById(R.id.deleteCard);
		deleteCardButton.setOnClickListener(this);
		deleteCardButton.setVisibility(View.INVISIBLE);
		setName = "ERROR";
		cameFromDeck = retrievedData.getBoolean("camefromdecks");
		if (cameFromDeck == true)
		{
			deleteCardButton.setVisibility(View.VISIBLE);
			setName = retrievedData.getString("setname", "ERROR");
		}
		
		Log.i("Test data", retrievedData.getString("jsonobject"));
		cardCost = (TextView) findViewById(R.id.cardCost);
		cardType = (TextView) findViewById(R.id.cardType);
		cardRarity = (TextView) findViewById(R.id.cardRarity);
		cardText = (TextView) findViewById(R.id.cardText);
		cardFlavorText = (TextView) findViewById(R.id.cardFlavorText);
		cardArtist = (TextView) findViewById(R.id.cardArtist);
		cardPower = (TextView) findViewById(R.id.cardPower);
		retrievedCardImage = (ImageView) findViewById(R.id.cardView);
		
		cardTitle = (TextView) findViewById(R.id.cardTitle);
		try {
			if (returnedCard.has("name"))
			{
				cardTitle.setText(returnedCard.getString("name"));
				cardTitle.setVisibility(View.VISIBLE);
			}
			else
			{
				cardTitle.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("manaCost"))
			{
				cardCost.setText(returnedCard.getString("manaCost"));
				cardCost.setVisibility(View.VISIBLE);
			}
			else
			{
				cardCost.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("type"))
			{
				cardType.setText(returnedCard.getString("type"));
				cardType.setVisibility(View.VISIBLE);
			}
			else
			{
				cardType.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("rarity"))
			{
				cardRarity.setText(returnedCard.getString("rarity"));
				cardRarity.setVisibility(View.VISIBLE);
			}
			else
			{
				cardRarity.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("text"))
			{
				cardText.setText(returnedCard.getString("text"));
				cardText.setVisibility(View.VISIBLE);
			}
			else
			{
				cardText.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("flavor"))
			{
				cardFlavorText.setText(returnedCard.getString("flavor"));
				cardFlavorText.setVisibility(View.VISIBLE);
			}
			else
			{
				cardFlavorText.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("manaCost"))
			{
				cardArtist.setText(returnedCard.getString("artist"));
				cardArtist.setVisibility(View.VISIBLE);
				
			}
			else
			{
				cardArtist.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("power") && returnedCard.has("toughness"))
			{
				String convertedPower = returnedCard.getString("power") + "/" + returnedCard.getString("toughness");
				cardPower.setText(convertedPower);
				cardPower.setVisibility(View.VISIBLE);
			}
			else
			{
				cardPower.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.has("multiverseid"))
			{
				multiId = returnedCard.getString("multiverseid").replace(" ", "");
				
				String formattedUrl = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" +
						multiId + "&type=card";       
				
				GetImage asyncTask = new GetImage();
				
				asyncTask.execute(new String[]{formattedUrl});
			}
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card_details, menu);
		return true;
	}
	
	
	//async task, since you can't connect in main thread
	private class GetImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }
 
        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            retrievedCardImage.setImageBitmap(result);
        }
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                
            }
            return bitmap;
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
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
		
		if (v.equals(CardDetailsActivity.addCardButton))
		{
			
			
			if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
			{
				addMenu.showAtLocation(thisRelativeLayout, Gravity.CENTER, 20, 20);
				addMenu.update(10,10,500,600);
			}
			else
			{
				addMenu.showAtLocation(thisLinearLayout, Gravity.CENTER, 20, 20);
				
				addMenu.update(10,10,500,600);
			}
			
			
		}
		else if (v.equals(CardDetailsActivity.deleteCardButton))
		{
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor = preferences.edit();
			if (!setName.equals("ERROR"))
			{
				editor = preferences.edit();
				try {
					JSONArray arrayOfCards = new JSONArray(preferences.getString(setName, "[]"));
					JSONArray newArray = new JSONArray();
					if (!arrayOfCards.toString().equals("[]"))
					{
						for (int i = 0; i < arrayOfCards.length(); i++)
						{
							JSONObject selectedCard = arrayOfCards.getJSONObject(i);
							String objectString = selectedCard.getString("multiverseid");
							if (!objectString.equals(multiId))
							{
								newArray.put(selectedCard);
							}
						}
					}
					editor.remove(setName);
					editor.commit();
					editor.putString(setName, newArray.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			editor.commit();
			addMenu.dismiss();
		}
		
	}
	
	public static ArrayAdapter<String> getAdapter()
	{
		ArrayAdapter<String> listViewAdapter = null;
		
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
		
	
		
		
		return listViewAdapter;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int pos, long id) {
		// TODO Auto-generated method stub
		
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
				
				
				
				
			}
			
			CARDS_LIST = savedCards.get(pos);
			
			
			
			SharedPreferences.Editor editor = preferences.edit();
			JSONArray loadedCardsForSet = new JSONArray(preferences.getString(CARDS_LIST, "[]"));
			
			if (loadedCardsForSet.equals("[]"))
			{
				loadedCardsForSet.put(returnedCard);
				editor.putString(CARDS_LIST, loadedCardsForSet.toString());
			}
			else
			{
				loadedCardsForSet.put(returnedCard);
				editor.putString(CARDS_LIST, loadedCardsForSet.toString());
			}
			
			editor.commit();
			
			addMenu.dismiss();
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
