package hackNSlay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import enemies.Gnom;

public interface EnemyState{
	public void movementAction(Gnom gnom);
}
