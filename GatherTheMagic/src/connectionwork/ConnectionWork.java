package connectionwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionWork {
	static Boolean theConnection = false;
	static String theConnectionType = "none";
	
	public static String getTheConnectionType(Context context)
	{
		connectionManager(context);
		return theConnectionType;
	}
	
	public static Boolean getStatusOfConnection(Context context)
	{
		connectionManager(context);
		return theConnection;
	}
	
	//This sets the connection type and a bool whether there's a connection
	private static void connectionManager(Context context)
	{
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();
		if (netInfo != null)
		{
			if(netInfo.isConnected())
			{
				theConnectionType = netInfo.getTypeName();
				theConnection = true;
			}
			else
			{
				theConnectionType = null;
				theConnection = false;
			}
		}
	}
	
	
}
