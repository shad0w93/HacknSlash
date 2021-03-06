package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Dungeon {

	public DungeonState dungeonState;
	public int level;
	public int dungeonWidth;
	public int dungeonHeight;

	static int maxXPosition = 3;
	static int maxYPosition = 3;
	public int levelPositionX = 1;
	public int levelPositionY = 1;
	@SuppressWarnings("unchecked")
	public ArrayList<Rectangle> walls[][];
	public ArrayList<Rectangle> doors[][];

	public Dungeon(int width, int height) {
		walls = new ArrayList[maxXPosition][maxYPosition];
		doors = new ArrayList[maxXPosition][maxYPosition];
		dungeonWidth = width;
		dungeonHeight = height;
		for (int i = 0; i < walls.length; i++) {
			for (int y = 0; y < walls[0].length; y++) {
				walls[i][y] = new ArrayList<Rectangle>();
			}
		}
		for (int i = 0; i < walls.length; i++) {
			for (int y = 0; y < walls[0].length; y++) {
				doors[i][y] = new ArrayList<Rectangle>();
			}
		}
		dungeonState = new WoodenForest(this);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.green);
		for (Rectangle rectangle : walls[levelPositionX][levelPositionY]) {
			g.fill(rectangle);
		}
		/*
		 * g.setColor(Color.blue); for (Rectangle rectangle : doors) {
		 * g.fill(rectangle); }
		 */
	};

	public void update(GameContainer container, int delta, Input input) throws SlickException {
		if (input.isKeyPressed(Input.KEY_UP)) {
			if(levelPositionY < maxYPosition -1) {
				levelPositionY++;
			}
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if(levelPositionY > 0) {
				levelPositionY--;
			}
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			if(levelPositionX < maxYPosition -1) {
				levelPositionX++;
			}
		}
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			if(levelPositionX > 0) {
				levelPositionX--;
			}
		}
	};
}
