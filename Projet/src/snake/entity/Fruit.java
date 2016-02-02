package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

public class Fruit implements Drawable, GameEntity, Overlappable{
	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 16;
	protected Point position;
	protected int value;
	protected String name;

	public Fruit(Canvas defaultCanvas, Point pos, int value, String name) {
		this.spriteManager = new SpriteManagerDefaultImpl("Projet/images/fruits.gif",
				defaultCanvas, RENDERING_SIZE, 1);
		spriteManager.setTypes("strawberry", "blackberry", "perry", "cherry", "toxic");
		this.position = pos;
		this.value = value;
		this.name = name;
	}

	public Point getPosition() {
		return position;
	}

	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}

	public void draw(Graphics g) {
		String spriteType = getName();

		spriteManager.setType(spriteType);
		spriteManager.draw(g, getPosition());

	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}

}
