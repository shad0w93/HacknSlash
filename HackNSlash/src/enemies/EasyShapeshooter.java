package enemies;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class EasyShapeshooter implements EnemyState {
	int shootDirection = 0; // 0 = initial 1 = nach links schießen, 2 = nach rechts schießen
	private int stepValue = 3; // Fortbewegungsgeschwindigkeit
	private Random random;
	boolean inRange = false;
	int counter;
	int runningTime;
	int shootingUpdate = 1;
	int nextStep;
	int shootTime;
	int attackRange = 150;
	int dungeonSizeX = 1000;
	int dungeonSizeY = 1000;
	int gameBorderXY = 32;
	int moveDirectionXY[];

	public EasyShapeshooter(Shapeshooter shapeshooter) {
		random = new Random();
		shapeshooter.projectiles = new ArrayList<Projectile>();
		shapeshooter.xPos = random.nextInt(dungeonSizeX);
		shapeshooter.yPos = random.nextInt(dungeonSizeY);
		shapeshooter.circleRadius = 16;
		shapeshooter.circle = new Circle(shapeshooter.xPos, shapeshooter.yPos, shapeshooter.circleRadius);
		shapeshooter.name = "Shapeshooter Mirana";
		runningTime = 0;
		shootTime = 0;
		nextStep = 0;
		moveDirectionXY = new int[2];
		moveDirectionXY = changeDirection();
	}

	@Override
	public void movementAction(Gnome gnom, int delta, float x, float y) {
		// TODO Auto-generated method stub
	}

	@Override
	public void movementAction(Shapeshooter shapeshooter, int delta, float x, float y) {
		runningTime += delta;
		nextStep += delta;

		if (runningTime >= 5000) {
			runningTime = 0;
			nextStep = 0;
			moveDirectionXY = changeDirection();
		} else if (nextStep >= 30) {
			nextStep = 0;
			if (shapeshooter.xPos + moveDirectionXY[0] < dungeonSizeX - gameBorderXY
					&& shapeshooter.xPos + moveDirectionXY[0] > gameBorderXY) {
				shapeshooter.xPos = shapeshooter.xPos + moveDirectionXY[0];
				shapeshooter.circle.setCenterX(shapeshooter.xPos);
			}
			if (shapeshooter.yPos + moveDirectionXY[1] < dungeonSizeY - gameBorderXY
					&& shapeshooter.yPos + moveDirectionXY[1] > gameBorderXY) {
				shapeshooter.yPos = shapeshooter.yPos + moveDirectionXY[1];
				shapeshooter.circle.setCenterY(shapeshooter.yPos);
			}
		}

		shootTime += delta;

		if (shootTime >= 2000) {
			// Neues Geschoss erstellen
			shootTime = 0;
			shootPlayer(shapeshooter, delta, x, y);
		} else {
			// Bewegungsaktion für bestehenden Schuss
			for (Projectile currentProjectile : shapeshooter.projectiles) {
				currentProjectile.updateProjectile();
			}
		}
		// Projektile außerhalb des Spielbereichs löschen
		Projectile.deleteProjectiles(shapeshooter.projectiles, dungeonSizeX);
	}

	private int[] changeDirection() {
		int xY[] = new int[2];
		int direction = random.nextInt(4);
		if (direction == 0) {
			xY[0] = 0;
			xY[1] = stepValue;
		} else if (direction == 1) {
			xY[0] = stepValue;
			xY[1] = 0;
		} else if (direction == 2) {
			xY[0] = 0;
			xY[1] = 0 - stepValue;
		} else if (direction == 3) {
			xY[0] = 0 - stepValue;
			xY[1] = 0;
		}
		return xY;
	}

	private void shootPlayer(Shapeshooter shapeshooter, int delta, float playerPosX, float playerPosY) {
		if (playerPosX < shapeshooter.circle.getCenterX()) {
			if (playerPosX <= shapeshooter.circle.getCenterX() + attackRange) {
				// createProjectile(shapeshooter);
				// Gegner ist links vom Spieler und in Reichweite
				shapeshooter.projectile = new Projectile(1, shootingUpdate, shapeshooter.circle.getCenterX(),
						shapeshooter.circle.getCenterY());
			}
		} else if (playerPosX > shapeshooter.circle.getCenterX()) {
			if (playerPosX >= shapeshooter.circle.getCenterX() - attackRange) {
				// Gegner ist rechts vom Spieler und in Reichweite
				shapeshooter.projectile = new Projectile(2, shootingUpdate, shapeshooter.circle.getCenterX(),
						shapeshooter.circle.getCenterY());
			}
		}
		shapeshooter.projectiles.add(shapeshooter.projectile);
	}
}
