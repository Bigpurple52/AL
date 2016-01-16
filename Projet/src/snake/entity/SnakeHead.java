package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

public class SnakeHead extends Snake{
	
	public SnakeHead(Canvas defaultCanvas) {
		super(defaultCanvas);
		spriteManager.setTypes("head-right","head-down", "head-left", "head-top");
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;
		String tmpMove = "";
		
		if (tmp.getX() == 1) {
			spriteType += "head-right";
			tmpMove = "right";
		} else if (tmp.getX() == -1) {
			spriteType += "head-left";
			tmpMove = "left";
		} else if (tmp.getY() == 1) {
			spriteType += "head-down";
			tmpMove = "down";
		} else if (tmp.getY() == -1) {
			spriteType += "head-top";
			tmpMove = "top";
		} else {
			spriteType = "static";
			spriteManager.reset();
			movable = false;
		}
		
		lastMove = currentMove;
		currentMove = tmpMove;
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
		
	}
}
