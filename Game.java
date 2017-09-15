import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static java.awt.image.BufferedImage.TYPE_3BYTE_BGR;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.File;
import javax.swing.filechooser.*;

public class Game extends JPanel {
    // create the widgets
    private Player p = new Player(0, 150);
    private Obstacle o1 = new Obstacle(750, 70, 30, 90);
    private Obstacle o2 = new Obstacle(850, 250, 30, 60);
    private Obstacle o3 = new Obstacle(1000, 170, 30, 60);
    private Obstacle o4 = new Obstacle(1000, 20, 30, 90);
    private Obstacle o5 = new Obstacle(1050, 220, 30, 120);
    private Obstacle o6 = new Obstacle(1200, 50, 30, 90);
    private Obstacle o7 = new Obstacle(1300, 70, 30, 120);
    private Obstacle o8 = new Obstacle(1450, 150, 30, 120);
    private Obstacle o9  = new Obstacle(1500, 240, 60, 90);
    private Obstacle o10 = new Obstacle(1600, 180, 90, 150);
    private Obstacle o11 = new Obstacle(1750, 150, 60, 120);
    private Obstacle o12 = new Obstacle(1850, 50, 30, 150);
    private Obstacle o13 = new Obstacle(1950, 50, 30, 180);

    // variables
    private int [] nums = new int [52];
    private boolean defaultlevel = true;
    private Point pLocation;
    private int FPS = 30;
    private LinkedList<Displayable> displayables = new LinkedList<Displayable>();
    private Timer animationTimer;
    private boolean collision;
    private boolean placing;
    private boolean usingDefault = true;

    public Game() {
	super();
	this.setLayout(null);	
	this.collision = false;
	this.addObstacles();
	// Using a Timer to handle animation.
	this.animationTimer = new Timer(1000/this.FPS, event -> {
		if (!collision) {
		collision =  this.handleAnimation();
		this.repaint(); // note that we call repaint, not paintComponent
		}
		});
    }

    public void setDefaultlevel(boolean val) {
	this.defaultlevel = val;
    }

    public boolean placingValue() {
	return placing;
    }

    public void addObstacles() {
	// Add player and obstacles
	this.displayables.add(this.p);
	this.displayables.add(this.o1);
	this.displayables.add(this.o2);
	this.displayables.add(this.o3);
	this.displayables.add(this.o4);
	this.displayables.add(this.o5);
	this.displayables.add(this.o6);
	this.displayables.add(this.o7);
	this.displayables.add(this.o8);
	this.displayables.add(this.o9);
	this.displayables.add(this.o10);
	this.displayables.add(this.o11);
	this.displayables.add(this.o12);
	this.displayables.add(this.o13);
    }

    public void placeObstacles() {
	if (!this.defaultlevel) {
	    try {
		usingDefault = false;
		placing = true;
		this.animationTimer.stop();
		int i = 0;
		// Choose the txt file and write to it
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text", "txt");
		chooser.setFileFilter(filter);
		int result = chooser.showOpenDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
		    System.out.println("File name:  " + chooser.getSelectedFile().getName());
		}
		BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()));
		String line;
		line = br.readLine();
		String[] dimension = line.split(", ");
		this.setSize(new Dimension(Integer.parseInt(dimension[0]), Integer.parseInt(dimension[1])));
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
		o1.loadVal(nums[0], nums[1], nums[2], nums[3]);
		o2.loadVal(nums[4], nums[5], nums[6], nums[7]);
		o3.loadVal(nums[8], nums[9], nums[10], nums[11]);
		o4.loadVal(nums[12], nums[13], nums[14], nums[15]);
		o5.loadVal(nums[16], nums[17], nums[18], nums[19]);
		o6.loadVal(nums[20], nums[21], nums[22], nums[23]);
		o7.loadVal(nums[24], nums[25], nums[26], nums[27]);
		o8.loadVal(nums[28], nums[29], nums[30], nums[31]);
		o9.loadVal(nums[32], nums[33], nums[34], nums[35]);
		o10.loadVal(nums[36], nums[37], nums[38], nums[39]);
		o11.loadVal(nums[40], nums[41], nums[42], nums[43]);
		o12.loadVal(nums[44], nums[45], nums[46], nums[47]);
		o13.loadVal(nums[48], nums[49], nums[50], nums[51]);

		this.restart();

		this.animationTimer.start();
		placing = false;
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	} else if (!usingDefault) {
	    usingDefault = true;
	    o1.loadVal(750, 70, 30, 90);
	    o2.loadVal(850, 250, 30, 60);
	    o3.loadVal(1000, 170, 30, 60);
	    o4.loadVal(1000, 20, 30, 90);
	    o5.loadVal(1050, 220, 30, 120);
	    o6.loadVal(1200, 50, 30, 90);
	    o7.loadVal(1300, 70, 30, 120);
	    o8.loadVal(1450, 150, 30, 120);
	    o9.loadVal(1500, 240, 60, 90);
	    o10.loadVal(1600, 180, 90, 150);
	    o11.loadVal(1750, 150, 60, 120);
	    o12.loadVal(1850, 50, 30, 150);
	    o13.loadVal(1950, 50, 30, 180);
	}	    

    }

    public void startTimer() {
	this.animationTimer.start();
    }

    public void pauseTimer() {
	this.animationTimer.stop();
    }

    public void updateLocation(Point p) {
	pLocation = p;
    }

    public void restart() {
	this.o1.reset();
	this.o2.reset();
	this.o3.reset();
	this.o4.reset();
	this.o5.reset();
	this.o6.reset();
	this.o7.reset();
	this.o8.reset();
	this.o9.reset();
	this.o10.reset();
	this.o11.reset();
	this.o12.reset();
	this.o13.reset();
    }

    public boolean collisionValue() {
	return collision;
    }

    public void clearCollision() {
	collision = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
	// dBuff and gBuff are used for double buffering
	Image dBuff = new BufferedImage(this.getWidth(), this.getHeight(), TYPE_3BYTE_BGR);
	Graphics gBuff = dBuff.getGraphics();
	gBuff.setClip(0, 0, this.getWidth(), this.getHeight());
	gBuff.setColor(Color.WHITE);
	gBuff.fillRect(0, 0, this.getWidth(), this.getHeight());
	for (Displayable d : this.displayables) {
	    d.paint(gBuff);
	}
	g.drawImage(dBuff, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    private boolean handleAnimation() {
	this.p.movePlayer(pLocation);
	return (this.o1.move(pLocation) || this.o2.move(pLocation) || this.o3.move(pLocation) || this.o4.move(pLocation) || this.o5.move(pLocation) || this.o6.move(pLocation) || this.o7.move(pLocation) || this.o8.move(pLocation) || this.o9.move(pLocation) || this.o10.move(pLocation) || this.o11.move(pLocation) || this.o12.move(pLocation) || this.o13.move(pLocation));
    }
}
