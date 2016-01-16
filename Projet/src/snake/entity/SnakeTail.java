package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

public class SnakeTail extends Snake{

	public SnakeTail(Canvas defaultCanvas) {
		super(defaultCanvas);
		spriteManager.setTypes("tail-down","tail-left", "tail-top", "tail-right");
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (tmp.getX() == 1) {
			spriteType += "tail-right";
		} else if (tmp.getX() == -1) {
			spriteType += "tail-left";
		} else if (tmp.getY() == 1) {
			spriteType += "tail-down";
		} else if (tmp.getY() == -1) {
			spriteType += "tail-top";
		} else {
			spriteType = "static";
			spriteManager.reset();
			movable = false;
		}
		
		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());
		
	}
}
