package spinnerClasses;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.gatherthemagic.ResultsActivity;
import com.example.gatherthemagic.SearchAndBrowseActivity;

public class SetSpinner implements OnItemSelectedListener {
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
		// TODO Auto-generated method stub
		
		SearchAndBrowseActivity.getSpinnerPosition(pos);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	public static JSONArray sortBySets(int spinnerPos, ArrayList<String> codes, Context context)
	{
		JSONArray arrayOfCards = null;
		
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
		
		
		
		
		return arrayOfCards;
	}
	
	
	

}
