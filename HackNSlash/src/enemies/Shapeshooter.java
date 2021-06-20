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
	private int dmgAmount = 2;
	int shootTime;
	int attackRange = 500;
	int shootingUpdate = 1;
	private int dmgCooldownInMs;
	int gameBorderXY = 50;

	public Shapeshooter() {
		projectiles = new ArrayList<Projectile>();
		circleRadius = 16; // Größe des Gegners

		this.shapeshooterState = new EasyShapeshooter(this);
	}

	@Override
	public void update(GameContainer container, int delta, Player player) throws SlickException {
		shapeshooterState.update(this, delta, player);
		shootingAction(delta, player, container.getWidth() - (container.getWidth() / 3));
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
	public void inflictPlayerDamage(int dmgAmount, Player player) {
		player.setLp(player.getLp()-dmgAmount);
	}

	public void shootingAction(int delta, Player player, int dungeonSizeX) {
		shootTime += delta;

		if (shootTime >= 2000) {
			// Neues Geschoss erstellen
			shootTime = 0;
			shootPlayer(delta, player.getxPos(), player.getyPos());
		} else {
			// Bewegungsaktion für bestehenden Schuss
			for (int i = projectiles.size() - 1; i >= 0; i--) {
				Projectile c = projectiles.get(i);
				c.updateProjectile();
				if (player.getplayerShape().intersects(c.getShape())) {
					inflictPlayerDamage(dmgAmount, player);
					projectiles.remove(c);
				}
			}
			// Projektile außerhalb des Spielbereichs löschen
			Projectile.deleteProjectiles(projectiles, dungeonSizeX);
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