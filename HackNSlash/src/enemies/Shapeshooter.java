package enemies;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class Shapeshooter extends Enemy {

	public EnemyState shapeshooterState;
	Projectile projectile;
	ArrayList<Projectile> projectiles;

	public Shapeshooter() {
		projectiles = new ArrayList<Projectile>();
		this.shapeshooterState = new EasyShapeshooter(this);
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.cyan);
		g.fill(circle);

		for (Projectile projectile : projectiles) {
			if (projectile != null) {
				g.setColor(Color.pink);
				g.fill(projectile.getShape());
			}
		}
	}

	public void update(GameContainer container, int delta, float x, float y) throws SlickException {
		shapeshooterState.movementAction(this, delta, x, y);

		if (!projectiles.isEmpty()) {
			for (int i = projectiles.size() - 1; i >= 0; i--) {
				Projectile c = projectiles.get(i);
				if (c.getShape().getCenterX() < 0 || c.getShape().getCenterX() > 1000) {
					projectiles.remove(i);
				}
			}
		}
	}
}