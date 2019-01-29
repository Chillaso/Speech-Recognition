package com.atsistemas.almunia.dictionary;

import com.atsistemas.almunia.utils.Commands;

public class VoiceCommands {

	public static boolean checkResponse(String transcript)
	{
		int cont = 0;
		if(transcript.toLowerCase().contains("atsistemas"))
		{
			for(String s : Commands.OPEN_ALMUNIA)
			{
				if(transcript.toLowerCase().contains(s))
					cont++;
			}
			return (cont == Commands.OPEN_ALMUNIA.length) ? true : false;
		}
		return false;
	}
}
