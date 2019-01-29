package com.atsistemas.almunia;

import com.atsistemas.almunia.recognition.InfiniteStreamRecognize;

public class StartStream 
{
	public static void main(String... args) 
	{
	  try 
	  {
		  InfiniteStreamRecognize.infiniteStreamingRecognize();
	  } 
	  catch (Exception e) 
	  {
		  System.out.println("Exception caught: " + e);
	  }
	}
}
