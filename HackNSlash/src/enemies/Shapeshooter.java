package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Shapeshooter extends Enemy{
		
	public EnemyState shapeshooterState;
	
	public Shapeshooter() {
		this.shapeshooterState = new EasyShapeshooter(this);
	}
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.cyan);
		g.fill(circle);
		
		g.setColor(Color.pink);
		g.fill(shape);
	};
	public void update(GameContainer container, int delta, float x, float y) throws SlickException{
		shapeshooterState.movementAction(this, delta, x, y);
	};
}