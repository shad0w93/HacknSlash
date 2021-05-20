package enemies;

import org.newdawn.slick.geom.Circle;
import player.Player;

import java.util.Random;

public class HardGnome implements EnemyState {
	private Random random;
	private boolean direction = false; // false = nach X laufen; true = nach Y laufen;
	private float playerPosX;
	private float playerPosY;
	private int stepValue = 1;	// Fortbewegungsgeschwindigkeit
	private int movementMargin = 500; // Ändert den Abstand zwischen Player & Gnome X und Y zur Positionsveränderung
	int dungeonSizeX = 1000;
	int dungeonSizeY = 1000;

	public HardGnome(Gnome gnome) {
		random = new Random();
		gnome.xPos = random.nextInt(dungeonSizeX);
		gnome.yPos = random.nextInt(dungeonSizeY);
		gnome.circleRadius = 16;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Gizmo";
	}

	public void moveLeftRight(Enemy enemy) {
		if (playerPosX > enemy.xPos) {
			enemy.xPos = enemy.xPos + stepValue;
			enemy.circle.setCenterX(enemy.xPos);
		} else if (playerPosX < enemy.xPos) {
			enemy.xPos = enemy.xPos - stepValue;
			enemy.circle.setCenterX(enemy.xPos);
		}
	}

	public void moveUpDown(Enemy enemy) {
		if (playerPosY > enemy.yPos) {
			enemy.yPos = enemy.yPos + stepValue;
			enemy.circle.setCenterY(enemy.yPos);
		} else if (playerPosY < enemy.yPos) {
			enemy.yPos = enemy.yPos - stepValue;
			enemy.circle.setCenterY(enemy.yPos);
		}
	}

	@Override
	public void update(Enemy enemy, int time, Player player) {
		playerPosX = player.getxPos();
		playerPosY = player.getyPos();
		if (!direction) {
			if (enemy.xPos > (playerPosX - movementMargin) && enemy.xPos < (playerPosX + movementMargin)) {
				direction = true;
				moveUpDown(enemy);
			} else {
				moveLeftRight(enemy);
			}
		} else {
			if (enemy.yPos > (playerPosY - movementMargin) && enemy.yPos < (playerPosY + movementMargin)) {
				direction = false;
				moveLeftRight(enemy);
			} else {
				moveUpDown(enemy);
			}
		}
	}
}
