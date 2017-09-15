import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/*
 * A DTPicture can display an image and take care
 * of data transfer (hence the DT in the name) operations.
 */
class DTPicture extends JComponent {
    private Image image;

    int offset = 720;

    /* Create a picture using getPictureFromFile */
    private DTPicture(Image image) {
	super();
	this.image = image;
	this.setFocusable(true);

	DragGesture dg = new DragGesture();
	this.addMouseListener(dg);
	this.addMouseMotionListener(dg);
	this.addFocusListener(new HighlightWhenFocusedListener());
    }

    public void setMyLocation(int x, int y, int w, int h) {
	this.setBounds(x, y, w, h);
    }

    public String getMyLocation() {
	return (this.getX()+offset)  + ", " + this.getY() + ", " + this.getWidth() + ", " + this.getHeight();
    }

    /* Get a picture from an image file. */
    public static DTPicture getPictureFromFile(String path) {
	if (path == null) {
	    return new DTPicture(null);
	}

	java.net.URL imageURL = Main.class.getResource(path);

	if (imageURL == null) {
	    System.err.println("Resource not found: " + path);
	    return new DTPicture(null);
	} else {
	    return new DTPicture(new ImageIcon(imageURL, path).getImage());
	}
    }

    public void setImage(Image image) {
	this.image = image;
	this.repaint();
    }

    public Image getImage() {
	return this.image;
    }


    protected void paintComponent(Graphics graphics) {
	Graphics g = graphics.create();

	// Draw in our entire space, even if isOpaque is false.
	g.setColor(Color.GREEN);

	g.fillRect(0, 0, image == null ? 125 : image.getWidth(this),
		image == null ? 125 : image.getHeight(this));

	/*
	if (image != null) {
	    // Draw image at its natural size of 125x125.
	    g.drawImage(image, 0, 0, this);
	}
	*/

	// Add a border, red if picture currently has focus
	if (this.isFocusOwner()) {
	    g.setColor(Color.RED);
	} else {
	    g.setColor(Color.BLACK);
	}
	g.drawRect(0, 0, image == null ? 125 : image.getWidth(this),
		image == null ? 125 : image.getHeight(this));
	g.dispose();
    }

    class HighlightWhenFocusedListener implements FocusListener {
	public void focusGained(FocusEvent e) {
	    // Draw the component with a red border
	    // indicating that it has focus.
	    DTPicture.this.repaint();
	}

	public void focusLost(FocusEvent e) {
	    // Draw the component with a black border
	    // indicating that it doesn't have focus.
	    DTPicture.this.repaint();
	}

    }

    /*---------------------- Drag 'n Drop support ----------------------*/

    // MouseInputAdapter implements and provides default methods for 
    // both MouseListener and MouseMotionListener interfaces.
    class DragGesture extends MouseInputAdapter {
	private Point lastPoint;

	public void mouseClicked(MouseEvent e) {
	    requestFocusInWindow();
	}	

	public void mousePressed(MouseEvent e) {
	    lastPoint = e.getPoint();
	}

	public void mouseDragged(MouseEvent e) {
	    int dx = e.getX() - lastPoint.x;
	    int dy = e.getY() - lastPoint.y;
	    if (dx > 1 || dy > 1) {
		DTPicture.this.setLocation(DTPicture.this.getLocation().x + dx, DTPicture.this.getLocation().y + dy);
	    }
	}	
    }

}
