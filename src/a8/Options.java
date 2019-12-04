package a8;

import java.awt.Color;

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

public class Options implements ActionListener{
	private final int DIMENSIONS_MIN = 10;
	private final int DIMENSIONS_MAX = 500;
	
	private final int OPTIONS_STRUT_VALUE = 10;
	
	private JFrame optionsFrame;
	private JSlider widthSlider,heightSlider;
	
	public Options(){
		// Dimension sliders label
		JLabel slidersLabel = new JLabel("Simulation Dimensions");
		slidersLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Dimension sliders
		widthSlider = new JSlider();
		widthSlider.setMinimum(DIMENSIONS_MIN);
		widthSlider.setMaximum(DIMENSIONS_MAX);
		widthSlider.setMinorTickSpacing(10);
		widthSlider.setMajorTickSpacing(70);
		widthSlider.setPaintTicks(true);
		widthSlider.setPaintLabels(true);
		
		heightSlider = new JSlider();
		heightSlider.setMinimum(DIMENSIONS_MIN);
		heightSlider.setMaximum(DIMENSIONS_MAX);
		heightSlider.setMinorTickSpacing(10);
		heightSlider.setMajorTickSpacing(70);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		
		// Adding the sliders to the panel
		JPanel dimensionsPanel = new JPanel();
		dimensionsPanel.setLayout(new BoxLayout(dimensionsPanel,BoxLayout.X_AXIS));
		dimensionsPanel.add(widthSlider);
		dimensionsPanel.add(new JLabel(" by "));
		dimensionsPanel.add(heightSlider);
		
		// Play Button
		JButton playButton = new JButton("Play");
		playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		playButton.addActionListener(this);
		
		// Creating the main panel
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel,BoxLayout.Y_AXIS));
		optionsPanel.setBorder(BorderFactory.createEmptyBorder(OPTIONS_STRUT_VALUE,OPTIONS_STRUT_VALUE,OPTIONS_STRUT_VALUE,OPTIONS_STRUT_VALUE));
		
		// Adding each previously created item to the main panel.
		optionsPanel.add(Box.createVerticalStrut(OPTIONS_STRUT_VALUE));
		optionsPanel.add(slidersLabel);
		optionsPanel.add(Box.createVerticalStrut(OPTIONS_STRUT_VALUE / 2));
		optionsPanel.add(dimensionsPanel);
		
		optionsPanel.add(Box.createVerticalStrut(OPTIONS_STRUT_VALUE));
		optionsPanel.add(playButton);
		
		// Adding the main panel to the main frame
		optionsFrame = new JFrame();
		optionsFrame.setTitle("Game Of Life: Options");
		optionsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionsFrame.setContentPane(optionsPanel);
		
		// Initialize the execution thread
		optionsFrame.pack();
		optionsFrame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		// Play button was hit, time to launch the simulation.
		// Get rid of old frame and create new widget
		optionsFrame.dispose();
		
		// MVC!!!!---------------------------------------------------------------------------------------------------------------------------
		ViewPanel newPanel = new ViewPanel(widthSlider.getValue(),heightSlider.getValue());
		Model newModel = new Model(widthSlider.getValue(),heightSlider.getValue());
		Controller newController = new Controller(newModel,newPanel);
		
		// Panel for the simulation content
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(newPanel,BorderLayout.CENTER);
		
		// New main frame
		JFrame mainFrame = new JFrame();
		mainFrame.setTitle("Game Of Life");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setContentPane(panel);
		
		// Making the frame visible
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
}