package TestProject;

public class ApplicationParalelThread extends Thread {
	
	@Override
	public void run() {
		long LastTime = System.currentTimeMillis(), CurrentTime = LastTime;
		float ExpectedFPS = 1f / 60 * 1000;
		while(true) {
			while(CurrentTime - LastTime < ExpectedFPS) {
				CurrentTime = System.currentTimeMillis();
			}
			Main.Update((CurrentTime - LastTime) * 0.001f);
			LastTime = CurrentTime;
		}
	}
}
