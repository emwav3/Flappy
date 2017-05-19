package com.lawrencefoley.flappy.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils
{
	private FileUtils()
	{
		
	}
	
	public static String loadAsString(String filename)
	{
		StringBuilder result = new StringBuilder();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String buffer = "";
			while((buffer = reader.readLine()) != null)
			{
				result.append(buffer + '\n');
			}
			reader.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
