package games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Random;

import engine.App;
import math.Vector;

public class Pong extends App {
	public final int SCREEN_WIDTH = 1280;
	public final int SCREEN_HEIGHT = 720;

	public final double PUCK_MAX_SPEED = 15;
	public final double PUCK_RADIUS = SCREEN_WIDTH * 0.03;
	public final double PUCK_INITIAL_X = (SCREEN_WIDTH - PUCK_RADIUS) / 2;
	public final double PUCK_INITIAL_Y = (SCREEN_HEIGHT - PUCK_RADIUS) / 2;

	public final double PADDLE_SPEED = 10;
	public final double PADDLE_WIDTH = SCREEN_WIDTH * 0.02;
	public final double PADDLE_HEIGHT = SCREEN_WIDTH * 0.2;
	public final double PADDLE_1_INITIAL_X = 2 * PADDLE_WIDTH;
	public final double PADDLE_1_INITIAL_Y = (SCREEN_HEIGHT - PADDLE_HEIGHT) / 2;
	public final double PADDLE_2_INITIAL_X = SCREEN_WIDTH - 3 * PADDLE_WIDTH;
	public final double PADDLE_2_INITIAL_Y = (SCREEN_HEIGHT - PADDLE_HEIGHT) / 2;

	private volatile int score;
	private volatile boolean isRunning;
	private Rectangle2D.Double paddle1;
	private Rectangle2D.Double paddle2;
	private Ellipse2D.Double puck;
	private Vector p1Velocity;
	private Vector p2Velocity;
	private Vector puckVelocity;

	public Pong() {
		resetPaddles();
		resetPuck();
		addKeyListener(controls());
		score = 0;
		isRunning = true;
	}

	@Override
	public void update() {
		if (isRunning) {
			moveObject(paddle1, p1Velocity);
			keepInbounds(paddle1);
			moveObject(paddle2, computeCpuVelocity(p2Velocity));
			keepInbounds(paddle2);
			moveObject(puck, puckVelocity);

			if (puck.getX() < 0 || puck.getX() > SCREEN_WIDTH - 1) {
				resetPuck();
			}

			if (puck.getY() < 0 || puck.getY() > SCREEN_HEIGHT - 1) {
				// TODO: add better bouncing mechanics
				puckVelocity = Vector.hadamard(puckVelocity, new Vector(1, -1));
				
			}

			if (puck.intersects(paddle1) || puck.intersects(paddle2)) {
				puckVelocity = Vector.mult(puckVelocity, -1);
			}

			// Stop moving
			p1Velocity = new Vector();
			p2Velocity = new Vector();
		}
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.white);
		g.fill(paddle1);
		g.fill(paddle2);
		g.fill(puck);
	}

	@Override
	public String toString() {
		return Integer.toString(score);
	}

	private void moveObject(RectangularShape object, Vector amount) {
		double newX = object.getX() + amount.getX();
		double newY = object.getY() + amount.getY();

		object.setFrame(newX, newY, object.getWidth(), object.getHeight());
	}

	private void keepInbounds(RectangularShape object) {
		double newX = object.getX();
		double newY = object.getY();
		
		newX = newX < 0 ? 0 : newX;
		newX = newX > SCREEN_WIDTH - object.getWidth() ? SCREEN_WIDTH - object.getWidth() : newX;
		newY = newY < 0 ? 0 : newY;
		newY = newY > SCREEN_HEIGHT - object.getHeight() ? SCREEN_HEIGHT - object.getHeight() : newY;
		
		object.setFrame(newX, newY, object.getWidth(), object.getHeight());
	}
	
	private Vector computeCpuVelocity(Vector currentVelocity) {
		Vector randAcceleration = new Vector(0, (new Random().nextGaussian() * PADDLE_SPEED * 0.20));
		Vector sum = Vector.add(currentVelocity, randAcceleration);
		return sum;
	}

	private void resetPuck() {
		puck = new Ellipse2D.Double(PUCK_INITIAL_X, PUCK_INITIAL_Y, PUCK_RADIUS, PUCK_RADIUS);
		puckVelocity = new Vector(Math.random() < 0 ? 0.55 * PUCK_MAX_SPEED : 0.55 * -PUCK_MAX_SPEED, 0);
	}

	private void resetPaddles() {
		Dimension paddleDim = new Dimension((int) PADDLE_WIDTH, (int) PADDLE_HEIGHT);
		Vector paddle1Offset = new Vector(PADDLE_1_INITIAL_X, PADDLE_1_INITIAL_Y);
		Vector paddle2Offset = new Vector(PADDLE_2_INITIAL_X, PADDLE_2_INITIAL_Y);

		paddle1 = new Rectangle2D.Double();
		paddle2 = new Rectangle2D.Double();
		paddle1.setFrame(paddle1Offset, paddleDim);
		paddle2.setFrame(paddle2Offset, paddleDim);

		p1Velocity = new Vector();
		p2Velocity = new Vector();
	}

	private KeyListener controls() {
		return new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case 'W':
				case 'w':
					p1Velocity = Vector.add(p1Velocity, new Vector(0, -PADDLE_SPEED));
					break;
				case 'S':
				case 's':
					p1Velocity = Vector.add(p1Velocity, new Vector(0, PADDLE_SPEED));
					break;
				}
			}
		};
	}
}
