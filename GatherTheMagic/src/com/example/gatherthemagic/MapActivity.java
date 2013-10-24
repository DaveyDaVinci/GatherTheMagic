package com.example.gatherthemagic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import listviewobjects.LocationArrayAdapter;
import listviewobjects.LocationResults;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import connectionwork.ConnectionWork;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MapActivity extends Activity implements OnClickListener, OnItemClickListener{
	
	static Button getLocationButton;
	
	static String dummyFormattedLocation;
	
	static Context context;
	
	static GetPlaces asyncTask;
	
	static JSONArray sortedArray;
	
	static String LATITUDE;
	static String LONGITUDE;
	static String NAME;
	static String ID;
	static String FORMATTED_ADDRESS;
	
	static ArrayAdapter<String> populatedAdapter;
	
	static ArrayList<String> nameList;
	static ArrayList<String> addressList;
	
	static ListView resultsListView;
	
	static ProgressDialog progressDialog;
	
	static LocationManager locationManager;
	
	static Boolean listenerActive;
	
	static Boolean connection;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		
		
		getLocationButton = (Button) findViewById(R.id.searchMapButton);
		getLocationButton.setOnClickListener(this);
		
		context = this;
		
		
		LATITUDE = "latitude";
		LONGITUDE = "longitude";
		NAME = "name";
		ID = "id";
		FORMATTED_ADDRESS = "formatted_address";
		
		sortedArray = new JSONArray();
		
		nameList = new ArrayList<String>();
		addressList = new ArrayList<String>();
		
		resultsListView = (ListView) findViewById(R.id.resultsList);
		
		progressDialog = new ProgressDialog(context);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
		if (v.equals(MapActivity.getLocationButton))
		{
			
			progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.show();
            
          //BOOL TEST TO SEE IF CONNECTION IS AVAILABLE
    		connection = ConnectionWork.getStatusOfConnection(this);
    		
    		if (connection == true)
    		{
    			Log.i("NETWORKCONNECTION", ConnectionWork.getTheConnectionType(this));
    		} else
    		{
    			Toast toast = Toast.makeText(context, "NO CONNECTION",  Toast.LENGTH_SHORT);
    			toast.show();
    		}
			
			 locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			 Boolean locEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			
			 
			 Log.i("bool check", Boolean.toString(locEnabled));
			 
			 
			 if(locEnabled){
				 Location location =  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				 
				 if (location != null)
				 {
					 double lat = location.getLatitude();
					 double lon = location.getLongitude();
					 
					 dummyFormattedLocation = Double.toString(lat) + "," + Double.toString(lon);
					startDataRetrieval(dummyFormattedLocation);
				 }
				 else
				 {
					 double lat = 42.3482;
					 double lon = -75.1890;
					 
					 Toast toast = Toast.makeText(context, "Location not found.  Using NYC default location",  Toast.LENGTH_SHORT);
		    			toast.show();
		    			
		    			dummyFormattedLocation = Double.toString(lat) + "," + Double.toString(lon);
						startDataRetrieval(dummyFormattedLocation);
				 }
				
					
				
			 } else {
				 Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				 
				 if (location != null)
				 {
					 double lat = location.getLatitude();
						double lon = location.getLongitude();
							
						dummyFormattedLocation = Double.toString(lat) + "," + Double.toString(lon);
						startDataRetrieval(dummyFormattedLocation);
				 }
				 else
				 {
					 double lat = 42.3482;
					 double lon = -75.1890;
					 
					 Toast toast = Toast.makeText(context, "Location not found.  Using NYC default location",  Toast.LENGTH_SHORT);
		    			toast.show();
		    			
		    			dummyFormattedLocation = Double.toString(lat) + "," + Double.toString(lon);
						startDataRetrieval(dummyFormattedLocation);
				 }
				
			 }
			
			
			
			
			
			
			//double lat = location.getLatitude();
			//double lon = location.getLongitude();
			
			//dummyFormattedLocation = Double.toString(lat) + "," + Double.toString(lon);
			  //startDataRetrieval(dummyFormattedLocation);
			   
			  
			
			
			   
		}
	}

	
	private class GetPlaces extends AsyncTask<String, Void, String> {
		
		
        @Override
        protected String doInBackground(String... urls) {
            String listOfPlaces = null;
            
            
            try{
            	listOfPlaces = retrievePlaces(urls[0]);
            }
            catch (Exception e)
            {
            	
            }
            
            
            
            return listOfPlaces;
        }
 
        
        @Override
        protected void onPostExecute(String result) {
            
        	
            if (result != null && !result.isEmpty() && !result.equals(""))
            {
            	Log.i("Test if hit", "hit");
            	
            	parseJSONData(result);
            }
            else
            {
            	Toast toast = Toast.makeText(context, "No information found", Toast.LENGTH_LONG);
            	toast.show();
            }
            
            if(asyncTask.getStatus().equals(AsyncTask.Status.RUNNING))
            {
                asyncTask.cancel(true);
            }
            
            
        	
        	
            
        }
 
        
        private String retrievePlaces(String url) {
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
	
	public void parseJSONData (String results)
	{
		try {
			JSONObject retrievedResults = new JSONObject(results);
			JSONArray arrayOfResults = retrievedResults.getJSONArray("results");
			
			Log.i("Length", Integer.toString(arrayOfResults.length()));
			for (int i = 0; i < arrayOfResults.length(); i++)
			{
				JSONObject individualResult = arrayOfResults.getJSONObject(i);
				JSONObject geometry = individualResult.getJSONObject("geometry");
				JSONObject location = geometry.getJSONObject("location");
				JSONObject newResult = new JSONObject();
				newResult.put(LATITUDE, location.getDouble("lat"));
				newResult.put(LONGITUDE, location.getDouble("lng"));
				newResult.put(NAME, individualResult.getString("name"));
				newResult.put(ID, individualResult.getString("id"));
				newResult.put(FORMATTED_ADDRESS, individualResult.getString("formatted_address"));
				addressList.add(individualResult.getString(FORMATTED_ADDRESS));
				
				nameList.add(individualResult.getString(NAME));
				
				sortedArray.put(newResult);
				
			}
			
			
			ArrayList<LocationResults> locationResults = GetLocationResults();
			
			progressDialog.dismiss();
			
			resultsListView.setAdapter(new LocationArrayAdapter(this, locationResults));
			resultsListView.setOnItemClickListener(this);
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int pos, long id) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject individualResult = sortedArray.getJSONObject(pos);
			
			
			
			String uriBegin = "geo:" + Double.toString(individualResult.getDouble(LATITUDE)) + "," + 
					Double.toString(individualResult.getDouble(LONGITUDE));
			String query = Double.toString(individualResult.getDouble(LATITUDE)) + "," + Double.toString(individualResult.getDouble(LONGITUDE)) 
					+ "(" + individualResult.getString("name") + ")";
			String encodedQuery = Uri.encode(query);
			String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
			Uri theUrl = Uri.parse(uriString);
			
			Intent mapsIntent = new Intent(android.content.Intent.ACTION_VIEW, theUrl);
			
			startActivity(mapsIntent);
			
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//TEST
	private ArrayList<LocationResults> GetLocationResults(){
	     ArrayList<LocationResults> results = new ArrayList<LocationResults>();
	     
	     Log.i("length test", Integer.toString(nameList.size()));
	     for (int i = 0; i < nameList.size(); i++)
	     {
	    	 
	    	 LocationResults selectedResult = new LocationResults();
		     selectedResult.setName(nameList.get(i));
		     selectedResult.setAddress(addressList.get(i));
		     results.add(selectedResult);
	     }
	     
	     
	     return results;
	    }
	
	public void startDataRetrieval(String location)
	{
		
		
		String formattedUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=comic+shops&sensor=true" +
				"&key=AIzaSyDTHafO80et9HCwOm5z9xGnrrGqDox8C54&location=" + dummyFormattedLocation + "&radius=500";
		
		
		asyncTask = new GetPlaces();
		
		asyncTask.execute(new String[]{formattedUrl});
		
	}
	
	

}
