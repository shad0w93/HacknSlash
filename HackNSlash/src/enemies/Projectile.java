package enemies;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Projectile {
	private Shape shape;
	private int direction = 0; // 0 = initial 1 = nach links schieﬂen, 2 = nach rechts schieﬂen
	private int travelSpeed;
	private float xPos;
	private float yPos;
	private Random random;
	int dungeonSizeX;
	int dungeonSizeY;
	int gameBorderXY;

	public Projectile(int direction, int travelSpeed, float xPos, float yPos) {
		super();
		this.direction = direction;
		this.travelSpeed = travelSpeed;
		this.xPos = xPos;
		this.yPos = yPos;

		createProjectile();
	}

	void updateProjectile() {
		if (shape != null) {
			if (direction == 1) {
				shape.setCenterX(shape.getCenterX() - travelSpeed);
			} else {
				shape.setCenterX(shape.getCenterX() + travelSpeed);
			}
		}
	}

	private void createProjectile() {
		random = new Random();
		int randomNumber = random.nextInt(3);

		switch (randomNumber) {
		case 0:
			shape = new Rectangle(xPos, yPos, random.nextInt(20), random.nextInt(20) + 20);
			break;
		case 1:
			shape = new Circle(xPos, yPos, random.nextInt(20));
			break;
		case 2:
			float[] points = { 0, 7, 20, 40, 40, 7 };
			shape = new Polygon(points);
			break;
		default:
			break;
		}
		shape.setCenterX(xPos);
		shape.setCenterY(yPos);
	}

	static void deleteProjectiles(ArrayList<Projectile> projectiles, int dungeonSizeX) {
		if (projectiles.size() == 0 || projectiles == null) {
			return;
		} else {
			for (int i = projectiles.size() - 1; i >= 0; i--) {
				Projectile c = projectiles.get(i);
				if (c.shape.getCenterX() < 0 || c.shape.getCenterX() > dungeonSizeX) {
					projectiles.remove(i);
				}
			}
		}
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
