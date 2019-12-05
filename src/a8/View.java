package a8;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class View extends JPanel implements MouseListener{
	private final int MAX_PANEL_DIMENSIONS = 500;
	
	private int cellSize;
	private int width,height;
	
	private boolean[] values;
	
	private ViewMouseListener clickListener;
	
	public View(int width,int height){
		if(width < 1 || height < 1){
			throw new IllegalArgumentException("Invalid view dimensions.");
		}
		
		float widthRatio = (float)MAX_PANEL_DIMENSIONS / (float)width;
		float heightRatio = (float)MAX_PANEL_DIMENSIONS / (float)height;
		cellSize = (int)Math.floor(Math.min(widthRatio,heightRatio));
		
		this.width = width;
		this.height = height;
		
		setPreferredSize(new Dimension(width * cellSize,height * cellSize));
		addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(values == null){
			return;
		}
		
		Graphics g2 = g.create();
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0,0,width * cellSize,height * cellSize);
		
		g2.setColor(Color.BLACK);
		
		for(int x = 0;x < width;++x){
			for(int y = 0;y < height;++y){
				if(values[y * width + x]){
					g2.fillRect(x * cellSize,y * cellSize,cellSize,cellSize);
				}
			}
		}
	}
	
	public void setClickListener(ViewMouseListener newListener){
		clickListener = newListener;
	}
	
	public void setValues(boolean[] newValues){
		values = newValues;
	}
	
	// Mouse Listener implementation, including obligatory empty methods
	public void mouseClicked(MouseEvent e){
		
	}
	
	public void mouseEntered(MouseEvent e){
		
	}
	
	public void mouseExited(MouseEvent e){
		
	}
	
	public void mousePressed(MouseEvent e){
		clickListener.registerClick(e.getX() / cellSize,e.getY() / cellSize);
		repaint();
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
}