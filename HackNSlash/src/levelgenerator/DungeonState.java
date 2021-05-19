package levelgenerator;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import player.Player;

public interface DungeonState {
	public void newDungeonLevel(Dungeon dungeon);
	public void spawnEnemies(Dungeon dungeon);
	public void moveEnemies(Dungeon dungeon, GameContainer container, int delta, Input input, Player player) throws SlickException;
	public void renderEnemies(Dungeon dungeon, GameContainer container, Graphics g) throws SlickException;
}
