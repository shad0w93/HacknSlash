package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

public interface DungeonState {
	public void newDungeonLevel(Dungeon dungeon);
	public void spawnMobs(Dungeon dungeon);
}
