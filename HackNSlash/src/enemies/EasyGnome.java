package enemies;

import org.newdawn.slick.geom.Circle;

import java.util.Random;

public class EasyGnome implements EnemyState {
	private int stepValue = 2; // Fortbewegungsgeschwindigkeit
	private Random random;
	int runningTime;
	int nextStep;
	int moveDirectionXY[];
	int dungeonSizeX = 1000;
	int dungeonSizeY = 1000;

	public EasyGnome(Gnome gnome) {
		random = new Random();
		gnome.xPos = random.nextInt(dungeonSizeX);
		gnome.yPos = random.nextInt(dungeonSizeY);
		gnome.circleRadius = 16;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Bob";
		moveDirectionXY = new int[2];
		moveDirectionXY = changeDirection();
	}

	@Override
	public void update(Enemy enemy, int time, float x, float y) {
		runningTime += time;
		nextStep += time;

		if (runningTime >= 1000) {
			runningTime = 0;
			nextStep = 0;
			moveDirectionXY = changeDirection();
		} else if (nextStep >= 10) {
			nextStep = 0;
			if (enemy.xPos + moveDirectionXY[0] < dungeonSizeX && enemy.xPos + moveDirectionXY[0] > 0) {
				enemy.circle.setCenterX(enemy.xPos);
				enemy.xPos = enemy.xPos + moveDirectionXY[0];
			} else if (enemy.yPos + moveDirectionXY[1] < dungeonSizeY && enemy.yPos + moveDirectionXY[1] > 0) {
				enemy.yPos = enemy.yPos + moveDirectionXY[1];
				enemy.circle.setCenterY(enemy.yPos);
			} else {
				runningTime = 1000;
			}
		}
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
}
