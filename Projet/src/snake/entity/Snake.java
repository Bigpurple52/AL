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
		this.spriteManager = new SpriteManagerDefaultImpl("Projet/images/snake.gif",
				defaultCanvas, RENDERING_SIZE, 1);
		spriteManager.setTypes("head-right","head-down", "head-left", "head-top", "horizontal", "vertical",
				"turn-top-right", "turn-top-left", "turn-down-left", "turn-down-right", "tail-down","tail-left", "tail-top", "tail-right");
	}

	public String getLastMove() {
		return lastMove;
	}
	
	public void setLastMove(String lastMove) {
		this.lastMove = lastMove;
	}

	public String getCurrentMove() {
		return currentMove;
	}

	public void setCurrentMove(String currentMove) {
		this.currentMove = currentMove;
	}

	@Override
	public Rectangle getBoundingBox() {
		return (new Rectangle(0, 0, RENDERING_SIZE, RENDERING_SIZE));
	}
	
}
