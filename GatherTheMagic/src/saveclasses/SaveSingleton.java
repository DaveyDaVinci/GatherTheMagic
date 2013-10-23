package saveclasses;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;

public class SaveSingleton {
	
	private static SaveSingleton instance = null;
	
	protected SaveSingleton()
	{
		
	}
	
	public static SaveSingleton getInstance()
	{
		if (instance == null)
		{
			instance = new SaveSingleton();
		}
		return instance;
	}
	
	public static boolean storeJSONStringData(Context context, String filename, String urlContent)
	{
		//File file;
		FileOutputStream fileOutput;
		
		try {
			fileOutput = context.openFileOutput(filename, Context.MODE_PRIVATE);
			fileOutput.write(urlContent.getBytes());
			fileOutput.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public static String readStringData(Context context, String filename)
	{
		String content = "";
		
		try{
			@SuppressWarnings("unused")
			File file = new File(filename);
			FileInputStream fileInput = context.openFileInput(filename);
			
			BufferedInputStream buffInput = new BufferedInputStream(fileInput);
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while((bytesRead = buffInput.read(contentBytes)) != -1)
			{
				content = new String(contentBytes, 0, bytesRead);
				contentBuffer.append(content);
			}
			content = contentBuffer.toString();
			fileInput.close();
			
		}
		catch(FileNotFoundException e)
		{
			
		}
		catch(IOException e)
		{
			
		}
		
		return content;
	}

}
