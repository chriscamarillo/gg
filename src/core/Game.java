package core;

import java.awt.Graphics2D;

public interface Game {
	public void update();
	public void draw(Graphics2D g);
	public int getWidth();
	public int getHeight();
	public boolean done();
}
