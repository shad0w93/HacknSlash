package player;

import enemies.Enemy;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

public interface PlayerClass {
	public void update(GameContainer container, int delta, Input input, Player player);
	public void render(GameContainer container, Graphics g, Player player);
	public void attack(Input input, Player player);
	public void inflictEnemyDamage(int dmgAmount, Enemy enemy);
}
