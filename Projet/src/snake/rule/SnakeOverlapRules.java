package snake.rule;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.Canvas;

import gameframework.base.MoveStrategyKeyboard;
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
		//Condition to know which fruit the snake is eating
		Point goal;
		if(f.getName() == "toxic"){
			life.setValue(life.getValue() - 1);
			universe.removeGameEntity(f);
		}else{
			score.setValue(score.getValue() + f.getValue());
			//Fruit fruit3 = new Fruit (canvas, new Point(6 * SPRITE_SIZE, 14 * SPRITE_SIZE), 1, "toxic");
			//universe.addGameEntity(fruit3);
			//universe.removeGameEntity((Snake)snake.get(2));
			goal = snake.get(2).getPosition();
			universe.removeGameEntity(snake.get(2));
			snake.remove(2);
			GameMovableDriverDefaultImpl snakeDriver = new GameMovableDriverDefaultImpl();
			MoveStrategyKeyboard keyStr = new MoveStrategyKeyboard();
			snakeDriver.setStrategy(keyStr);
			
			Snake snakeB = new SnakeBody(canvas, snake.get(1));
			snakeB.setDriver(snakeDriver);
			snakeB.setPosition(new Point((int)goal.getX(),(int)goal.getY()+10));
			snakeB.setLastMove(snake.get(1).getLastMove());
			snakeB.setCurrentMove(snake.get(1).getCurrentMove());
			snake.add(snakeB);
			universe.addGameEntity(snakeB);
			/*Snake snakeT = new SnakeTail(canvas, snake.get(2));
			snakeT.setDriver(snakeDriver);
			//snakeT.setPosition(new Point((int)snake.get(1).getPosition().getX()-1,(int)snake.get(1).getPosition().getY()));
			snakeT.setPosition(newPoint(snake.get(2)));
			//snakeT.setSpeedVector(snake.get(2).getSpeedVector());
			universe.addGameEntity(snakeT);
			snake.add(snakeT);*/
			universe.removeGameEntity(f);
		}
	}

	public void overlapRule(SnakeHead sh, SnakeBody sb) {
		life.setValue(life.getValue() - 1);
	}

	public void overlapRule(SnakeHead sh, SnakeTail sb) {
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
	
	public Point newPoint(Snake snake){
		Point goal;
		switch (snake.getLastMove()){
		case "top":
			goal = new Point((int)snake.getPosition().getX()+2, (int)snake.getPosition().getY());
			break;
		case "down":
			goal = new Point((int)snake.getPosition().getX()-2, (int)snake.getPosition().getY());
			break;
		case "right":
			goal = new Point((int)snake.getPosition().getX(), (int)snake.getPosition().getY()-2);
			break;
		case "left":
			goal = new Point((int)snake.getPosition().getX(), (int)snake.getPosition().getY()+2);
			break;
		default:
			goal = null;
			break;
		}
		return goal;
	}

}
