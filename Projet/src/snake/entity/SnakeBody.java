package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

import gameframework.base.MoveStrategyStraightLine;
import gameframework.game.GameMovableDriverDefaultImpl;

public class SnakeBody extends Snake{

	protected Snake partAttach;
	GameMovableDriverDefaultImpl snakeDriver;

	public SnakeBody(Canvas defaultCanvas, Snake partAttach) {
		super(defaultCanvas);
		this.partAttach = partAttach;
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		String tmpMove = "left";
		movable = true;

		//Watch where his attach part is going
		switch(this.partAttach.getLastMove()+this.partAttach.getCurrentMove()){
		case "righttop": 
			spriteType = "turn-down-left";
			tmpMove = "right";
			break;
		case "downleft":
			spriteType = "turn-down-left";
			tmpMove = "down";
			break;
		case "lefttop": 
			spriteType = "turn-down-right";
			tmpMove = "left";
			break;
		case "downright":
			spriteType = "turn-down-right";
			tmpMove = "down";
			break;
		case "rightdown": 
			spriteType = "turn-top-left";
			tmpMove = "right";
			break;
		case "topleft":
			spriteType = "turn-top-left";
			tmpMove = "top";
			break;
		case "leftdown": 
			spriteType = "turn-top-right";
			tmpMove = "left";
			break;
		case "topright":
			spriteType = "turn-top-right";
			tmpMove = "top";
			break;
		case "leftleft": 
			spriteType = "horizontal";
			tmpMove = "left";
			break;
		case "rightright":
			spriteType = "horizontal";
			tmpMove = "right";
			break;
		case "toptop": 
			spriteType = "vertical";
			tmpMove = "top";
			break;
		case "downdown":
			spriteType = "vertical";
			tmpMove = "down";
			break;
		default:
			spriteType = "horizontal";
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
