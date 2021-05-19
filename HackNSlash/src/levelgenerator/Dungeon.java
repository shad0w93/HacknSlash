package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import enemies.*;
import player.Player;

public class Dungeon {

	public DungeonState dungeonState;
	public int dungeonWidth;
	public int dungeonHeight;

	// Dungeon Size
	static int maxXPosition = 3;
	static int maxYPosition = 3;

	// Start Position
	public int levelPositionX = 1;
	public int levelPositionY = 1;

	public ArrayList<Rectangle> walls[][][];
	public ArrayList<Rectangle> doors[][][];
	public int checkForDoorTimer;

	public Dungeon(int width, int height) {
		dungeonWidth = width;
		dungeonHeight = height;
		initWalls();
		initDoors();
		dungeonState = new WoodenForest(this);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.green);
		for (int direction = 0; direction < 4; direction++) {
			for (Rectangle rectangle : walls[levelPositionX][levelPositionY][direction]) {
				g.fill(rectangle);
			}
		}

		dungeonState.renderEnemies(this, container, g);
		/*
		 * g.setColor(Color.blue); for (Rectangle rectangle : doors) {
		 * g.fill(rectangle); }
		 */
	}

	public void update(GameContainer container, int delta, Input input, Player player) throws SlickException {
		// Just for testing!!!
		if (container.getInput().isKeyPressed(Input.KEY_L)) {
			dungeonState.newDungeonLevel(this);
		}

		dungeonState.moveEnemies(this, container, delta, input, player);

		checkForDoor(player, delta);
	}

	public void checkForDoor(Player player, int delta) {
		System.out.println(levelPositionY);

		for (int direction = 0; direction < 4; direction++) {
			for (Rectangle door : doors[levelPositionX][levelPositionY][direction]) {
				if (player.getplayerShape().intersects(door)) {
					if (direction < 4) {
						switch (direction) {
						case 0:
							levelPositionY++;
							break;
						case 1:
							levelPositionX++;
							break;
						case 2:
							levelPositionY--;
							break;
						case 3:
							levelPositionX--;
							break;
						}
						setNewPlayerPosition(direction, player);
						direction = 5;

					}
				}
			}
		}

	}
	
	private void setNewPlayerPosition(int direction, Player player) {
		switch(direction) {
		case 0:
			player.setyPos(player.getyPos() + 825);
			break;
		case 1:
			player.setxPos(player.getxPos() - 825);
			break;
		case 2:
			player.setyPos(player.getyPos() - 825);
			break;
		case 3:
			player.setxPos(player.getxPos() + 825);
			break;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void initDoors() {
		doors = new ArrayList[maxXPosition][maxYPosition][4];
		for (int i = 0; i < doors.length; i++) {
			for (int y = 0; y < doors[0].length; y++) {
				for (int direction = 0; direction < 4; direction++) {
					doors[i][y][direction] = new ArrayList<Rectangle>();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void initWalls() {
		walls = new ArrayList[maxXPosition][maxYPosition][4];

		for (int i = 0; i < walls.length; i++) {
			for (int y = 0; y < walls[0].length; y++) {
				for (int direction = 0; direction < 4; direction++) {
					walls[i][y][direction] = new ArrayList<Rectangle>();
				}
			}
		}
	}
}
