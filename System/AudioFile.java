package TestProject.System;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFile {
	public Clip Audio;	
	public AudioInputStream AudioFile;
	private long Length;
	private long LoopReturnPosition = 0;
	
	public AudioFile(String Path) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioFile = AudioSystem.getAudioInputStream(new File(Path).getAbsoluteFile());
		Audio = AudioSystem.getClip();
		Audio.open(AudioFile);
		Length = Audio.getMicrosecondLength();
		Audio.addLineListener(new LineListener() {

			@Override
			public void update(LineEvent event) {
				// TODO Auto-generated method stub
				if(Audio.isRunning())
				{
					Audio.start();
					Audio.setMicrosecondPosition(LoopReturnPosition);
				}
			}
			
		});
	}
	
	public void SetLoopReturnPosition(float Time) {
		LoopReturnPosition = (long)(Time * 1000 * 60);
	}
	
	public static AudioFile LoadAudio(String Path) {
		try {
			return new AudioFile(Path);
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
