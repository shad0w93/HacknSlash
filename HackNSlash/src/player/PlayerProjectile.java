package player;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

public class PlayerProjectile {
	private Shape shape;
	private int direction; // 0 = oben, 1 = rechts, 2 = unten, 3 = links, 4 = oben rechts, 5 = unten rechts, 6 = unten links, 7 = oben links
	private int travelSpeed;
	private float xPos;
	private float yPos;

	public PlayerProjectile(int direction, int travelSpeed, float xPos, float yPos) {
		super();
		this.direction = direction;
		this.travelSpeed = travelSpeed;
		this.xPos = xPos;
		this.yPos = yPos;

		createProjectile();
	}

	private void createProjectile() {
		shape = new Circle(getxPos(),getyPos(), 8);
	}

	void updateProjectile() {
		if (shape != null) {
			switch (direction) {
				case 0:
					// nach oben
					shape.setCenterY(shape.getCenterY() - travelSpeed);
					break;
				case 1:
					// nach rechts
					shape.setCenterX(shape.getCenterX() + travelSpeed);
					break;
				case 2:
					// nach unten
					shape.setCenterY(shape.getCenterY() + travelSpeed);
					break;
				case 3:
					// nach links
					shape.setCenterX(shape.getCenterX() - travelSpeed);
					break;
				case 4:
					// oben rechts
					shape.setCenterX(shape.getCenterX() + travelSpeed);
					shape.setCenterY(shape.getCenterY() - travelSpeed);
					break;
				case 5:
					// unten rechts
					shape.setCenterX(shape.getCenterX() + travelSpeed);
					shape.setCenterY(shape.getCenterY() + travelSpeed);
					break;
				case 6:
					// nach unten links
					shape.setCenterX(shape.getCenterX() - travelSpeed);
					shape.setCenterY(shape.getCenterY() + travelSpeed);
					break;
				case 7:
					// nach oben links
					shape.setCenterX(shape.getCenterX() - travelSpeed);
					shape.setCenterY(shape.getCenterY() - travelSpeed);
					break;
				default:
					System.out.println("Direction not found.");
			}
		}
	}

	static boolean deleteProjectiles(PlayerProjectile projectile, int dungeonSizeX) {
		if (projectile.getShape().getCenterX() < 0 || projectile.getShape().getCenterX() > dungeonSizeX || projectile.getShape().getCenterY() < 0 || projectile.getShape().getCenterY() > 1000) {
			return true;
		}
		return false;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getTravelSpeed() {
		return travelSpeed;
	}

	public void setTravelSpeed(int travelSpeed) {
		this.travelSpeed = travelSpeed;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
}
