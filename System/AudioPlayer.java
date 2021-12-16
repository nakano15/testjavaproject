package TestProject.System;

import javax.sound.sampled.FloatControl;

public class AudioPlayer {
	private static AudioFile CurrentAudio;
	public static String Status = "Inactive";
	
	public static void ChangeAudio(AudioFile audio) {
		CurrentAudio = audio;
	}
	
	public static void Play() {
		if(CurrentAudio != null) {
			ChangeVolume(0.7f);
			CurrentAudio.Audio.start();
			Status = "Playing";
		}
	}
	
	public static void Stop() {
		CurrentAudio.Audio.stop();
		CurrentAudio.Audio.setMicrosecondPosition(0);
	}
	
	public static void ChangeVolume(float Volume) {
		FloatControl control = (FloatControl) CurrentAudio.Audio.getControl(FloatControl.Type.MASTER_GAIN);
		float range = control.getMaximum() - control.getMinimum();
		control.setValue((range * Volume) + control.getMinimum());
	}
}
