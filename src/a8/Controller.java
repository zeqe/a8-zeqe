package a8;

public class Controller implements ViewPanelListener, ViewMouseListener{
	private Model model;
	private ViewPanel view;
	
	private boolean mouseWhileAnimating;
	
	private boolean animationShouldStop;
	private AnimationThread animation;
	
	public Controller(Model model,ViewPanel view,boolean mouseWhileAnimating){
		if(model == null || view == null){
			throw new IllegalArgumentException("Invalid model or view passed to controller.");
		}
		
		this.model = model;
		this.view = view;
		
		this.mouseWhileAnimating = mouseWhileAnimating;
		
		this.animationShouldStop = false;
		
		view.setListener(this);
		view.setViewClickListener(this);
		view.setValues(model.getValues());
	}
	
	// View Panel Listener Implementation
	public void applySettings(boolean torusMode,int bLow,int bHigh,int sLow,int sHigh){
		if(torusMode){
			model.enableTorusMode();
		}else{
			model.disableTorusMode();
		}
		
		model.setBirthLowThreshold(bLow);
		model.setBirthHighThreshold(bHigh);
		model.setSurviveLowThreshold(sLow);
		model.setSurviveHighThreshold(sHigh);
	}
	
	public void clearButtonEvent(){
		for(int x = 0;x < model.getWidth();++x){
			for(int y = 0;y < model.getHeight();++y){
				model.setCell(x,y,false);
			}
		}
		
		view.repaint();
	}
	
	public void noiseFillButtonEvent(){
		for(int x = 0;x < model.getWidth();++x){
			for(int y = 0;y < model.getHeight();++y){
				model.setCell(x,y,Math.random() < 0.5);
			}
		}
		
		view.repaint();
	}
	
	public void stepButtonEvent(){
		model.stepCells();
		view.setValues(model.getValues());
		view.repaint();
	}
	
	public void toggleAnimation(int delayValue) {
		if(isAnimating()) {
			// Setting the signal to end execution
			animationShouldStop = true;
			
			// Wait for the thread to end
			try {
				animation.join();
			}catch(InterruptedException e) {
				// Do nothing
			}
			
			// Set variable to null so that animation state
			// is guaranteed to be detected correctly, and so
			// that JVM knows to GC.
			animation = null;
		}else {
			// Starting a new animation thread otherwise
			animationShouldStop = false;
			
			animation = new AnimationThread(delayValue,this);
			animation.start();
		}
	}
	
	public boolean isAnimating() {
		return animation != null;
	}
	
	public boolean shouldStopAnimating() {
		return animationShouldStop;
	}
	
	// View Mouse Listener Implementation
	public void registerClick(int x,int y){
		if(!isAnimating() || mouseWhileAnimating) {
			if(model.getCell(x,y)){
				model.setCell(x,y,false);
			}else{
				model.setCell(x,y,true);
			}
			
			view.repaint();
		}
	}
}