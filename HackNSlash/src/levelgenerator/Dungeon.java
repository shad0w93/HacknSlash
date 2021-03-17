package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import player.Player;

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
	public ArrayList<Rectangle> walls[][][];
	public ArrayList<Rectangle> doors[][][];

	public Dungeon(int width, int height) {
		walls = new ArrayList[maxXPosition][maxYPosition][4];
		doors = new ArrayList[maxXPosition][maxYPosition][4];
		dungeonWidth = width;
		dungeonHeight = height;
		for (int i = 0; i < walls.length; i++) {
			for (int y = 0; y < walls[0].length; y++) {
				for (int direction = 0; direction < 4; direction++) {
					walls[i][y][direction] = new ArrayList<Rectangle>();
				}
			}
		}
		for (int i = 0; i < walls.length; i++) {
			for (int y = 0; y < walls[0].length; y++) {
				for (int direction = 0; direction < 4; direction++) {
					doors[i][y][direction] = new ArrayList<Rectangle>();
				}
			}
		}
		dungeonState = new WoodenForest(this);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.green);
		for (int direction = 0; direction < 4; direction++) {
			for (Rectangle rectangle : walls[levelPositionX][levelPositionY][direction]) {
				g.fill(rectangle);
			}
		}
		/*
		 * g.setColor(Color.blue); for (Rectangle rectangle : doors) {
		 * g.fill(rectangle); }
		 */
	}

	public void update(GameContainer container, int delta, Input input, Shape character) throws SlickException {
		checkForDoor(character);
		if (container.getInput().isKeyPressed(Input.KEY_L)) {
			dungeonState.newDungeonLevel(this);
		}
	}

	public void checkForDoor(Shape character) {

	}


}
