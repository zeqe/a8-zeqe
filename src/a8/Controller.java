package a8;

public class Controller implements ViewPanelListener, ViewMouseListener{
	private Model model;
	private ViewPanel view;
	
	private boolean isPlaying;
	
	public Controller(Model model,ViewPanel view){
		if(model == null || view == null){
			throw new IllegalArgumentException("Invalid model or view passed to controller.");
		}
		
		this.model = model;
		this.view = view;
		
		this.isPlaying = false;
		
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
	}
	
	public void noiseFillButtonEvent(){
		for(int x = 0;x < model.getWidth();++x){
			for(int y = 0;y < model.getHeight();++y){
				model.setCell(x,y,Math.random() < 0.5);
			}
		}
	}
	
	public void stepButtonEvent(){
		model.stepCells();
		view.setValues(model.getValues());
	}
	
	// View Mouse Listener Implementation
	public void registerClick(int x,int y){
		if(model.getCell(x,y)){
			model.setCell(x,y,false);
		}else{
			model.setCell(x,y,true);
		}
	}
	
	public void toggleAnimation() {
		isPlaying = !isPlaying;
	}
	
	public boolean isAnimating() {
		return isPlaying;
	}
}