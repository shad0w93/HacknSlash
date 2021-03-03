package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import hackNSlay.EnemyState;

public class NormalGnome implements EnemyState {

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
		float playerPosX = 50;
		float playerPosY = 500;
		Integer stepValue = 5;

		if (!gnome.circle.contains(playerPosX, playerPosY)) {
			if (playerPosX > gnome.xPos) {
				gnome.xPos = gnome.xPos - stepValue;
				gnome.circle.setCenterX(gnome.xPos);
			}else if (playerPosX < gnome.xPos) {
				gnome.xPos = gnome.xPos + stepValue;
				gnome.circle.setCenterX(gnome.xPos);
			}else if (playerPosY > gnome.yPos) {
				gnome.yPos = gnome.yPos + stepValue;
				gnome.circle.setCenterY(gnome.xPos);
			}else if (playerPosY < gnome.yPos) {
				gnome.yPos = gnome.yPos - stepValue;
				gnome.circle.setCenterY(gnome.xPos);			
			}
		}
}

}
