package com.atsistemas.almunia;

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
