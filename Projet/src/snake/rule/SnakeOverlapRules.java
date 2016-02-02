package snake.rule;

import java.awt.Point;
import java.util.Vector;

import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import snake.entity.Fruit;
import snake.entity.SnakeBody;
import snake.entity.SnakeHead;
import snake.entity.SnakeTail;
import snake.entity.TeleportPairOfPoints;

public class SnakeOverlapRules extends OverlapRulesApplierDefaultImpl{

	protected GameUniverse universe;
	protected Vector<Fruit> vFruits = new Vector<Fruit>();

	protected Point snakeStartPos;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Integer> life;

	public SnakeOverlapRules(Point snakePos, ObservableValue<Integer> score,
			ObservableValue<Integer> life) {
		this.snakeStartPos = (Point) snakePos.clone();
		this.score = score;
		this.life = life;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}


	public void addGhost(Fruit f) {
		vFruits.addElement(f);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(SnakeHead sh, Fruit f) {
		//Condition to know which fruit the snake is eating
		if(f.getName() == "toxic"){
			life.setValue(life.getValue() - 1);
			universe.removeGameEntity(f);
		}else{
			score.setValue(score.getValue() + f.getValue());
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

}
