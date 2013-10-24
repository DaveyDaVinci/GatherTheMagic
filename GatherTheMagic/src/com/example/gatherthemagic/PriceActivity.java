package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PriceActivity extends Activity implements OnClickListener{
	
	static TextView cardName;
	static TextView cardPrice;
	static EditText searchBox;
	static Button searchButton;
	
	static WebView tickerWebView;

	static Context context;
	
	static ProgressDialog progressDialog;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_price);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		cardName = (TextView) findViewById(R.id.searchedCardName);
		cardPrice = (TextView) findViewById(R.id.returnedCardPrice);
		searchBox = (EditText) findViewById(R.id.searchPriceBox);
		searchButton = (Button) findViewById(R.id.searchPriceButton);
		searchButton.setOnClickListener(this);
		
		context = this;
		
		tickerWebView = (WebView) findViewById(R.id.tickerWebView);
		
		
		WebSettings webSettings = tickerWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		tickerWebView.loadUrl("file:///android_asset/ticker.html");
		
		progressDialog = new ProgressDialog(context);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.price, menu);
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
			
			//async task, since you can't connect in main thread
			private class GetCardPrice extends AsyncTask<String, Void, String> {
				
				
		        @Override
		        protected String doInBackground(String... urls) {
		            String downloadedPrice = null;
		            
		            
		            try{
		            	downloadedPrice = retrievePrice(urls[0]);
		            }
		            catch (Exception e)
		            {
		            	Log.i("Test", "enter valid card");
		            }
		            
		            
		            
		            return downloadedPrice;
		        }
		 
		        
		        @Override
		        protected void onPostExecute(String result) {
		            
		        	
		            if (result != null && !result.isEmpty() && !result.equals(""))
		            {
		            	String resultPrice = result.substring(2, result.length()-2);
		            	cardPrice.setText(resultPrice);
		            }
		            else
		            {
		            	Toast toast = Toast.makeText(context, "Please enter a valid card", Toast.LENGTH_LONG);
		            	toast.show();
		            }
		            
		        	
		        	progressDialog.dismiss();
		            
		        }
		 
		        
		        private String retrievePrice(String url) {
		            String price = null;
		            InputStream stream = null;
		            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		            bmOptions.inSampleSize = 1;
		 
		            try {
		                stream = getHttpConnection(url);
		                
		                byte[] contentInBytes = new byte[1024];
		    			int bytesRead = 0;
		    			StringBuffer responseBuffer = new StringBuffer();
		    			
		    			while((bytesRead = stream.read(contentInBytes)) != -1)
		    			{
		    				price = new String(contentInBytes, 0, bytesRead);
		    				responseBuffer.append(price);
		    			}
		    			stream.close();
		    			return responseBuffer.toString();
		                
		               
		                
		            } catch (IOException e1) {
		                
		            }
		            return price;
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
			
			
			

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.equals(PriceActivity.searchButton))
				{
					String enteredName = searchBox.getText().toString().trim();
					
					String formattedName1 = enteredName.replace(" ", "%20");
					String formattedName2 = formattedName1.replace(",", "%2C");
					
					cardName.setText(enteredName);
					String formattedUrl = "http://magictcgprices.appspot.com/api/cfb/price.json?cardname=" + formattedName2 + "&setname=theros";       
					
					GetCardPrice asyncTask = new GetCardPrice();
					
					asyncTask.execute(new String[]{formattedUrl});
					
					progressDialog.setTitle("Loading");
		            progressDialog.setMessage("Wait while loading...");
		            progressDialog.show();
				}
			}
			
			
			
			
			
			

}
