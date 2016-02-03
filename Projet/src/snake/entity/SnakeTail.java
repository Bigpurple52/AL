package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.game.GameMovableDriverDefaultImpl;

public class SnakeTail extends Snake{

	protected Snake partAttach;
	GameMovableDriverDefaultImpl snakeDriver;

	public SnakeTail(Canvas defaultCanvas, Snake partAttach) {
		super(defaultCanvas);
		this.partAttach = partAttach;
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "tail-";
		String tmpMove = "left";
		movable = true;

		//Watch where his attach part is going
		switch(this.partAttach.getLastMove()){
		case "right": 
			spriteType += "right";
			tmpMove = "right";
			break;
		case "down":
			spriteType += "down";
			tmpMove = "down";
			break;
		case "left": 
			spriteType += "left";
			tmpMove = "left";
			break;
		case "top":
			spriteType += "top";
			tmpMove = "top";
			break;
		default:
			spriteType += "left";
			tmpMove = "";
			movable = false;
		}

		lastMove = currentMove;
		currentMove = tmpMove;
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		//Watch if the part is movable
		if(this.partAttach.movable){
			Point goal = null;
			//Watch where his attach part is going
			switch (this.partAttach.getCurrentMove()){
			case "top":
				goal = new Point((int)Math.round(this.getPosition().getX()), (int)Math.round(this.getPosition().getY())-1);
				break;
			case "down":
				goal = new Point((int)Math.round(this.getPosition().getX()), (int)Math.round(this.getPosition().getY())+1);
				break;
			case "right":
				goal = new Point((int)Math.round(this.getPosition().getX())+1, (int)Math.round(this.getPosition().getY()));
				break;
			case "left":
				goal = new Point((int)Math.round(this.getPosition().getX())-1, (int)Math.round(this.getPosition().getY()));
				break;
			default:
				goal = this.getPosition();
				break;
			}

			//Allows to move to his current position to his attach part
			snakeDriver = new GameMovableDriverDefaultImpl();
			snakeDriver.setStrategy(new MoveStrategyStraightLine(this.getPosition(), goal));
			this.setDriver(snakeDriver);
		}
	}
}
