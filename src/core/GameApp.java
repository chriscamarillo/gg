package core;
import games.Pong;

public abstract class GameApp {
	public static void main(String[] args) {
		new Pong().start();
	}
}
