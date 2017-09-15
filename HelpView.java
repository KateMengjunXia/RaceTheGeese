import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Enumeration;
import java.awt.geom.*;

class HelpView extends JFrame implements Observer {

    // the model that this view is showing
    private Model model;

    // create the button
    private JButton backbutton = new JButton("BACK");
    private JLabel text = new JLabel("Reach the right of the screen to win. Don't forget to avoid any geese in the way! Move player using the arrow keys.");

    HelpView(Model model_) {

	// create UI
	setBackground(Color.GREEN);
	this.setSize(720, 480);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(true);


	this.layoutFrame();
	this.registerControllers();

	// set the model
	model = model_;	
    }

    private void layoutFrame() {
	this.setLayout(new BorderLayout());

	JPanel buttonPanel2 = new JPanel();
	buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonPanel2.setBackground(Color.GREEN);

	backbutton.setLayout(null);
	backbutton.setBackground(Color.GREEN);
	backbutton.setBounds(450,65,150,75);
	backbutton.setBackground(Color.GREEN);
	backbutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel2.add(backbutton);

	this.add(text,BorderLayout.CENTER);
	this.add(buttonPanel2,BorderLayout.SOUTH);
    }


    private void registerControllers() {
	// What to do when the playbutton is clicked
	backbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToMain();
		}
		});
    }


    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
	if (model.helpValue()) {
	    this.setVisible(true);
	} else {
	    this.setVisible(false);
	}
    }
}

