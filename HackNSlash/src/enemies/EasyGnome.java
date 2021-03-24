package enemies;

import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.AppletGameContainer.Container;
import org.newdawn.slick.geom.Circle;

import main.Game;

public class EasyGnome implements EnemyState {
	private boolean direction = false; // false = nach X laufen; true = nach Y laufen;
	private int stepValue = 5; // Fortbewegungsgeschwindigkeit
	private Random random;
	int counter;
	int runningTime;
	int nextStep;
	int moveDirectionXY[];


	public EasyGnome(Gnome gnome) {
		random = new Random();
		gnome.xPos = 100;
		gnome.yPos = 100;
		gnome.circleRadius = 30;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Bob";
		runningTime = 0;
		nextStep = 0;
		moveDirectionXY = new int[2];
		moveDirectionXY = changeDirection();
	}

	@Override
	public void movementAction(Gnome gnome, int time, float x, float y) {
		runningTime += time;
		nextStep += time;

		if (runningTime >= 1000) {
			runningTime = 0;
			nextStep = 0;
			moveDirectionXY = changeDirection();
		} else if (nextStep >= 30){
			nextStep = 0;
			gnome.xPos = gnome.xPos + moveDirectionXY[0];
			gnome.yPos = gnome.yPos + moveDirectionXY[1];
			gnome.circle.setCenterX(gnome.xPos);
			gnome.circle.setCenterY(gnome.yPos);
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

	@Override
	public void movementAction(Shapeshooter shapeshooter, int delta, float x, float y) {
		// TODO Auto-generated method stub
		
	}

}
