package enemies;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import hackNSlay.EnemyState;

public class NormalGnom implements EnemyState {

	public NormalGnom(Gnom gnom) {
		gnom.xPos = 100;
		gnom.yPos = 100;
		gnom.circleRadius = 30;
		gnom.circle = new Circle(gnom.xPos, gnom.yPos, gnom.circleRadius);
		gnom.name = "Brian";
	}

	@Override
	public void movementAction(Gnom gnom) {
		// TODO Auto-generated method stub
		gnom.xPos = gnom.xPos + 5;
		gnom.circle.setCenterX(gnom.xPos);
	}


}
