package a8;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Component;
import javax.swing.Box;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;

public class ViewPanel extends JPanel implements ActionListener{
	private final int STRUT_VALUE = 10;
	
	private JSlider birthLowThreshold,birthHighThreshold;
	private JSlider surviveLowThreshold,surviveHighThreshold;
	
	private JCheckBox torusMode;
	private JButton clearButton,noiseFillButton,stepButton,playButton;
	
	private JSlider animationDelay;
	
	private ViewPanelListener listener;
	private View viewer;
	
	private JSlider generateSliderPanel(String title,int low,int high,int init,int minor,int major,JPanel parent){
		// Title
		JLabel sliderTitle = new JLabel(title);
		sliderTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Slider
		JSlider newSlider = new JSlider();
		newSlider.setMinimum(low);
		newSlider.setMaximum(high);
		newSlider.setValue(init);
		newSlider.setMinorTickSpacing(minor);
		newSlider.setMajorTickSpacing(major);
		newSlider.setPaintTicks(true);
		newSlider.setPaintLabels(true);
		newSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Panel
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new BoxLayout(sliderPanel,BoxLayout.Y_AXIS));
		
		sliderPanel.add(sliderTitle);
		sliderPanel.add(newSlider);
		
		// Parent
		parent.add(sliderPanel);
		
		return newSlider;
	}
	
	public ViewPanel(int width,int height){
		if(width < 1 || height < 1){
			throw new IllegalArgumentException("Invalid dimensions.");
		}
		
		// Thresholds
		JPanel thresholds = new JPanel();
		thresholds.setLayout(new BoxLayout(thresholds,BoxLayout.X_AXIS));
		
		// Sliders
		birthLowThreshold = generateSliderPanel("Birth Lower Threshold",0,8,3,1,2,thresholds);
		birthHighThreshold = generateSliderPanel("Birth Upper Threshold",0,8,3,1,2,thresholds);
		surviveLowThreshold = generateSliderPanel("Survival Lower Threshold",0,8,2,1,2,thresholds);
		surviveHighThreshold = generateSliderPanel("Survival Upper Threshold",0,8,3,1,2,thresholds);
		
		// Frame Modifiers
		JPanel frameModifiers = new JPanel();
		frameModifiers.setLayout(new BoxLayout(frameModifiers,BoxLayout.X_AXIS));
		
		// Buttons
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		frameModifiers.add(clearButton);
		
		noiseFillButton = new JButton("Random");
		noiseFillButton.addActionListener(this);
		frameModifiers.add(noiseFillButton);
		
		// Simulation
		JPanel simulation = new JPanel();
		simulation.setLayout(new BoxLayout(simulation,BoxLayout.X_AXIS));
		
		// Button 1
		stepButton = new JButton("Step");
		stepButton.addActionListener(this);
		simulation.add(stepButton);
		
		// Checkbox
		torusMode = new JCheckBox("Torus Mode");
		simulation.add(torusMode);
		
		// Button 2
		playButton = new JButton("Play");
		playButton.addActionListener(this);
		simulation.add(playButton);
		
		// Slider
		animationDelay = generateSliderPanel("Animation Delay (milliseconds)",10,1000,10,10,90,simulation);
		
		// Putting all the panels together into one
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls,BoxLayout.Y_AXIS));
		controls.setBorder(BorderFactory.createEmptyBorder(STRUT_VALUE,STRUT_VALUE,STRUT_VALUE,STRUT_VALUE));
		
		controls.add(thresholds);
		controls.add(Box.createVerticalStrut(STRUT_VALUE));
		controls.add(frameModifiers);
		controls.add(Box.createVerticalStrut(STRUT_VALUE));
		controls.add(simulation);
		
		// Viewer
		viewer = new View(width,height);
		
		// Final Composition
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add(viewer);
		add(controls);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == clearButton || e.getSource() == noiseFillButton ||
			e.getSource() == stepButton || e.getSource() == playButton){
			listener.applySettings(torusMode.isSelected(),
				birthLowThreshold.getValue(),birthHighThreshold.getValue(),surviveLowThreshold.getValue(),surviveHighThreshold.getValue());
		}
		
		if(e.getSource() == clearButton){
			listener.clearButtonEvent();
		}else if(e.getSource() == noiseFillButton){
			listener.noiseFillButtonEvent();
		}else if(e.getSource() == stepButton){
			listener.stepButtonEvent();
		}else if(e.getSource() == playButton) {
			// Fancy animations!
			listener.toggleAnimation();
			
			// Toggle all other buttons to prevent race conditions while animating
			birthLowThreshold.setEnabled(!listener.isAnimating());
			birthHighThreshold.setEnabled(!listener.isAnimating());
			surviveLowThreshold.setEnabled(!listener.isAnimating());
			surviveHighThreshold.setEnabled(!listener.isAnimating());
			
			clearButton.setEnabled(!listener.isAnimating());
			noiseFillButton.setEnabled(!listener.isAnimating());
			
			stepButton.setEnabled(!listener.isAnimating());
			torusMode.setEnabled(!listener.isAnimating());
			animationDelay.setEnabled(!listener.isAnimating());
			
			if(listener.isAnimating()) {
				playButton.setText("Stop");
			}else{
				playButton.setText("Play");
			}
		}
		
		viewer.repaint();
	}
	
	public void setListener(ViewPanelListener newListener){
		listener = newListener;
	}
	
	public void setViewClickListener(ViewMouseListener newListener){
		viewer.setClickListener(newListener);
	}
	
	public void setValues(boolean[] newValues){
		viewer.setValues(newValues);
	}
}