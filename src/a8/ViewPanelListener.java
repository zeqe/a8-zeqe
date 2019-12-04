package a8;

public interface ViewPanelListener{
	void applySettings(boolean torusMode,int bLow,int bHigh,int sLow,int sHigh);
	
	void clearButtonEvent();
	void noiseFillButtonEvent();
	void stepButtonEvent();
}