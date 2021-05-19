package enemies;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public abstract class Enemy extends Being {
	Circle circle;
	float circleRadius;
	public abstract void update(GameContainer container, int delta, float x, float y)throws SlickException;
	public abstract void render(GameContainer container, Graphics g)throws SlickException;
}
