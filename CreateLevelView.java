import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Enumeration;
import java.awt.geom.*;

import javax.swing.JScrollPane;
import java.awt.Dimension;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.filechooser.*;
import java.io.IOException;

class CreateLevelView extends JFrame implements Observer {

    // the model that this view is showing
    private Model model;

    // create the widgets
    private JButton savebutton = new JButton("SAVE");
    private JButton backbutton = new JButton("BACK");
    private JLabel name = new JLabel("Level name:");
    private JTextArea namebox = new JTextArea(1, 8);
    private JLabel sizeV = new JLabel("Choose vertical size");
    private SpinnerNumberModel spinnerNumV = new SpinnerNumberModel(15,15,100,1);
    private JSpinner spinnerV = new JSpinner(spinnerNumV);
    private JLabel sizeH = new JLabel("Choose horizontal size");
    private SpinnerNumberModel spinnerNumH = new SpinnerNumberModel(15,15,100,1);
    private JSpinner spinnerH = new JSpinner(spinnerNumH);

    PicturePanel dnd = new PicturePanel(0);
    
    CreateLevelView(Model model_) {

	// create UI
	setBackground(Color.GREEN);
	this.setSize(720, 480);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(false);

	this.layoutFrame();
	this.registerControllers();

	// set the model
	model = model_;
    }

    private void layoutFrame() {
	this.setLayout(new BorderLayout());

	// button panel 1
	JPanel buttonPanel1 = new JPanel();
	buttonPanel1.setLayout(new BoxLayout(buttonPanel1, BoxLayout.Y_AXIS));
	buttonPanel1.setBackground(Color.GREEN);

	name.setFont(new Font("SansSerif", Font.PLAIN, 15));
	name.setAlignmentX(0.5f);
	buttonPanel1.add(name);
	namebox.setMaximumSize(new Dimension(100, 40));
	buttonPanel1.add(namebox);

	sizeV.setFont(new Font("SansSerif", Font.PLAIN, 15));
	sizeV.setAlignmentX(0.5f);
	buttonPanel1.add(sizeV);
	spinnerV.setMaximumSize(new Dimension(100, 40));
	buttonPanel1.add(spinnerV);

	sizeH.setFont(new Font("SansSerif", Font.PLAIN, 15));
	sizeH.setAlignmentX(0.5f);
	buttonPanel1.add(sizeH);
	spinnerH.setMaximumSize(new Dimension(100, 40));
	buttonPanel1.add(spinnerH);

	// button panel 2
	JPanel buttonPanel2 = new JPanel();
	buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonPanel2.setBackground(Color.GREEN);

	savebutton.setAlignmentX(0.5f);
	savebutton.setLayout(null);
	savebutton.setBackground(Color.GREEN);
	savebutton.setBounds(150,65,150,75);
	savebutton.setBackground(Color.GREEN);
	savebutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel2.add(savebutton);

	backbutton.setLayout(null);
	backbutton.setBackground(Color.GREEN);
	backbutton.setBounds(450,65,150,75);
	backbutton.setBackground(Color.GREEN);
	backbutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel2.add(backbutton);

	this.add(buttonPanel1,BorderLayout.NORTH);
	this.add(buttonPanel2,BorderLayout.SOUTH);
    }

    private void addDND() {
	this.getContentPane().add(dnd);
        this.pack();
    }

    private void registerControllers() {
	// make sure that horizon size is no smaller than vertical size
	spinnerNumV.addChangeListener(new ChangeListener() {      
		public void stateChanged(ChangeEvent evt) {
		int v = (Integer) spinnerNumV.getValue();
		int h = (Integer) spinnerNumH.getValue();
		if (h < v) {
		spinnerNumH.setValue(v);
		}
		spinnerNumH.setMinimum(v);
		}
		});
	// What to do when the back button is clicked
	backbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		namebox.setText(null);
		spinnerNumV.setValue(15);
		spinnerNumV.setMinimum(15);
		spinnerNumH.setValue(15);
		spinnerNumH.setMinimum(15);
		model.goToMain();
		}
		});
	// What to do when the save button is clicked
	savebutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		String name = namebox.getText();
		int v = (Integer) spinnerNumV.getValue();
		int h = (Integer) spinnerNumH.getValue();

		// get obstacles' locations
		String[] locations = new String[13];
		dnd.recordLocations(locations);	

		try {
		    FileWriter fw = new FileWriter(name + ".txt", true);
		    // write level name and world dimension
		    fw.append(Integer.toString(30*h));
		    fw.append(", ");
		    fw.append(Integer.toString(30*v));
		    fw.append('\n');

		    // get obstacles' locations
		    for(int i = 0; i < 13; ++i) {
		    fw.append(locations[i]);
		    fw.append('\n');
		    }
		    fw.close();
		}
		catch (Exception ex) {
		ex.printStackTrace();
		}
		
		namebox.setText(null);
		spinnerNumV.setValue(15);
		spinnerNumV.setMinimum(15);
		spinnerNumH.setValue(15);
		spinnerNumH.setMinimum(15);
		model.goToMain();
		}
		});
    }
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
	if (model.createlevelValue()) {
	    this.addDND();
	    this.setVisible(true);
	} else {
	    this.setVisible(false);
	}
    }
}



