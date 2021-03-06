package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public interface DungeonState {
	public void generateWalls(Dungeon dungeon, int x, int y);
	public void generateDoors(Dungeon dungeon);
	public void setRoomDoors(Dungeon dungeon, int roomX, int roomY);
	public void generateMobs(Dungeon dungeon);
	public void drawField(Dungeon dungeon);
}
