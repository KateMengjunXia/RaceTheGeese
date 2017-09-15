import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Enumeration;
import java.awt.geom.*;

class MyFrame extends JFrame implements Observer {

    // the model that this view is showing
    private Model model;

    // create the buttons
    private JButton playbutton = new JButton("PLAY");
    private JButton loadbutton = new JButton("LOAD A LEVEL");
    private JButton createbutton = new JButton("CREATE A LEVEL");
    private JButton savedbutton = new JButton("EDIT SAVED LEVELS");
    private JButton helpbutton1 = new JButton("?");
    private JButton helpbutton2 = new JButton("?");

    // create the main menu
    JPanel mainMenu = new JPanel();
    // create the level editor view
    JPanel levelEditor = new JPanel();

    MyFrame(Model model_) {

	// create UI
	setBackground(Color.GREEN);
	this.setSize(720, 480);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);


	this.layout1();
	this.layout2();
	this.layoutTabs();
	this.registerControllers();

	// set the model
	model = model_;	
    }

    private void layout1() {
	mainMenu.setLayout(new BorderLayout());
	mainMenu.setBackground(Color.GREEN);

	JPanel buttonPanel1 = new JPanel();
	buttonPanel1.setLayout(null);
	buttonPanel1.setBackground(Color.GREEN);
	playbutton.setBounds(265,130,150,75);
	playbutton.setBackground(Color.GREEN);
	playbutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel1.add(playbutton);
	loadbutton.setBounds(272,215,135,43);
	loadbutton.setBackground(Color.GREEN);
	loadbutton.setFont(new Font("SansSerif", Font.PLAIN, 15));
	buttonPanel1.add(loadbutton);

	JPanel buttonPanel2 = new JPanel();
	buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonPanel2.setBackground(Color.GREEN);
	helpbutton1.setBounds(150,750,60,60);
	buttonPanel2.add(helpbutton1);

	mainMenu.add(buttonPanel1,BorderLayout.CENTER);
	mainMenu.add(buttonPanel2,BorderLayout.SOUTH);
    }

    private void layout2() {
	levelEditor.setLayout(new BorderLayout());
	levelEditor.setBackground(Color.GREEN);

	JPanel buttonPanel3 = new JPanel();
	buttonPanel3.setLayout(null);
	buttonPanel3.setBackground(Color.GREEN);
	createbutton.setBounds(250,130,210,70);
	createbutton.setBackground(Color.GREEN);
	createbutton.setFont(new Font("SansSerif", Font.BOLD, 20));
	savedbutton.setBounds(275,215,160,40);
	savedbutton.setBackground(Color.GREEN);
	savedbutton.setFont(new Font("SansSerif", Font.PLAIN, 15));
	buttonPanel3.add(createbutton);
	buttonPanel3.add(savedbutton);

	JPanel buttonPanel4 = new JPanel();
	buttonPanel4.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonPanel4.setBackground(Color.GREEN);
	helpbutton2.setBounds(150,750,55,60);
	buttonPanel4.add(helpbutton2);

	levelEditor.add(buttonPanel3,BorderLayout.CENTER);
	levelEditor.add(buttonPanel4,BorderLayout.SOUTH);
    }

    private void layoutTabs() {
	// create the tabs for views, add them to the window
	JTabbedPane tabs = new JTabbedPane();
	tabs.add("MAIN MENU", mainMenu);
	tabs.add("LEVEL EDITOR", levelEditor);
	this.getContentPane().add(tabs);
    }


    private void registerControllers() {
	// What to do when the playbutton is clicked
	playbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.useDefaultLevel();
		model.goToGamePlay();
		}
		});
	// What to do when the saved levels button is clicked
	loadbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.useSavedLevel();
		model.goToGamePlay();
		}
		});
	// What to do when the create level button is clicked
	createbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToCreateLevel();
		}
		});
	// What to do when the saved levels button is clicked
	savedbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToSavedLevels();
		}
		});
	// What to do when the help button is clicked
	helpbutton1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToHelp();
		}
		});
	helpbutton2.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToHelp();
		}
		});
    }


    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
	if (model.mainframeValue()) {
	    this.setVisible(true);
	} else {
	    this.setVisible(false);
	}
    }
}
