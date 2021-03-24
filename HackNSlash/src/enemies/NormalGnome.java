package enemies;

import org.newdawn.slick.geom.Circle;

public class NormalGnome implements EnemyState {
	private boolean direction = false; // false = nach X laufen; true = nach Y laufen;
	private float playerPosX = 1000; //TODO: Ersetzen mit character rectangle pos
	private float playerPosY = 1000; //TODO: Ersetzen mit character rectangle pos
	private int stepValue = 5;	// Fortbewegungsgeschwindigkeit
	private int movementMargin = 100; // Ändert den Abstand zwischen Player & Gnome X und Y zur Positionsveränderung

	public NormalGnome(Gnome gnome) {
		gnome.xPos = 100;
		gnome.yPos = 100;
		gnome.circleRadius = 30;
		gnome.circle = new Circle(gnome.xPos, gnome.yPos, gnome.circleRadius);
		gnome.name = "Brian";
	}
	
	@Override
	public void movementAction(Gnome gnome, int time, float x, float y) {
		if (!direction) {
			if (gnome.xPos > (playerPosX - movementMargin) && gnome.xPos < (playerPosX + movementMargin)) {
				direction = true;
				moveUpDown(gnome);
			} else {
				moveLeftRight(gnome);
			}
		} else {
			if (gnome.yPos > (playerPosY - movementMargin) && gnome.yPos < (playerPosY + movementMargin)) {
				direction = false;
				moveLeftRight(gnome);
			} else {
				moveUpDown(gnome);
			}
		}
	}

	public void moveLeftRight(Gnome gnome) {
		if (playerPosX > gnome.xPos) {
			gnome.xPos = gnome.xPos + stepValue;
			gnome.circle.setCenterX(gnome.xPos);
		} else if (playerPosX < gnome.xPos) {
			gnome.xPos = gnome.xPos - stepValue;
			gnome.circle.setCenterX(gnome.xPos);
		}
	}

	public void moveUpDown(Gnome gnome) {
		if (playerPosY > gnome.yPos) {
			gnome.yPos = gnome.yPos + stepValue;
			gnome.circle.setCenterY(gnome.yPos);
		} else if (playerPosY < gnome.yPos) {
			gnome.yPos = gnome.yPos - stepValue;
			gnome.circle.setCenterY(gnome.yPos);
		}
	}

	@Override
	public void movementAction(Shapeshooter shapeshooter, int delta, float x, float y) {
		// TODO Auto-generated method stub
		
	}

}
