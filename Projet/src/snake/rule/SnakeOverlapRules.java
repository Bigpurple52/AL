package snake.rule;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Canvas;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import snake.entity.Fruit;
import snake.entity.Snake;
import snake.entity.SnakeBody;
import snake.entity.SnakeHead;
import snake.entity.SnakeTail;
import snake.entity.TeleportPairOfPoints;

public class SnakeOverlapRules extends OverlapRulesApplierDefaultImpl{

	public static final int SPRITE_SIZE = 16;

	protected GameUniverse universe;
	protected Point snakeStartPos;

	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;
	private final Canvas canvas;
	private final ArrayList<Snake> snake;

	public SnakeOverlapRules(Point snakePos, ObservableValue<Integer> score,
			ObservableValue<Integer> life, Canvas canvas, ArrayList<Snake> snake) {
		this.snakeStartPos = (Point) snakePos.clone();
		this.score = score;
		this.life = life;
		this.canvas = canvas;
		this.snake = snake;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(SnakeHead sh, Fruit f) throws InterruptedException {
		if(f.getName() == "toxic"){
			life.setValue(life.getValue() - 1);
			universe.removeGameEntity(f);
		}else{
			score.setValue(score.getValue() + f.getValue());
			universe.removeGameEntity(f);

			//Getting data which its needed
			Snake tail = snake.get(snake.size()-1);
			Snake body = snake.get(snake.size()-2);

			//Remove tail into the game and in the array
			universe.removeGameEntity(tail);
			snake.remove(tail);

			//Set up driver and strategy
			GameMovableDriverDefaultImpl snakeDriverB = new GameMovableDriverDefaultImpl();
			GameMovableDriverDefaultImpl snakeDriverT = new GameMovableDriverDefaultImpl();

			//Create a new body part
			Snake snakeB = new SnakeBody(canvas, body);
			snakeB.setPosition(newPoint(tail, 1));
			snakeB.setLastMove(tail.getLastMove());
			snakeB.setCurrentMove(tail.getCurrentMove());
			snakeDriverB.setStrategy(new MoveStrategyStraightLine(snakeB.getPosition(), body.getPosition()));
			snakeB.setDriver(snakeDriverB);
			snakeB.oneStepMoveAddedBehavior();

			//Create a new tail
			Snake snakeT = new SnakeTail(canvas, snakeB);
			snakeT.setPosition(newPoint(snakeB, SPRITE_SIZE));
			snakeT.setLastMove(snakeB.getLastMove());
			snakeT.setCurrentMove(snakeB.getCurrentMove());
			snakeDriverT.setStrategy(new MoveStrategyStraightLine(snakeT.getPosition(), snakeB.getPosition()));
			snakeT.setDriver(snakeDriverT);
			snakeT.oneStepMoveAddedBehavior();

			//Add body part and tail into the game and in the array
			universe.addGameEntity(snakeB);
			universe.addGameEntity(snakeT);		
			snake.add(snakeB);
			snake.add(snakeT);
		}
	}

	public void overlapRule(SnakeHead sh, SnakeBody sb) {
		life.setValue(life.getValue() - 1);
	}

	public void overlapRule(SnakeHead sh, SnakeTail st) {
		life.setValue(life.getValue() - 1);
	}

	public void overlapRule(SnakeHead sh, TeleportPairOfPoints teleport) {
		sh.setPosition(teleport.getDestination());
	}

	public void overlapRule(SnakeBody sb, TeleportPairOfPoints teleport) {
		sb.setPosition(teleport.getDestination());
	}

	public void overlapRule(SnakeTail st, TeleportPairOfPoints teleport) {
		st.setPosition(teleport.getDestination());
	}

	public Point newPoint(Snake snake, int delay){
		Point goal = null;
		switch (snake.getCurrentMove()){
		case "top":
			goal = new Point((int)Math.round(snake.getPosition().getX()), (int)Math.round(snake.getPosition().getY()+delay));
			break;
		case "down":
			goal = new Point((int)Math.round(snake.getPosition().getX()), (int)Math.round(snake.getPosition().getY()-delay));
			break;
		case "right":
			goal = new Point((int)Math.round(snake.getPosition().getX()-delay), (int)Math.round(snake.getPosition().getY()));
			break;
		case "left":
			goal = new Point((int)Math.round(snake.getPosition().getX()+delay), (int)Math.round(snake.getPosition().getY()));
			break;
		default:
			goal = null;
			break;
		}
		return goal;
	}
}
