package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;

public class SnakeBody extends Snake{

	public SnakeBody(Canvas defaultCanvas) {
		super(defaultCanvas);
		spriteManager.setTypes("horizontal", "vertical",
				"turn-top-right", "turn-top-left", "turn-down-left", "turn-down-right");
	}

	@Override
	public void draw(Graphics g) {
		String spriteType = "";
		Point tmp = getSpeedVector().getDirection();
		movable = true;

		if (tmp.getX() == 1 || tmp.getX() == -1) {
			if(lastMove == "right" || lastMove == "left"){
				spriteType += "horizontal";
			}else{
				
				switch(lastMove+currentMove){
				case "righttop":
					spriteType += "turn-down-left";
					break;
				case "lefttop": 
					spriteType += "turn-down-right";
					break;
				case "rightdown":
					spriteType += "turn-top-left";
					break;
				case "leftdown":
					spriteType += "turn-top-right";
					break;
				default:
					spriteType += "horizontal";
					break;
				}
			}
			
		} else if (tmp.getY() == 1 || tmp.getY() == -1) {
			if(lastMove == "top" || lastMove == "down"){
				spriteType += "vertical";
			}else{
				
				switch(lastMove+currentMove){
				case "downleft" :
					spriteType += "turn-down-left";
					break;
				case "downright" :
					spriteType += "turn-down-right";
					break;
				case "topleft" :
					spriteType += "turn-top-left";
					break;
				case "topright" :
					spriteType += "turn-top-right";
					break;
				default:
					spriteType += "vertical";
					break;
				}
				
			}
		} else {
			spriteType = "static";
			spriteManager.reset();
			movable = false;
		}

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());

	}

}
