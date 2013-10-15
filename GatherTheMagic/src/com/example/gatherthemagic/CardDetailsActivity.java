package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CardDetailsActivity extends Activity {
	
	static TextView cardTitle;
	static TextView cardCost;
	static TextView cardType;
	static TextView cardRarity;
	static TextView cardText;
	static TextView cardFlavorText;
	static TextView cardArtist;
	static TextView cardPower;
	
	static ImageView retrievedCardImage;
	
	static JSONObject returnedCard;
	
	static String multiId;
	
	
	
	static URL imageUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_details);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		Bundle retrievedData = getIntent().getExtras();
		
		try {
			returnedCard = new JSONObject(retrievedData.getString("jsonobject"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			if (returnedCard.getString("name") != null)
			{
				cardTitle.setText(returnedCard.getString("name"));
				cardTitle.setVisibility(View.VISIBLE);
			}
			else
			{
				cardTitle.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("manaCost") != null)
			{
				cardCost.setText(returnedCard.getString("manaCost"));
				cardCost.setVisibility(View.VISIBLE);
			}
			else
			{
				cardCost.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("type") != null)
			{
				cardType.setText(returnedCard.getString("type"));
				cardType.setVisibility(View.VISIBLE);
			}
			else
			{
				cardType.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("rarity") != null)
			{
				cardRarity.setText(returnedCard.getString("rarity"));
				cardRarity.setVisibility(View.VISIBLE);
			}
			else
			{
				cardRarity.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("text") != null)
			{
				cardText.setText(returnedCard.getString("text"));
				cardText.setVisibility(View.VISIBLE);
			}
			else
			{
				cardText.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("flavor") != null)
			{
				cardFlavorText.setText(returnedCard.getString("flavor"));
				cardFlavorText.setVisibility(View.VISIBLE);
			}
			else
			{
				cardFlavorText.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("artist") != null)
			{
				cardArtist.setText(returnedCard.getString("artist"));
				cardArtist.setVisibility(View.VISIBLE);
				
			}
			else
			{
				cardArtist.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("power") != null && returnedCard.getString("toughness") != null)
			{
				String convertedPower = returnedCard.getString("power") + "/" + returnedCard.getString("toughness");
				cardPower.setText(convertedPower);
				cardPower.setVisibility(View.VISIBLE);
			}
			else
			{
				cardPower.setVisibility(View.INVISIBLE);
			}
			
			if (returnedCard.getString("multiverseid") != null)
			{
				multiId = returnedCard.getString("multiverseid").replace(" ", "");
				
				Log.i("multiidtest", multiId);
				
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

}
