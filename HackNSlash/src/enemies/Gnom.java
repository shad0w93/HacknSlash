package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import hackNSlay.EnemyState;

public class Gnom extends Enemy{
		
	public EnemyState gnomState;
	
	public Gnom() {
		this.gnomState = new NormalGnom(this);
	}
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.white);
		g.fill(circle);
	};
	public void update(GameContainer container, int delta) throws SlickException{
		gnomState.movementAction(this);
	};
}
