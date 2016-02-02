package snake;

import java.awt.Canvas;
import java.awt.Point;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gameframework.base.ObservableValue;
import gameframework.game.Game;
import gameframework.game.GameLevel;
import gameframework.game.GameUniverse;
import gameframework.game.GameUniverseViewPort;
import snake.entity.Fruit;

public abstract class GameLevelDefaultImplSnake extends Thread implements GameLevel {
	private static final int MINIMUM_DELAY_BETWEEN_GAME_CYCLES = 100;
	public static final int SPRITE_SIZE = 16;
	
	private static final String fruits[] = {"strawberry", "blackberry", "perry", "cherry", "toxic"};
	private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	protected final Game g;
	protected GameUniverse universe;
	protected GameUniverseViewPort gameBoard;
	protected Canvas canvas;

	protected ObservableValue<Integer> score[];
	protected ObservableValue<Integer> life[];
	protected ObservableValue<Boolean> endOfGame;

	boolean stopGameLoop;

	protected abstract void init();

	public GameLevelDefaultImplSnake(Game g) {
		this.g = g;
		this.score = g.score();
		this.life = g.life();
		this.canvas = g.getCanvas();
	}

	// start of class Thread which calls the run method (see below) 
	@Override
	public void start() {  
		endOfGame = g.endOfGame();
		init();
		super.start();
		try {
			super.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		stopGameLoop = false;
		
		// main game loop 
		long start;
		final Runnable pop = new Runnable() { 
		        @Override
		        public void run() {
		        	int x = (int)((Math.random()*25)+1);
				    int y = (int)((Math.random()*28)+1);
				    int f = (int)(Math.random()*5);
			    	Fruit fruit = new Fruit (canvas, new Point(x * SPRITE_SIZE, y * SPRITE_SIZE), 1, fruits[f]);
			    	universe.addGameEntity(fruit);
			    	canvas.repaint();  
			    	Timer t = new Timer();
					t.schedule(new TimerTask() {
					    @Override
					    public void run() {
					    	universe.removeGameEntity(fruit);
					    	canvas.repaint(); 
					    }
					}, 5000, 5000);
		    }
		};
		
		
		    executor.scheduleWithFixedDelay(pop, 2, 5, TimeUnit.SECONDS);
		    
		while (!stopGameLoop && !this.isInterrupted()) {
			start = new Date().getTime();
			gameBoard.paint();
			universe.allOneStepMoves();
			universe.processAllOverlaps();
			try {
				long sleepTime = MINIMUM_DELAY_BETWEEN_GAME_CYCLES
						- (new Date().getTime() - start);
				if (sleepTime > 0) {
					Thread.sleep(sleepTime);
				}
			} catch (Exception e) {
			}
		}
	}

	public void end() {
		stopGameLoop = true;
		executor.shutdownNow();
	}

	protected void overlap_handler() {
	}

}