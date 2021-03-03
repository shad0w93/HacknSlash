package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import hackNSlay.EnemyState;

public class Gnome extends Enemy{
		
	public EnemyState gnomeState;
	
	public Gnome() {
		this.gnomeState = new NormalGnome(this);
	}
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.fill(circle);
	};
	public void update(GameContainer container, int delta) throws SlickException{
		gnomeState.movementAction(this);
	};
}
