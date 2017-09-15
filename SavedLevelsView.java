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
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;

class SavedLevelsView extends JFrame implements Observer {

    // the model that this view is showing
    private Model model;

    // create the widgets
    private JButton savebutton = new JButton("SAVE");
    private JButton backbutton = new JButton("BACK");
    
    PicturePanel dnd = new PicturePanel(1);
    
    SavedLevelsView(Model model_) {

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
	
	this.add(buttonPanel2,BorderLayout.SOUTH);
    }

    private void addDND() {
	this.getContentPane().add(dnd);
        this.pack();
    }

    private void registerControllers() {
	// What to do when the back button is clicked
	backbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToMain();
		}
		});
	// What to do when the save button is clicked
	savebutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {

		// get obstacles' locations
		String[] locations = new String[13];
		dnd.recordLocations(locations);	

		try {
		BufferedReader br = new BufferedReader(new FileReader(dnd.filenameValue()));
		String firstline = br.readLine();
		br.close();
		FileWriter fw = new FileWriter(dnd.filenameValue());
		fw.append(firstline);
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

	
		
		model.goToMain();
		}
		});
    }
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
	if (model.savedlevelsValue()) {
	    this.addDND();
	    this.setVisible(true);
	} else {
	    this.setVisible(false);
	}
    }
}
