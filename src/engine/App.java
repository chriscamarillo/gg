package engine;

import java.awt.Graphics2D;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class App {
	JFrame GUI;
	DualThreadEngine manager;
	Window gameWindow;

	public App(int width, int height) {
		gameWindow = new Window(width, height);
		GUI = new JFrame();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.add(gameWindow);
		GUI.pack();
		GUI.setLocationRelativeTo(null);

		manager = new DualThreadEngine();
		manager.load(this);
	}

	public void start() {
		GUI.setVisible(true);
		manager.start();
	}

	public void addKeyListener(KeyListener l) {
		gameWindow.addKeyListener(l);
	}

	public int getWidth() {
		return gameWindow.getWidth();
	}

	public int getHeight() {
		return gameWindow.getHeight();
	}

	public void update() {
	}

	public void draw(Graphics2D g) {
	}

	public App() {
		this(Constants.DEFAULT_WINDOW_WIDTH, Constants.DEFAULT_WINDOW_HEIGHT);
	}

}
