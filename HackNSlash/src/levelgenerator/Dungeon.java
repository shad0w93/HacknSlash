package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Dungeon {

	public DungeonState dungeonState;
	public int level;
	public int dungeonWidth;
	public int dungeonHeight;
	
	public int levelPosition[][] = new int[3][3];
	public ArrayList<Rectangle> walls;
	public ArrayList<Rectangle> doors;
	//[Reihe][spalte][0 = Oben, 1 = Rechts, 2 = Unten, 3 = Links];
	public int levelRoomDoors[][][] = new int[3][3][4];
	
	public Dungeon(int width, int height) {
		dungeonWidth = width;
		dungeonHeight = height;
		walls = new ArrayList<Rectangle>();
		doors = new ArrayList<Rectangle>();
		dungeonState = new WoodenForest(this);
	}
	
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.green);
		for (Rectangle rectangle : walls) {
			g.fill(rectangle);
		}
	};
	
	public void update(GameContainer container, int delta) throws SlickException{
	};
}
