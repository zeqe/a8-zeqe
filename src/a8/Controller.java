package a8;

public class Controller implements ViewPanelListener{
	private Model model;
	private ViewPanel view;
	
	public Controller(Model model,ViewPanel view){
		if(model == null || view == null){
			throw new IllegalArgumentException("Invalid model or view passed to controller.");
		}
		
		this.model = model;
		this.view = view;
		
		view.setListener(this);
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
}