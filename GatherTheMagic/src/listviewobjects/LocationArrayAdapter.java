package listviewobjects;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gatherthemagic.R;

public class LocationArrayAdapter extends BaseAdapter {

	private static ArrayList<LocationResults> locationArrayList;
	 
	private LayoutInflater cellInflater;
	
	public LocationArrayAdapter(Context context, ArrayList<LocationResults> results) 
	{
		  locationArrayList = results;
		  cellInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return locationArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return locationArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		  if (convertView == null) {
		   convertView = cellInflater.inflate(R.layout.custom_row, null);
		   holder = new ViewHolder();
		   holder.txtName = (TextView) convertView.findViewById(R.id.cellname);
		   holder.txtAddress = (TextView) convertView.findViewById(R.id.celladdress);
		  

		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtName.setText(locationArrayList.get(position).getName());
		  holder.txtAddress.setText(locationArrayList.get(position).getAddress());
		 
		  return convertView;
	}
	
	static class ViewHolder {
		  TextView txtName;
		  TextView txtAddress;
		  
		 }

}
