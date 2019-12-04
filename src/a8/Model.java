package a8;

public class Model{
	private int width,height;
	private boolean[] currCells,nextCells;
	
	private boolean torusMode;
	
	private int birthLowThreshold,birthHighThreshold;
	private int surviveLowThreshold,surviveHighThreshold;
	
	public Model(int width,int height){
		if(width < 1 || height < 1){
			throw new IllegalArgumentException("Invalid Game of Life board dimensions.");
		}
		
		this.width = width;
		this.height = height;
		
		this.currCells = new boolean[width * height];
		this.nextCells = new boolean[width * height];
		
		this.torusMode = false;
	}
	
	// Utility
	private int getIndex(int x,int y){
		if(!torusMode && (x < 0 || x >= width || y < 0 || y >= height)){
			return -1;
		}
		
		if(torusMode){
			x = ((x % width) + width) % width;
			y = ((y % height) + height) % height;
		}
		
		return y * width + x;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public boolean[] getValues(){
		return currCells;
	}
	
	// Simulation Fundamentals
	public boolean getCell(int x,int y){
		int index = getIndex(x,y);
		
		if(index < 0){
			return false;
		}
		
		return currCells[index];
	}
	
	public void setCell(int x,int y,boolean newState){
		int index = getIndex(x,y);
		
		if(index >= 0){
			currCells[index] = newState;
		}
	}
	
	// Simulation Parameters
	public void enableTorusMode(){
		torusMode = true;
	}
	
	public void disableTorusMode(){
		torusMode = false;
	}
	
	public void setBirthLowThreshold(int newThreshold){
		birthLowThreshold = newThreshold;
	}
	
	public void setBirthHighThreshold(int newThreshold){
		birthHighThreshold = newThreshold;
	}
	
	public void setSurviveLowThreshold(int newThreshold){
		surviveLowThreshold = newThreshold;
	}
	
	public void setSurviveHighThreshold(int newThreshold){
		surviveHighThreshold = newThreshold;
	}
	
	// Simulation Simulation
	public void stepCells(){
		// Preparing other buffer
		for(int x = 0;x < width;++x){
			for(int y = 0;y < height;++y){
				// Gathering the count for each cell
				int count = 0;
				
				if(getCell(x - 1,y - 1)){
					++count;
				}
				
				if(getCell(x - 1,y)){
					++count;
				}
				
				if(getCell(x - 1,y + 1)){
					++count;
				}
				
				if(getCell(x,y - 1)){
					++count;
				}
				
				if(getCell(x,y + 1)){
					++count;
				}
				
				if(getCell(x + 1,y - 1)){
					++count;
				}
				
				if(getCell(x + 1,y)){
					++count;
				}
				
				if(getCell(x + 1,y + 1)){
					++count;
				}
				
				// Appropriately setting the cell
				if(getCell(x,y)){
					if(count < surviveLowThreshold || count > surviveHighThreshold){
						// Death
						nextCells[getIndex(x,y)] = false;
					}else{
						// Survival
						nextCells[getIndex(x,y)] = true;
					}
				}else{
					if(count >= birthLowThreshold && count <= birthHighThreshold){
						// Birth
						nextCells[getIndex(x,y)] = true;
					}else{
						// Dead, still
						nextCells[getIndex(x,y)] = false;
					}
				}
			}
		}
		
		// Swapping cell buffers
		boolean[] tempCells = currCells;
		currCells = nextCells;
		nextCells = tempCells;
	}
}