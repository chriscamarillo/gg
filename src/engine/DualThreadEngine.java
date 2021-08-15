package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DualThreadEngine {
	private Runnable renderLoop;
	private Runnable updateLoop;
	private ScheduledExecutorService executor;

	public void load(App app) {
		renderLoop = new Runnable() {
			public void run() {
				BufferStrategy bs = app.gameWindow.getBufferStrategy();
				if (bs == null) {
					app.gameWindow.createBufferStrategy(3);
					return;
				}
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				app.draw(g);
				g.dispose();
				bs.show();
			}
		};

		updateLoop = new Runnable() {
			public void run() {
				app.update();
			}
		};
	}

	public void start() {
		executor = Executors.newScheduledThreadPool(2);
		executor.scheduleWithFixedDelay(renderLoop, 0, (int) 1E9 / Constants.RENDER_RATE_CAP, TimeUnit.NANOSECONDS);
		executor.scheduleWithFixedDelay(updateLoop, 0, (int) 1E9 / Constants.UPDATE_TICK_RATE, TimeUnit.NANOSECONDS);
	}

	public void stop() {
		executor.shutdown();
	}
}
