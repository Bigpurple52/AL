package snake.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import gameframework.base.Drawable;
import gameframework.base.Overlappable;
import gameframework.game.GameEntity;
import gameframework.game.GameMovable;
import gameframework.game.SpriteManager;
import gameframework.game.SpriteManagerDefaultImpl;

public abstract class Snake extends GameMovable implements Drawable, GameEntity, Overlappable{

	protected final SpriteManager spriteManager;
	public static final int RENDERING_SIZE = 16;
	protected boolean movable = true;
	protected String lastMove = "";
	protected String currentMove = "";

	public Snake(Canvas defaultCanvas) {
		this.spriteManager = new SpriteManagerDefaultImpl("image/snake.gif",
				defaultCanvas, RENDERING_SIZE, 6);
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}

	@Override
	public void oneStepMoveAddedBehavior() {
		if (movable) {
			spriteManager.increment();
		}
	}
}
