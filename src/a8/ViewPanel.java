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
	private JButton clearButton,noiseFillButton,stepButton;
	
	private ViewPanelListener listener;
	private View viewer;
	
	private JSlider generateSliderPanel(String title,int init,JPanel parent){
		// Title
		JLabel sliderTitle = new JLabel(title);
		sliderTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Slider
		JSlider newSlider = new JSlider();
		newSlider.setMinimum(1);
		newSlider.setMaximum(8);
		newSlider.setValue(init);
		newSlider.setMinorTickSpacing(1);
		newSlider.setMajorTickSpacing(2);
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
		
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls,BoxLayout.X_AXIS));
		controls.setBorder(BorderFactory.createEmptyBorder(STRUT_VALUE,STRUT_VALUE,STRUT_VALUE,STRUT_VALUE));
		
		// Threshold Sliders
		birthLowThreshold = generateSliderPanel("Birth Lower Threshold",3,controls);
		birthHighThreshold = generateSliderPanel("Birth Upper Threshold",3,controls);
		surviveLowThreshold = generateSliderPanel("Survival Lower Threshold",2,controls);
		surviveHighThreshold = generateSliderPanel("Survival Upper Threshold",3,controls);
		
		// Checkboxes
		torusMode = new JCheckBox("Torus Mode");
		torusMode.setAlignmentX(Component.CENTER_ALIGNMENT);
		controls.add(torusMode);
		
		// Buttons
		clearButton = new JButton("Clear");
		clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		clearButton.addActionListener(this);
		controls.add(clearButton);
		
		noiseFillButton = new JButton("Random");
		noiseFillButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		noiseFillButton.addActionListener(this);
		controls.add(noiseFillButton);
		
		stepButton = new JButton("Step");
		stepButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		stepButton.addActionListener(this);
		controls.add(stepButton);
		
		viewer = new View(width,height);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		add(viewer);
		add(controls);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == clearButton || e.getSource() == noiseFillButton || e.getSource() == stepButton){
			listener.applySettings(torusMode.isSelected(),
				birthLowThreshold.getValue(),birthHighThreshold.getValue(),surviveLowThreshold.getValue(),surviveHighThreshold.getValue());
		}
		
		if(e.getSource() == clearButton){
			listener.clearButtonEvent();
		}else if(e.getSource() == noiseFillButton){
			listener.noiseFillButtonEvent();
		}else if(e.getSource() == stepButton){
			listener.stepButtonEvent();
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