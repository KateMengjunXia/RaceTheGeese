import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
	// create the model
        Model model = new Model();

	// create the windows
	MyFrame frame = new MyFrame(model);
	frame.setTitle("Race the geese");
	CreateLevelView frame2 = new CreateLevelView(model);
	frame2.setTitle("Create a level");
	GamePlayView frame3 = new GamePlayView(model);
	frame3.setTitle("Gameplay");
	SavedLevelsView frame4 = new SavedLevelsView(model);
	frame4.setTitle("Edit saved levels");
	HelpView frame5 = new HelpView(model);
	frame5.setTitle("Help");

	frame.setVisible(true);
	frame2.setVisible(false);
	frame3.setVisible(false);
	frame4.setVisible(false);
	frame5.setVisible(false);

	// tell the model about the main frame and the create level view
	model.addObserver(frame);
	model.addObserver(frame2);
	model.addObserver(frame3);
	model.addObserver(frame4);
	model.addObserver(frame5);

	// let all the views know that they are connected to the model
	model.notifyObservers();
    }
}
