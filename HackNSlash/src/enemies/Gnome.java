package enemies;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class Gnome extends Enemy{
		
	public EnemyState gnomeState;
	private int delay;
	private float playerPosX;
	private float playerPosY;
	private Random random;
	
	public Gnome() {
		//this.gnomeState = new EasyGnome(this);
		//this.gnomeState = new NormalGnome(this);
		this.gnomeState = new HardGnome(this);
	}
	public void render(GameContainer container, Graphics g) throws SlickException{
		g.setColor(Color.red);
		g.fill(circle);
	};
	
	public void update(GameContainer container, int delta, float x, float y) throws SlickException{
		// Aus Funktion aussteigen, bis "delay" erreicht wird.
		delay += delta;
		if (delay <= 10) {
			return;
		}
		delay = 0;
		
		random = new Random();
		// Position des Spielers wird verfälscht, damit der Spieler nicht genau verfolgt wird
		playerPosX = x + (random.nextInt(20)+5);
		playerPosY = y + (random.nextInt(20)+5);
		gnomeState.update(this, delta, playerPosX, playerPosY);
	}

}
