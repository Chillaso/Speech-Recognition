package com.atsistemas.almunia.dictionary;

import com.atsistemas.almunia.utils.Commands;

public class VoiceCommands {
	
	public static String[] checkResponse(String transcript)
	{
		int cont = 0;
		
		for(String[] command : Commands.COMMANDS)
		{
			for(String s : command)
			{
				if(transcript.toLowerCase().contains(s))
					cont++;		
			}
			//If command matches 100%
			if(command.length==cont)
				return command;
			//clear cont and continue with command list
			else
			{				
				cont=0;
				continue;
			}
		}
		
		return null;
	}
}
