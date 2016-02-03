package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

public class SnakeHead extends Snake{

	public SnakeHead(Canvas defaultCanvas) {
		super(defaultCanvas);
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "head-";
		Point tmp = getSpeedVector().getDirection();
		movable = true;
		String tmpMove = "";	

		//Watch which direction the head is going
		if (tmp.getX() == 1) {
			spriteType += "right";
			tmpMove = "right";
		} else if (tmp.getX() == -1) {
			spriteType += "left";
			tmpMove = "left";
		} else if (tmp.getY() == 1) {
			spriteType += "down";
			tmpMove = "down";
		} else if (tmp.getY() == -1) {
			spriteType += "top";
			tmpMove = "top";
		} else {
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
		//Nothing happened
	}
}
