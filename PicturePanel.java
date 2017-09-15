import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.File;
import javax.swing.filechooser.*;

public class PicturePanel extends JPanel {

    // Show 13 obstacles
    private DTPicture[] obstacles = new DTPicture[13];

    private JButton defaultbutton;
    private JPanel mugshots = new JPanel();

    // The currently selected picture's index, or -1 if none is currently
    // selected.
    private String filename;
    private int selectedPic = -1;
    int val;
    private int offset = 720;

    public PicturePanel(int val_) {
	super(new BorderLayout());
	this.val = val_;
	this.layoutComponent();
	this.registerControllers();
    }

    public String filenameValue() {
	return this.filename;
    }

    public void recordLocations(String[] locations) {
	for(int i = 0; i < 13; ++i) {
	    locations[i] = obstacles[i].getMyLocation();
	}
    }

    private void layoutComponent() {
	mugshots.setLayout(null);
	this.setBackground(Color.GREEN);
	mugshots.setBackground(Color.WHITE);
	this.add(mugshots, BorderLayout.CENTER);
	this.setPreferredSize(new Dimension(630, 520));
	this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	JPanel level = new JPanel(new FlowLayout());
	if (this.val == 0) {
	    this.defaultbutton = new JButton("Load obstacles");
	} else {
	    this.defaultbutton = new JButton("Choose a level");
	}
	defaultbutton.setFont(new Font("SansSerif", Font.BOLD, 40));
	level.add(defaultbutton, FlowLayout.LEFT);
	this.add(level, BorderLayout.NORTH);

    }

    private void registerControllers() {
	defaultbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		try {
		int i = 0;
		BufferedReader br;
		if (val == 0) {
		br = new BufferedReader(new FileReader("sample_level.txt"));
		} else {
		// Choose the txt file and write to it
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
		chooser.setFileFilter(filter);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		System.out.println("File name:  " + chooser.getSelectedFile().getName());
		filename = chooser.getSelectedFile().getName();
		}
		br = new BufferedReader(new FileReader(chooser.getSelectedFile()));
		}
		String line;
		int [] nums = new int [52];
		line = br.readLine();
		while(i < 52) {
		line = br.readLine();
		String[] values = line.split(", ");
		for (String str : values) {
		System.out.println(str);
		nums[i] = Integer.parseInt(str);
		++i;
		}
		}
		br.close();

		i = 0;
		while(i < 13) {
		    obstacles[i] = DTPicture.getPictureFromFile("obstacle.png");
		    mugshots.add(obstacles[i]);
		    obstacles[i].setMyLocation(nums[4*i] - offset, nums[4*i+1], nums[4*i+2], nums[4*i+3]);
		    ++i;
		}
		}
		catch (Exception ex) {
		    ex.printStackTrace();
		}

		FocusListener fl = new PicFocusListener();
		for (int i = 0; i <13; i++) {
		    obstacles[i].addFocusListener(fl);
		}
		}
	});
    }

    /*
     * A listener to help us keep track of which picture has been selected.
     */
    class PicFocusListener implements FocusListener {
	public void focusGained(FocusEvent e) {
	    Component c = e.getComponent();
	    for (int i = 0; i < obstacles.length; i++) {
		if (obstacles[i] == c) {
		    selectedPic = i;
		    return;
		}
	    }
	    assert false;
	}

	public void focusLost(FocusEvent e) {
	}
    }
}
