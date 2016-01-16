package snake.rule;

import java.awt.Point;
import java.util.Vector;

import gameframework.base.MoveStrategyRandom;
import gameframework.base.MoveStrategyStraightLine;
import gameframework.base.ObservableValue;
import gameframework.base.Overlap;
import gameframework.game.GameMovableDriverDefaultImpl;
import gameframework.game.GameUniverse;
import gameframework.game.OverlapRulesApplierDefaultImpl;
import snake.entity.Fruit;
import snake.entity.Snake;
import snake.entity.TeleportPairOfPoints;

public class SnakeOverlapRules extends OverlapRulesApplierDefaultImpl{

	protected GameUniverse universe;
	protected Vector<Fruit> vFruits = new Vector<Fruit>();

	protected Point snakeStartPos;
	protected boolean manageSnakeDeath;
	private final ObservableValue<Integer> score;
	private final ObservableValue<Boolean> endOfGame;

	public SnakeOverlapRules(Point snakePos, ObservableValue<Integer> score,
			ObservableValue<Boolean> endOfGame) {
		this.snakeStartPos = (Point) snakePos.clone();
		this.score = score;
		this.endOfGame = endOfGame;
	}

	public void setUniverse(GameUniverse universe) {
		this.universe = universe;
	}


	public void addGhost(Fruit f) {
		vFruits.addElement(f);
	}

	@Override
	public void applyOverlapRules(Vector<Overlap> overlappables) {
		manageSnakeDeath = true;
		super.applyOverlapRules(overlappables);
	}

	public void overlapRule(Snake s, Fruit f) {
		if(f.getName() == "toxic"){
			if (manageSnakeDeath) {
				s.setPosition(snakeStartPos);
				manageSnakeDeath = false;
				endOfGame.setValue(true);
			}
		}else{
			score.setValue(score.getValue() + f.getValue());
			universe.removeGameEntity(f);
		}
	}

	public void overlapRule(Snake s1, Snake s2) {
		if (manageSnakeDeath) {
			s1.setPosition(snakeStartPos);
			manageSnakeDeath = false;
			endOfGame.setValue(true);
		}
	}

	public void overlapRule(Snake s, TeleportPairOfPoints teleport) {
		s.setPosition(teleport.getDestination());
	}

}
