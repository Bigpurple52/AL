package snake;

import java.awt.Canvas;
import java.awt.Point;

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
import snake.entity.Snake;
import snake.entity.SnakeBody;
import snake.entity.SnakeHead;
import snake.entity.SnakeTail;
import snake.entity.TeleportPairOfPoints;
import snake.rule.SnakeOverlapRules;

public class GameLevel extends GameLevelDefaultImpl{

	Canvas canvas;

	static int [][] tab;

	public static final int SPRITE_SIZE = 32;

	public GameLevel(Game g) {
		super(g);
		canvas = g.getCanvas();
	}

	@Override
	protected void init() {
		//Init our map design
		for(int line[] : tab){
			for(int element : line){
				element = 0;
			}
		}

		OverlapProcessor overlapProcessor = new OverlapProcessorDefaultImpl();
		MoveBlockerChecker moveBlockerChecker = new MoveBlockerCheckerDefaultImpl();

		SnakeOverlapRules overlapRules = new SnakeOverlapRules(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE),
				score[0], endOfGame);
		overlapProcessor.setOverlapRules(overlapRules);

		universe = new GameUniverseDefaultImpl(moveBlockerChecker, overlapProcessor);
		overlapRules.setUniverse(universe);

		gameBoard = new GameUniverseViewPortDefaultImpl(canvas, universe);
		((CanvasDefaultImpl) canvas).setDrawingGameBoard(gameBoard);

		// Teleports points definition and inclusion in the universe
		for (int i = 0; i <= 30; i++) {
			for (int j = 0; j <= 27; j++) {
				//Maybe have a watch of corner tp
				universe.addGameEntity(new TeleportPairOfPoints(new Point(i * SPRITE_SIZE, j * SPRITE_SIZE), new Point(
						i * SPRITE_SIZE, j * SPRITE_SIZE)));
			}
		}

		// Snake definition and inclusion in the universe
		Snake snakeH = new SnakeHead(canvas);
		Snake snakeB = new SnakeBody(canvas);
		Snake snakeT = new SnakeTail(canvas);
		GameMovableDriverDefaultImpl snakeDriver = new GameMovableDriverDefaultImpl();
		MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
		snakeDriver.setStrategy(keyStr);
		canvas.addKeyListener(keyStr);
		snakeH.setDriver(snakeDriver);
		snakeH.setPosition(new Point(14 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(snakeH);
		snakeB.setPosition(new Point(15 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(snakeB);
		snakeT.setPosition(new Point(16 * SPRITE_SIZE, 17 * SPRITE_SIZE));
		universe.addGameEntity(snakeT);

	}
}
