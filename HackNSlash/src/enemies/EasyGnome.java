package enemies;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.geom.Circle;

import hackNSlay.EnemyState;
import hackNSlay.Game;

public class EasyGnome implements EnemyState {
	private boolean direction = false; // false = nach X laufen; true = nach Y laufen;
	private int stepValue = 5; // Fortbewegungsgeschwindigkeit
	private Random random;
	int counter;
	int runningTime;
	int moveDirection;
	private float xGame = 1000;
	private float yGame = 1000;

	public EasyGnome(Gnome gnome) {
		gnome.xPos = 100;
		gnome.yPos = 100;
		gnome.circleRadius = 30;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Bob";
		runningTime = 0;
		moveDirection[][] = changeDirection();
	}

	@Override
	public void movementAction(Gnome gnome, int time) {
		random = new Random();
		
		runningTime += time;
	
		if (runningTime >= 2000) {
			moveDirection = changeDirection();
		} else {
			
		}
		counter++;
		
		
		
		
		if (counter <= 10) {
			direction = true;
		} else {
			counter = 0;
			direction = false;
		}

		if (direction) {
			moveUpDown(gnome);
		} else {
			moveLeftRight(gnome);
		}

	}

	private int changeDirection() {
		// TODO Auto-generated method stub
		
	}

	public void moveLeftRight(Gnome gnome) {

		gnome.xPos = gnome.xPos + stepValue;

		gnome.circle.setCenterX(gnome.xPos);
	}

	public void moveUpDown(Gnome gnome) {
		gnome.yPos = gnome.yPos + stepValue;

		gnome.circle.setCenterY(gnome.yPos);
	}
}
