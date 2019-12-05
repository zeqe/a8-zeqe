package a8;

public class AnimationThread extends Thread{
	int stepDelay;
	ViewPanelListener listener;
	
	public AnimationThread(int stepDelay,ViewPanelListener listener) {
		if(listener == null) {
			throw new IllegalArgumentException("Null listener passed to animation thread.");
		}
		
		if(stepDelay < 0) {
			throw new IllegalArgumentException("Negative step delay not permitted.");
		}
		
		this.stepDelay = stepDelay;
		this.listener = listener;
	}
	
	public void run() {
		// Animate while there is no signal to stop
		while(!listener.shouldStopAnimating()) {
			listener.stepButtonEvent();
			
			try {
				sleep(stepDelay);
			}catch(InterruptedException e) {
				// do nothing
			}
		}
	}
}