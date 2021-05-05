package player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

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
		xPos = 300;
		size = 40;
		velocity = 1;
		playerShape = new Circle(xPos, yPos, size);
		playerClass = new Wizard();
	}

	public void update(GameContainer container, int delta, Input input) throws SlickException {
		if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			xPos = xPos + velocity;
			playerShape.setCenterX(xPos);
		}
		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			xPos = xPos - velocity;
			playerShape.setCenterX(xPos);
		}
		if (container.getInput().isKeyDown(Input.KEY_UP)) {
			yPos = yPos - velocity;
			playerShape.setCenterY(yPos);
		}
		if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			yPos = yPos + velocity;
			playerShape.setCenterY(yPos);
		}
		playerClass.update(container, delta, input);

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
