package hackNSlay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import enemies.Gnome;

public interface EnemyState{
	public void movementAction(Gnome gnom);
}
