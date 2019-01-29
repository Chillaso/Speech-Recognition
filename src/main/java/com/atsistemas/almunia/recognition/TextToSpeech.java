package com.atsistemas.almunia.recognition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import javazoom.jl.player.Player;

public class TextToSpeech {

	public static void synthesizeText(String text) throws Exception 
	{
		try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) 
		{
			SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();
		
			VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
										.setLanguageCode("es-ES") 
										.setSsmlGender(SsmlVoiceGender.FEMALE) 
										.build();

			AudioConfig audioConfig = AudioConfig.newBuilder()
									  .setAudioEncoding(AudioEncoding.MP3) // MP3 audio.
									  .build();

			SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

			ByteString audioContents = response.getAudioContent();

			try (OutputStream out = new FileOutputStream("output.mp3")) 
			{
				out.write(audioContents.toByteArray());
			}
			
			Player player = new Player(new FileInputStream("output.mp3"));
			player.play();
		}
	}
}
