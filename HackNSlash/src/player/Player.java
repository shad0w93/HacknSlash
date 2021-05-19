package player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import levelgenerator.Dungeon;

public class Player {

	private String name;
	private Shape playerShape;
	private PlayerClass playerClass;
	private int lp = 100;
	private float xPos;
	private float yPos;
	private float size;
	private float velocity;

	public Player() {
		name = "Joe";
		xPos = 500;
		yPos = 500;
		size = 40;
		velocity = 1;
		playerShape = new Circle(xPos, yPos, size);
		playerClass = new Wizard();
	}

	public void update(Dungeon dungeon, GameContainer container, int delta, Input input) throws SlickException {
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			xPos = xPos + velocity;
			playerShape.setCenterX(xPos);
			if (checkForWallCollision(dungeon, 1)) {
				xPos = xPos - velocity;
				playerShape.setCenterX(xPos);
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			xPos = xPos - velocity;
			playerShape.setCenterX(xPos);
			if (checkForWallCollision(dungeon, 3)) {
				xPos = xPos + velocity;
				playerShape.setCenterX(xPos);
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			yPos = yPos - velocity;
			playerShape.setCenterY(yPos);
			if (checkForWallCollision(dungeon, 0)) {
				yPos = yPos + velocity;
				playerShape.setCenterX(xPos);
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			yPos = yPos + velocity;
			playerShape.setCenterY(yPos);
			if (checkForWallCollision(dungeon, 2)) {
				yPos = yPos - velocity;
				playerShape.setCenterX(xPos);
			}
		}
		playerClass.update(container, delta, input);

	}

	private boolean checkForWallCollision(Dungeon dungeon, int direction) {
		boolean hitsAWall = false;
			for (Rectangle wall : dungeon.walls[dungeon.levelPositionX][dungeon.levelPositionY][direction]) {
				if (getplayerShape().intersects(wall)) {
					hitsAWall = true;
					direction = 5;
				}
			}
		return hitsAWall;
	}

	public void render(GameContainer container, Graphics g) {
		g.setColor(Color.green);
		g.fill(playerShape);
	}

	// Getter and Setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLp() {
		return lp;
	}

	public void setLp(int lp) {
		this.lp = lp;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public Shape getplayerShape() {
		return playerShape;
	}

}
