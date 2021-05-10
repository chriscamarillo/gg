package games;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import core.Game;

public class Hockey implements Game {
	
	public final int WIDTH;
	public final int HEIGHT;
	public final int PUCK_MAX_SPEED;
	public final int PADDLE_SPEED;
	private volatile int score;
	private Rectangle2D.Double paddle1, paddle2;
	private Ellipse2D.Double puck;
	
	
	public Hockey(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		PUCK_MAX_SPEED = 5;
		PADDLE_SPEED = 2;
		
		score = 0;
		
		double paddleHeight = HEIGHT * 0.2;
		double paddleWidth = WIDTH * 0.02;
		double puckRadius = WIDTH * 0.03;
		
		paddle1 = new Rectangle2D.Double(2 * paddleWidth, (HEIGHT - paddleHeight) / 2, paddleWidth, paddleHeight);
		paddle2 = new Rectangle2D.Double(WIDTH - 3 * paddleWidth, (HEIGHT - paddleHeight) / 2, paddleWidth, paddleHeight);
		puck = new Ellipse2D.Double((WIDTH - puckRadius) / 2, (HEIGHT - puckRadius) / 2, puckRadius, puckRadius);
	}
	
	@Override
	public void update() {
		System.out.println("[UPDATE]");
		this.score++;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.fill(paddle1);
		g.fill(paddle2);
		g.fill(puck);
	}
	
	@Override
	public boolean done() {
		return this.score > 100;
	}
	
	@Override
	public int getWidth() {
		return WIDTH;
	}
	
	@Override
	public int getHeight() {
		return HEIGHT;
	}
	
	@Override
	public String toString() {
		return Integer.toString(score);
	}
	
	public static void main(String args[]) throws Exception {
		Hockey game = new Hockey(1280, 720);
		while (!game.done()) {
			game.update();
			System.out.println(game);
		}
	}

}
