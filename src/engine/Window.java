package engine;

import java.awt.Canvas;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends Canvas {
	private int mouseX;
	private int mouseY;

	public Window(int width, int height) {
		super();
		setSize(width, height);
		addMouseListener(updateMouseCoords());
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	private MouseAdapter updateMouseCoords() {
		return new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			public void mouseExited(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
			
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		};
	}
}
