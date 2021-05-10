package engine;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import core.Game;

public class DualThreadEngine {
	
	private final int GLOBAL_FPS_CAP;
	private JFrame window;
	private Canvas gameArea;
	private Runnable renderLoop;
	private Runnable updateLoop;
	private ScheduledExecutorService executor;
	private int tickRate;
	private int fps;
	private Game game;
	
	public DualThreadEngine() {
		// default size
		this(1280, 720);
	}
	
	public DualThreadEngine(int cw, int ch) {
		GLOBAL_FPS_CAP = 300;
		tickRate = 60;
		fps = 0;
		
		window = new JFrame(":D");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		gameArea = new Canvas();
		gameArea.setSize(cw, ch);
		window.add(gameArea);
		window.pack();
	}
	
	public void load(Game newGame) {
		if (newGame != null) {
			game = newGame;
			gameArea.setSize(newGame.getWidth(), newGame.getHeight());
			
			// Graphics
			try {
				if (gameArea.getBufferStrategy() == null)
					gameArea.createBufferStrategy(3);
			} catch (Exception e) {
				System.err.println("Couldn't create graphics");
				e.printStackTrace();
				return;
			}
			window.pack();
			window.setLocationRelativeTo(null);
			
			// Loops
			renderLoop = new Runnable() {
				public void run() {
					BufferStrategy bs = gameArea.getBufferStrategy();
					Graphics2D g = (Graphics2D) bs.getDrawGraphics();
					game.draw(g);
					g.dispose();
					bs.show();
				}
			};
			
			updateLoop = new Runnable() {
				public void run() {
					if (game.done()) {
						System.out.println("DONE!");
						executor.shutdown();
					}
					else {
						game.update();
					}					
				}
			};
			window.setVisible(true);
		}
	}
	
	public void start() {
		int renderDelay = (fps > 0) ? (int) (1e9 / fps) : GLOBAL_FPS_CAP;  
		int updateDelay = (tickRate > 0) ? (int) (1e9 / tickRate) : 0;
		
		
		// Multi-Threading
		executor = Executors.newScheduledThreadPool(2);
		executor.scheduleWithFixedDelay(renderLoop, 0, renderDelay, TimeUnit.NANOSECONDS);
		executor.scheduleWithFixedDelay(updateLoop, 0, updateDelay, TimeUnit.NANOSECONDS);
		
	}
	
	public void setTickRate(int rate) {
		tickRate = (rate > 0) ? rate : tickRate;
	}
	
	public void setFPS(int newFPS) {
		fps = (newFPS >= 0) ? newFPS : fps;
	}
	
	public int getTickRate() {
		return tickRate;
	}
	
	public int getFPS() {
		return fps;
	}
}
