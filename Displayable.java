import java.awt.*;

import static java.lang.Math.*;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public interface Displayable {
    void paint(Graphics g);
}

class Player implements Displayable {
    private int x, y, width, height;
    public boolean win;
    public boolean lose;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 30;
        this.height = 30;
    }
    public void movePlayer(Point p) {
	this.x = p.x;
	this.y = p.y;
    }
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
}

class Obstacle implements Displayable {
    private int x, y, width, height;
    private int x_, y_;
    Image image;
    public Obstacle(int x, int y, int width, int height) {
        this.x = x;
	this.x_ = x;
        this.y = y;
	this.y_ = y;
        this.width = width;
        this.height = height;
	try {
	    image = ImageIO.read(this.getClass().getResource("obstacle.png"));
	} catch (IOException ex) {
	    ex.printStackTrace();
	} 
    }
    
    public void loadVal(int val1, int val2, int val3, int val4) {
	this.x = val1;
	this.x_ = val1;
	this.y = val2;
	this.y_ = val2;
	this.width = val3;
	this.height = val4;
    }

    public boolean move(Point p) {
        this.x_ -= 3;
	return (this.x_ < (p.x + 35) && this.x_ > (p.x - this.width) && this.y_ < (p.y + 35) && this.y_ > (p.y - this.height));
    }
    public void reset() {
	this.x_ = this.x;
	this.y_ = this.y;
    }
    public void paint(Graphics g) {
	/*
        g.setColor(Color.BLUE);
        g.fillRect(this.x_, this.y_, this.width, this.height);
	*/
	g.drawImage(image, this.x_, this.y_, this.width, this.height, null);
    }
}
