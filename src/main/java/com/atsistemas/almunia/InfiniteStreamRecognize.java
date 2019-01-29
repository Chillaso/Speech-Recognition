package com.atsistemas.almunia;

import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.TargetDataLine;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionConfig;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeRequest;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import com.google.protobuf.ByteString;

public class InfiniteStreamRecognize 
{	
	public static void infiniteStreamingRecognize() throws Exception 
	{
	  // Creating microphone input buffer thread
	  MicBuffer micrunnable = new MicBuffer();
	  Thread micThread = new Thread(micrunnable);
	  ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
    
	  //Create the client and the listener.
	  try (SpeechClient client = SpeechClient.create()) 
	  {
		  ClientStream<StreamingRecognizeRequest> clientStream;
	      responseObserver = new ResponseObserver<StreamingRecognizeResponse>() 
	      {
	    	  ArrayList<StreamingRecognizeResponse> responses = new ArrayList<>();
	    	  public void onStart(StreamController controller) {}
			
	    	  //ON RESPONSE. HERE HANDLE THE RESULTS
	    	  public void onResponse(StreamingRecognizeResponse response) 
	    	  {
	    		  responses.add(response);
	    		
	    		  StreamingRecognitionResult result = response.getResultsList().get(0);
	    		  SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	    		  
	    		  System.out.printf("Transcript : %s\n", alternative.getTranscript());
	    	  }
			
	    	  public void onComplete() 
	    	  {
	    		  System.out.println("Good bye atSistemas!");
			  }
			
			  public void onError(Throwable t) 
			  {
			      System.out.println(t);
			  }
	      };
	      //End listener

	      //Config recognition params
	      clientStream = client.streamingRecognizeCallable().splitCall(responseObserver);
	
	      RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
	              								.setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
	              								.setLanguageCode("es-ES")
	              								.setSampleRateHertz(16000)
	              								.build();
	      
	      StreamingRecognitionConfig streamingRecognitionConfig = StreamingRecognitionConfig.newBuilder()
	    		  												  .setConfig(recognitionConfig)
	    		  												  .build();
	
	      //The first request in a streaming call has to be a config
	      StreamingRecognizeRequest request = StreamingRecognizeRequest.newBuilder()
	              							  .setStreamingConfig(streamingRecognitionConfig)
	              							  .build(); 
	
	      clientStream.send(request);
	
	      try 
	      {
	    	  //SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true, bigEndian: false
	    	  AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);

	    	  //Set the system information to read from the microphone audio
	    	  DataLine.Info targetInfo = new Info(TargetDataLine.class,audioFormat); 
	
	    	  if (!AudioSystem.isLineSupported(targetInfo)) 
	    	  {
	    		  System.out.println("Microphone not supported");
	    		  System.exit(0);
	    	  }
	    	  
	    	  // Target data line captures the audio stream the microphone produces.
	    	  MicBuffer.setTargetDataLine((TargetDataLine) AudioSystem.getLine(targetInfo));
	    	  MicBuffer.getTargetDataLine().open(audioFormat);
	    	  micThread.start();
	
	    	  long startTime = System.currentTimeMillis();
	
	    	  while (true) 
	    	  {
	    		  long estimatedTime = System.currentTimeMillis() - startTime;
	    		  if (estimatedTime >= 55000) 
	    		  {
	    			 clientStream.closeSend();
	    			 clientStream = client.streamingRecognizeCallable().splitCall(responseObserver);
	    			 request = StreamingRecognizeRequest.newBuilder()
	    					   .setStreamingConfig(streamingRecognitionConfig)
	    					   .build();
	    			 startTime = System.currentTimeMillis();
	    		  } 
	    		  else 
	    		  {
	    			  request = StreamingRecognizeRequest.newBuilder()
	    					    .setAudioContent(ByteString.copyFrom(MicBuffer.getSharedQueue().take()))
	    					    .build();
	    		  }	
	    		  clientStream.send(request);
	    	  }
	      } 
	      catch (Exception e) 
	      {
	        System.out.println(e);
	      }
	  }
	}
}

