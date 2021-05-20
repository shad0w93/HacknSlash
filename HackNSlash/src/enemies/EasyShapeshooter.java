package enemies;

import org.newdawn.slick.geom.Circle;
import player.Player;

import java.util.Random;

public class EasyShapeshooter implements EnemyState {
	private final Random random;
	int runningTime;
	int nextStep;
	int shootTime;
	int dungeonSizeX = 1000;
	int dungeonSizeY = 1000;
	int gameBorderXY;
	private int[] moveDirectionXY;

	public EasyShapeshooter(Shapeshooter shapeshooter) {
		random = new Random();
		shapeshooter.xPos = random.nextInt(dungeonSizeX);
		shapeshooter.yPos = random.nextInt(dungeonSizeY);
		shapeshooter.circle = new Circle(shapeshooter.xPos, shapeshooter.yPos, shapeshooter.circleRadius);
		shapeshooter.name = "Shapeshooter Mirana";
		gameBorderXY = shapeshooter.gameBorderXY;
		runningTime = 0;
		shootTime = 0;
		nextStep = 0;
		moveDirectionXY = new int[2];
		moveDirectionXY = changeDirection();
	}

	private int[] changeDirection() {
		int[] xY = new int[2];
		int direction = random.nextInt(4);
		int stepValue = 2; // Fortbewegungsgeschwindigkeit
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

	@Override
	public void update(Enemy enemy, int time, Player player) {
		runningTime += time;
		nextStep += time;

		if (runningTime >= 5000) {
			runningTime = 0;
			nextStep = 0;
			moveDirectionXY = changeDirection();
		} else if (nextStep >= 30) {
			nextStep = 0;
			if (enemy.xPos + moveDirectionXY[0] < dungeonSizeX - gameBorderXY
					&& enemy.xPos + moveDirectionXY[0] > gameBorderXY) {
				enemy.xPos = enemy.xPos + moveDirectionXY[0];
				enemy.circle.setCenterX(enemy.xPos);
			}
			if (enemy.yPos + moveDirectionXY[1] < dungeonSizeY - gameBorderXY
					&& enemy.yPos + moveDirectionXY[1] > gameBorderXY) {
				enemy.yPos = enemy.yPos + moveDirectionXY[1];
				enemy.circle.setCenterY(enemy.yPos);
			}
		}
	}
}
