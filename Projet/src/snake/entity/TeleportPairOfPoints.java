package snake.entity;

import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Overlappable;
import gameframework.game.GameEntity;

public class TeleportPairOfPoints implements GameEntity, Overlappable{
	public static final int RENDERING_SIZE = 16;

	protected Point position;
	protected Point destination;

	public TeleportPairOfPoints(Point pos, Point destination) {
		position = pos;
		this.destination = destination;
	}

	public Point getDestination() {
		return destination;
	}

	public Point getPosition() {
		return position;
	}

	public Rectangle getBoundingBox() {
		return (new Rectangle((int) position.getX(), (int) position.getY(),
				RENDERING_SIZE, RENDERING_SIZE));
	}
}
