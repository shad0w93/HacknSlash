package enemies;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import player.Player;

public abstract class Enemy extends Being {
	Circle circle;
	float circleRadius;
	public abstract void update(GameContainer container, int delta, Player player)throws SlickException;
	public abstract void render(GameContainer container, Graphics g)throws SlickException;
	public abstract void inflictDamage(int dmgAmount);
}
