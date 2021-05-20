package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import player.Player;

import java.util.ArrayList;

public class Shapeshooter extends Enemy {

	public EnemyState shapeshooterState;
	Projectile projectile;
	ArrayList<Projectile> projectiles;

	int shootTime;
	int attackRange = 500;
	int shootingUpdate = 1;
	int gameBorderXY = 50;

	public Shapeshooter() {
		projectiles = new ArrayList<Projectile>();
		circleRadius = 16; // Größe des Gegners

		this.shapeshooterState = new EasyShapeshooter(this);
	}

	@Override
	public void update(GameContainer container, int delta, Player player) throws SlickException {
		shapeshooterState.update(this, delta, player);
		shootingAction(delta, player.getxPos(), player.getyPos(), container.getWidth() - (container.getWidth() / 3));
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

	@Override
	public void inflictDamage(int dmgAmount) {

	}

	public void shootingAction(int delta, float x, float y,int dungeonSizeX) {
		shootTime += delta;

		if (shootTime >= 2000) {
			// Neues Geschoss erstellen
			shootTime = 0;
			shootPlayer(delta, x, y);
		} else {
			// Bewegungsaktion für bestehenden Schuss
			for (Projectile currentProjectile : projectiles) {
				currentProjectile.updateProjectile();
			}
			// Projektile außerhalb des Spielbereichs löschen
			Projectile.deleteProjectiles(projectiles, dungeonSizeX);
		}
	}

	private void shootPlayer(int delta, float playerPosX, float playerPosY) {
		if (playerPosX < circle.getCenterX()) {
			if (playerPosX + attackRange >= circle.getCenterX()) {
				// Gegner ist links vom Spieler und in Reichweite
				projectile = new Projectile(1, shootingUpdate, circle.getCenterX(),
						circle.getCenterY());
				projectiles.add(projectile);
			}
		} else if (playerPosX > circle.getCenterX()) {
			if (playerPosX - attackRange <= circle.getCenterX()) {
				// Gegner ist rechts vom Spieler und in Reichweite
				projectile = new Projectile(2, shootingUpdate, circle.getCenterX(),
						circle.getCenterY());
				projectiles.add(projectile);
			}
		}
	}
}