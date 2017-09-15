import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Enumeration;
import java.awt.geom.*;

import java.awt.image.BufferedImage;
import javax.swing.Timer;

class GamePlayView extends JFrame implements Observer {

    // the model that this view is showing
    private Model model;

    // create the widgets
    public JButton pausebutton = new JButton("PAUSE");
    private JButton restartbutton = new JButton("RESTART");
    private JButton backbutton = new JButton("BACK");
    private JLabel score = new JLabel("Score:");
    private JLabel num = new JLabel("0");
    private Game game = new Game();
    private boolean initialized;

    private Timer collisionTimer = new Timer(1000/30, event -> {
	    if (this.game.collisionValue()) {
	    model.collision();
	    }
	    });


    GamePlayView(Model model_) {
	// create UI
	setBackground(Color.GREEN);
	this.setSize(720, 480);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setVisible(false);
	this.initialized = false;

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

	score.setFont(new Font("SansSerif", Font.PLAIN, 15));
	score.setAlignmentX(0.5f);
	buttonPanel1.add(score);

	num.setFont(new Font("SansSerif", Font.PLAIN, 15));
	num.setAlignmentX(0.5f);
	buttonPanel1.add(num);

	// button panel 2
	JPanel buttonPanel2 = new JPanel();
	buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
	buttonPanel2.setBackground(Color.GREEN);

	pausebutton.setAlignmentX(0.5f);
	pausebutton.setLayout(null);
	pausebutton.setBackground(Color.GREEN);
	pausebutton.setBounds(150,65,150,75);
	pausebutton.setBackground(Color.GREEN);
	pausebutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel2.add(pausebutton);

	restartbutton.setLayout(null);
	restartbutton.setBackground(Color.GREEN);
	restartbutton.setBounds(450,65,150,75);
	restartbutton.setBackground(Color.GREEN);
	restartbutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel2.add(restartbutton);

	backbutton.setLayout(null);
	backbutton.setBackground(Color.GREEN);
	backbutton.setBounds(450,65,150,75);
	backbutton.setBackground(Color.GREEN);
	backbutton.setFont(new Font("SansSerif", Font.BOLD, 30));
	buttonPanel2.add(backbutton);
		
	this.add(buttonPanel1,BorderLayout.NORTH);
	this.add(buttonPanel2,BorderLayout.SOUTH);
	this.getContentPane().add(game);
    }

    private void registerControllers() {
	// What to do when the back button is clicked
	backbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.goToMain();
		}
		});
	// What to do when the pause button is clicked
	pausebutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.pauseGame();
		if (!model.pausedValue()) {
		pausebutton.setText("PAUSE");
		game.requestFocusInWindow();
		} else {
		pausebutton.setText("RESUME");
		}
		}
		});
	// What to do when the restart button is clicked
	restartbutton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
		model.restartGame();
		game.requestFocusInWindow();
		}
		});

	// Using Key Listeners to handle key events
	game.addKeyListener(new KeyListener() {
		@Override
		public void keyTyped(KeyEvent evt) {}
		@Override
		public void keyReleased(KeyEvent evt) {}
		@Override
		public void keyPressed(KeyEvent evt) {
		System.out.println("Player moved!");
		int key = evt.getKeyCode();
		if (key == KeyEvent.VK_UP) model.goUp();
		else if (key == KeyEvent.VK_DOWN) model.goDown();
		else if (key == KeyEvent.VK_LEFT) model.goLeft();
		else if (key == KeyEvent.VK_RIGHT) model.goRight();;
		}
		});
    }



    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
	if (model.gameplayValue()) {
	    if (!initialized) {
		initialized = true;
		game.setDefaultlevel(model.defaultLevelValue());
		game.placeObstacles();
	    }
	    this.setVisible(true);
	    game.setFocusable(true);
	    game.requestFocusInWindow();
	    // this.game.startTimer();
	    this.collisionTimer.start();
	    num.setText(Integer.toString(model.getScore()));

	    if (model.pausedValue()) {
		if (model.winValue()) {
		    pausebutton.setVisible(false);
		    game.setFocusable(false);
		    // DISPLAY WIN MESSAGE!
		    score.setFont(new Font("SansSerif", Font.BOLD, 30));
		    score.setText("YOU WIN!");
		} else if (model.loseValue()) {
		    pausebutton.setVisible(false);
		    game.setFocusable(false);
		    // DISPLAY LOSE MESSAGE!
		    score.setFont(new Font("SansSerif", Font.BOLD, 30));
		    score.setText("YOU LOSE!");
		}
		this.game.pauseTimer();
		collisionTimer.stop();
	    } else if (!game.placingValue()) {
		model.startScoreTimer();
		this.game.startTimer();
	    }
    	    
	    if (model.resetValue()) {
		model.resetValueReverse();
		pausebutton.setVisible(true);
		game.setFocusable(true);
		score.setFont(new Font("SansSerif", Font.PLAIN, 15));
		score.setAlignmentX(0.5f);
		score.setText("Score:");
		num.setText(Integer.toString(0));
		this.game.restart();
		this.game.clearCollision();
	    }
	    this.game.updateLocation(model.locationValue());
	} else {
	    this.setVisible(false);
	    if (initialized) {
		initialized = false;
		this.game.pauseTimer();
		collisionTimer.stop();
	    }
	}
    }
}


