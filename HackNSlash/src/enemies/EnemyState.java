package enemies;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface EnemyState{
	public void movementAction(Gnome gnom, int delta, float x, float y);
}
