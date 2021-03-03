package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public interface DungeonState {
	public void generateWalls(Dungeon dungeon);
	public void generateDoors(Dungeon dungeon);
	public void generateMobs(Dungeon dungeon);
	public void drawField(Dungeon dungeon);
}
