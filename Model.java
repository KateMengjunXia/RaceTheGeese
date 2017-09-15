import java.awt.Point;
import java.util.*;

import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;

public class Model {
    /** The observers that are watching this model for changes. */
    private List<Observer> observers;

    private boolean mainframe = true;
    private boolean createlevel = false;
    private boolean gameplay = false;
    private boolean savedlevels = false;
    private boolean help = false;

    private boolean defaultlevel = true;

    private Point location = new Point(0, 150);
    private boolean paused = false;
    private boolean reset = false;
    private boolean win = false;
    private boolean lose = false;

    private int scorenum = 0;
    public Timer scoreTimer = new Timer(1000/3, event -> {
	    scorenum++;
	    // update all the views with my change
	    notifyObservers();
	    });
    
    private int game_V;
    private int game_H;

    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
    }

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public boolean mainframeValue() {
	return mainframe;
    }

    public boolean createlevelValue() {
	return createlevel;
    }

    public boolean savedlevelsValue() {
	return savedlevels;
    }

    public boolean gameplayValue() {
	return gameplay;
    }

    public Point locationValue() {
	return location;
    }

    public boolean pausedValue() {
	return paused;
    }

    public boolean resetValue() {
	return reset;
    }

    public int getScore() {
	return scorenum;
    }

    public boolean winValue() {
	return win;
    }

    public boolean loseValue() {
	return lose;
    }

    public boolean defaultLevelValue() {
	return defaultlevel;
    }

    public boolean helpValue() {
	return help;
    }

    

    public void goToCreateLevel() {
	// switich from main menu to create level view
	mainframe = false;
	createlevel = true;
	gameplay = false;
	savedlevels = false;
	help = false;

	// update all the views with my change
	notifyObservers();
    }

    public void startScoreTimer() {
	this.scoreTimer.start();
    }

    public void goToGamePlay() {
	// switich from main menu to game play view
	mainframe = false;
	createlevel = false;
	gameplay = true;
	savedlevels = false;
	help = false;

	location.x = 0;
	location.y = 150;
	scorenum = 0;
	paused = false;
	reset = true;
	win = false;
	lose = false;

	// update all the views with my change
	notifyObservers();
    }

    public void goToMain() {
	// switich from create level view to main menu
	mainframe = true;
	createlevel = false;
	gameplay = false;
	savedlevels = false;
	help = false;

	scoreTimer.stop();
	// update all the views with my change
	notifyObservers();
    }

    public void goToSavedLevels() {
	// switich from main menu to saved levels view
	mainframe = false;
	createlevel = false;
	gameplay = false;
	savedlevels = true;
	help = false;

	// update all the views with my change
	notifyObservers();
    }

    public void goToHelp() {
	// switich from main menu to help view
	mainframe = false;
	createlevel = false;
	gameplay = false;
	savedlevels = false;
	help = true;

	// update all the views with my change
	notifyObservers();
    }


    public void goUp() {
	if (location.y <= 0) {
	    location.y = 0;
	    System.out.println("Cannot move up anymore!");
	} else {
	    location.y -= 10;
	}
	// update all the views with my change
	notifyObservers();
    }

    public void goDown() {
	if (location.y >= 320) {
	    location.y = 320;
	    System.out.println("Cannot move down anymore!");
	} else {
	    location.y += 10;
	}
	// update all the views with my change
	notifyObservers();
    }

    public void goLeft() {
	if (location.x <= -50) {
	    // player loses
	    System.out.println("You lose!");
	    lose = true;
	    pauseGame();
	} else {
	    location.x -= 10;
	    // update all the views with my change
	    notifyObservers();
	}	
    }

    public void goRight() {
	if (location.x >= 720) {
	    System.out.println("You win!");
	    win = true;
	    pauseGame();
	} else {
	    location.x += 10;
	    // update all the views with my change
	    notifyObservers();
	}
    }

    public void pauseGame() {
	if (!paused) {
	    scoreTimer.stop();
	} else {
	     scoreTimer.start();
	}
	paused = !paused;
	// update all the views with my change
	notifyObservers();
    }

    public void restartGame() {
	location.x = 0;
	location.y = 150;
	scorenum = 0;
	paused = false;
	reset = true;
	win = false;
	lose = false;
	scoreTimer.start();
	// update all the views with my change
	notifyObservers();
    }

    public void resetValueReverse() {
	reset = false;
    }

    public void collision() {
	lose = true;
	pauseGame();
    }

    public void useDefaultLevel() {
	defaultlevel = true;
    }

    public void useSavedLevel() {
	defaultlevel = false;
    }
}
