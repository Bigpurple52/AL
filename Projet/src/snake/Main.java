package snake;

import java.util.ArrayList;

import gameframework.game.GameLevel;

public class Main {
	public static void main(String[] args) {
		GameDefaultImplSnake g = new GameDefaultImplSnake();
		ArrayList<GameLevel> levels = new ArrayList<GameLevel>();

		levels.add(new snake.Level(g));
		
		g.setLevels(levels);
		g.start();
	}
}
