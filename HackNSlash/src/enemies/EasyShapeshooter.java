package enemies;

import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;

public class EasyShapeshooter implements EnemyState {
	int shootDirection; // 0 = initial 1 = nach links schießen, 2 = nach rechts schießen
	private int stepValue = 5; // Fortbewegungsgeschwindigkeit
	private Random random;
	int counter;
	int runningTime;
	int shootingUpdate = 1;
	int nextStep;
	int shootTime;
	int moveDirectionXY[];

	public EasyShapeshooter(Shapeshooter shapeshooter) {
		random = new Random();
		shapeshooter.xPos = 150;
		shapeshooter.yPos = 150;
		shapeshooter.circleRadius = 30;
		shapeshooter.circle = new Circle(shapeshooter.xPos, shapeshooter.yPos, shapeshooter.circleRadius);
		shapeshooter.name = "Shapeshooter Mirana";
		runningTime = 0;
		shootTime = 0;
		nextStep = 0;
		moveDirectionXY = new int[2];
		moveDirectionXY = changeDirection();
		createProjectile(shapeshooter);
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
			shapeshooter.xPos = shapeshooter.xPos + moveDirectionXY[0];
			shapeshooter.yPos = shapeshooter.yPos + moveDirectionXY[1];
			shapeshooter.circle.setCenterX(shapeshooter.xPos);
			shapeshooter.circle.setCenterY(shapeshooter.yPos);
		}
		shootTime += delta;

		if (shootTime >= 1000) {
			// Neues Geschoss erstellen
			shootPlayer(shapeshooter, delta, x, y);
			shootTime = 0;
			shootDirection = 0;
			if (x < shapeshooter.shape.getCenterX()) {
				// Player is on the left side of enemy, shoot left
				shootDirection = 1;
			} else {
				// Player is on right side of enemy, shoot right
				shootDirection = 2;
			}
		} else {
			// Bewegungsaktion für bestehenden Schuss
			if (shootDirection == 1) {
				shapeshooter.shape.setCenterX(shapeshooter.shape.getCenterX() - shootingUpdate);
			} else if (shootDirection == 2) {
				shapeshooter.shape.setCenterX(shapeshooter.shape.getCenterX() + shootingUpdate);
			}

		}
	}

	private void shootPlayer(Shapeshooter shapeshooter, int delta, float playerPosX, float playerPosY) {
		// shoot player if in range
		createProjectile(shapeshooter);
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

	private void createProjectile(Shapeshooter shapeshooter) {
		random = new Random();
		int randomNumber = random.nextInt(3);
		
		switch (randomNumber) {
		case 0:
			shapeshooter.shape = new Rectangle(shapeshooter.xPos, shapeshooter.yPos, random.nextInt(20),
					random.nextInt(20));
			break;
		case 1:
			shapeshooter.shape = new Circle(shapeshooter.xPos, shapeshooter.yPos, random.nextInt(20));
			break;
		case 2:
			float[] points = { 7, 45, 10, 33, 11, 42, 15, 30, 17, 36, 25, 0, 33, 37, 37, 31, 38, 41, 41, 34, 42, 45 };
			shapeshooter.shape = new Polygon(points);
			break;
		default:
			break;
		}
		shapeshooter.shape.setCenterX(shapeshooter.xPos);
		shapeshooter.shape.setCenterY(shapeshooter.yPos);
	}

}
