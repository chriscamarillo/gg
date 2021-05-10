package core;

import engine.DualThreadEngine;
import games.Hockey;

public class GamePlayer {
	public static void main(String[] args) {
		DualThreadEngine engine = new DualThreadEngine();
		Hockey game = new Hockey(1280, 720);
		engine.load(game);
		engine.start();
	}
}
