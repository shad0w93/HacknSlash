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
	private int counterLeft;
	private int counterRight;
	private float xGame = 1000;
	private float yGame = 1000;

	public EasyGnome(Gnome gnome) {
		gnome.xPos = 100;
		gnome.yPos = 100;
		gnome.circleRadius = 30;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Bob";
	}

	@Override
	public void movementAction(Gnome gnome) {
		random = new Random();
		
		if (!direction) {
			direction = true;
			moveUpDown(gnome);
		} else {
			moveLeftRight(gnome);
		}
	}

	public void moveLeftRight(Gnome gnome) {
		if (counter > 10) {
		gnome.xPos = gnome.xPos - stepValue;
		counter = 0;
		}
		else {
		gnome.xPos = gnome.xPos + stepValue;
		}
		gnome.circle.setCenterX(gnome.xPos);
	}

	public void moveUpDown(Gnome gnome) {
		if (gnome.circle.getCenterY() <= yGame - stepValue && gnome.circle.getCenterY() >= 0 + stepValue) {
			direction = random.nextBoolean();
			if (!direction) {
				gnome.yPos += stepValue;
			} else {
				gnome.yPos -= stepValue;
			}
			gnome.circle.setCenterY(gnome.yPos);
		}
	}
}
