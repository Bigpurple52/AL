package snake;

import java.awt.AWTException;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import gameframework.base.MoveStrategyKeyboard;
import gameframework.game.CanvasDefaultImpl;
import gameframework.game.Game;
import gameframework.game.GameLevelDefaultImpl;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverseDefaultImpl;
import gameframework.game.GameUniverseViewPortDefaultImpl;
import gameframework.game.MoveBlockerChecker;
import gameframework.game.MoveBlockerCheckerDefaultImpl;
import gameframework.game.OverlapProcessor;
import gameframework.game.OverlapProcessorDefaultImpl;
import snake.entity.Fruit;
import snake.entity.Snake;
import snake.entity.SnakeBody;
import snake.entity.SnakeHead;
import snake.entity.SnakeTail;
import snake.entity.TeleportPairOfPoints;
import snake.rule.SnakeOverlapRules;

public class Level extends GameLevelDefaultImpl{

	Canvas canvas;

	static int [][] tab;

	public static final int SPRITE_SIZE = 16;

	public Level(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

	@Override
	protected void init() {

		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();

		SnakeOverlapRules overlapRules = new SnakeOverlapRules(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE),
				score[0], life[0]);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);		

		// Teleport points definition and inclusion in the universe
		for (int i = 0; i < 28; i++) {
			for (int j = 0; j < 31; j++) {
				// Only tp in the border side
				// Left side to right side
				if(i == 0 ){
					universe.addGameEntity(new TeleportPairOfPoints(new Point(i * SPRITE_SIZE, j * SPRITE_SIZE), new Point(
							26 * SPRITE_SIZE, j * SPRITE_SIZE)));					
				// Right side to left side
				}else if (i == 27){
					universe.addGameEntity(new TeleportPairOfPoints(new Point(i * SPRITE_SIZE, j * SPRITE_SIZE), new Point(
						1 * SPRITE_SIZE, j * SPRITE_SIZE)));
				// Top side to bottom side
				}else if(j == 0){
					universe.addGameEntity(new TeleportPairOfPoints(new Point(i * SPRITE_SIZE, j * SPRITE_SIZE), new Point(
							i * SPRITE_SIZE, 29 * SPRITE_SIZE)));
				// Bottom side to top side
				}else if(j == 30){
					universe.addGameEntity(new TeleportPairOfPoints(new Point(i * SPRITE_SIZE, j * SPRITE_SIZE), new Point(
							i * SPRITE_SIZE, 1 * SPRITE_SIZE)));
				}
			}
		}
		
		Fruit fruit = new Fruit (canvas, new Point(14 * SPRITE_SIZE, 25 * SPRITE_SIZE), 1, "strawberry");
		universe.addGameEntity(fruit);
		Fruit fruit1 = new Fruit (canvas, new Point(10 * SPRITE_SIZE, 25 * SPRITE_SIZE), 1, "toxic");
		universe.addGameEntity(fruit1);

		// Snake definition and inclusion in the universe
		Snake snakeH = new SnakeHead(canvas);
		Snake snakeB = new SnakeBody(canvas, snakeH);
		Snake snakeT = new SnakeTail(canvas, snakeB);
		GameMovableDriverDefaultImpl snakeDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
		snakeDriver.setStrategy(keyStr);
		canvas.addKeyListener(keyStr);
		snakeH.setDriver(snakeDriver);
		snakeH.setPosition(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(snakeH);
		snakeB.setDriver(snakeDriver);
		snakeB.setPosition(new Point(15 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(snakeB);
		snakeT.setDriver(snakeDriver);
		snakeT.setPosition(new Point(16 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(snakeT);
		
		// Perform a up key press to move the snake automatically at the beginning
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_LEFT);
			robot.keyRelease(KeyEvent.VK_LEFT);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
	
	
	
	
	
	
	
