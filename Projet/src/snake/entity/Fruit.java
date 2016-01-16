package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.DrawableImage;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

public class Fruit implements Drawable, GameEntity, Overlappable{
	protected static DrawableImage image = null;
	protected Point position;
	protected int value;
	protected String name;
	public static final int RENDERING_SIZE = 16;

	public Fruit(Canvas defaultCanvas, Point pos, int value, String name) {
		this.image = new DrawableImage("image/fruits.gif", defaultCanvas);
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
		g.drawImage(image.getImage(), (int) getPosition().getX(),
				(int) getPosition().getY(), RENDERING_SIZE, RENDERING_SIZE,
				null);

	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}

}
