package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import hackNSlay.EnemyState;

public class NormalGnome implements EnemyState {
	boolean test = false; // false = nach X laufen; true = nach Y laufen;

	public NormalGnome(Gnome gnome) {
		gnome.xPos = 100;
		gnome.yPos = 100;
		gnome.circleRadius = 30;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Brian";
	}

	@Override
	public void movementAction(Gnome gnome) {

		boolean playerReached = false;
		float playerPosX = 1000;
		float playerPosY = 1000;
		int stepValue = 5;
		// p = 105 g = 100

		if (!test) {
			if (gnome.xPos > (playerPosX - 10) && gnome.xPos < (playerPosX + 10)) {
				test = true;
				moveUpDown(gnome);
			} else {
				moveLeftRight(gnome);
			}
		} else {
			if (gnome.yPos > (playerPosY - 10) && gnome.yPos < (playerPosY + 10)) {
				test = false;
				moveLeftRight(gnome);
			} else {
				moveUpDown(gnome);
			}
		}
	}

	public void moveLeftRight(Gnome gnome) {
		float playerPosX = 1000;
		float playerPosY = 1000;
		int stepValue = 5;

		if (playerPosX > gnome.xPos) {
			gnome.xPos = gnome.xPos + stepValue;
			gnome.circle.setCenterX(gnome.xPos);
		} else if (playerPosX < gnome.xPos) {
			gnome.xPos = gnome.xPos - stepValue;
			gnome.circle.setCenterX(gnome.xPos);
		}
	}

	public void moveUpDown(Gnome gnome) {
		float playerPosX = 1000;
		float playerPosY = 1000;
		int stepValue = 5;

		if (playerPosY > gnome.yPos) {
			gnome.yPos = gnome.yPos + stepValue;
			gnome.circle.setCenterY(gnome.yPos);
		} else if (playerPosY < gnome.yPos) {
			gnome.yPos = gnome.yPos - stepValue;
			gnome.circle.setCenterY(gnome.yPos);
		}
	}
}
