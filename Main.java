package TestProject;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;

import javax.swing.*;

import TestProject.System.AudioFile;

public class Main {
	public static ApplicationParalelThread AltThread = new ApplicationParalelThread();
	public static JFrame GameFrame;
	public static Random random;
	
	public static void main(String[] args) {
		random = new Random();
		/*AudioFile test = AudioFile.LoadAudio("src/TestProject/Content/bgm_coc_boss_loop.wav");
		if(test != null) {
			test.SetLoopReturnPosition(16f);
			TestProject.System.AudioPlayer.ChangeAudio(test);
			TestProject.System.AudioPlayer.Play();
		}*/
		Races.Initialize();
		MaxExpHandler.Initialize();
		Player.Initialize();
		GameFrame = new JFrame();
		GameFrame.setTitle("RPG Desktop");
		GameFrame.setBackground(Color.black);
		GameFrame.setSize(800, 600);
		GameDesktop desktop = new GameDesktop();
		GameFrame.getContentPane().add(desktop, BorderLayout.CENTER);
		GameFrame.setVisible(true);
		GameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AltThread.setPriority(Thread.MIN_PRIORITY);
		AltThread.start();
	}
	
	public static float TimeStack = 0;
	
	public static void Update(float Time) {
		TimeStack += Time;
		GameDesktop.explorationInterface.UpdateExploration();
		for(Battle b: Battle.ActiveBattles)
		{
			b.Update();
		}
	}
}
