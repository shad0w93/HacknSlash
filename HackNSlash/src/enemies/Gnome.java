package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Gnome extends Enemy{
		
	public EnemyState gnomeState;
	
	public Gnome() {
		this.gnomeState = new HardGnome(this);
	}
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.fill(circle);
	};
	public void update(GameContainer container, int delta, float x, float y) throws SlickException{
		gnomeState.movementAction(this, delta, x, y);
	};
}
