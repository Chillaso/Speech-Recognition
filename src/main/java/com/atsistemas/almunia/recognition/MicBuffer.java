package com.atsistemas.almunia.recognition;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.sampled.TargetDataLine;

public class MicBuffer implements Runnable 
{

	private static volatile BlockingQueue<byte[]> sharedQueue = new LinkedBlockingQueue<byte[]>();
	private static TargetDataLine targetDataLine;
	private static int BYTES_PER_BUFFER = 6400;
	
	@Override
	public void run() 
	{
        System.out.println("Start speaking...Press Ctrl-C to stop");
        targetDataLine.start();
        byte[] data = new byte[BYTES_PER_BUFFER];
        
        while (targetDataLine.isOpen()) 
        {
          try 
          {
            int numBytesRead = targetDataLine.read(data, 0, data.length);
            if ((numBytesRead <= 0) && (targetDataLine.isOpen())) 
            {
              continue;
            }
          
            sharedQueue.put(data.clone());
          } 
          catch (InterruptedException e) 
          {
            System.out.println("Microphone input buffering interrupted : " + e.getMessage());
          }
        }
	}
	
	public static void setSharedQueue(BlockingQueue<byte[]> sharedQueue) {
		MicBuffer.sharedQueue = sharedQueue;
	}

	public static void setTargetDataLine(TargetDataLine targetDataLine) {
		MicBuffer.targetDataLine = targetDataLine;
	}

	public static void setBYTES_PER_BUFFER(int bYTES_PER_BUFFER) {
		BYTES_PER_BUFFER = bYTES_PER_BUFFER;
	}

	public static BlockingQueue<byte[]> getSharedQueue() {
		return sharedQueue;
	}

	public static TargetDataLine getTargetDataLine() {
		return targetDataLine;
	}

	public static int getBYTES_PER_BUFFER() {
		return BYTES_PER_BUFFER;
	}
	
}
