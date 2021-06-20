package player;

import levelgenerator.Dungeon;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import java.awt.Font;
import java.util.ArrayList;

public class Player extends Character {

	PlayerClass playerClass;
	public ArrayList<PlayerProjectile> playerProjectiles;
	private String name;
	private Shape playerShape;
	private Rectangle hpBarMax;
	private Rectangle currentHp;
	private Rectangle mpBarMax;
	private Rectangle currentMp;
	private int maxHpAsInt = 100;
	private int maxMpAsInt = 100;
	private Rectangle skill1;
	private Rectangle skill2;
	private Rectangle skill3;
	private Rectangle skill4;
	private boolean skillsAvailable = false;
	private Font font;
	private TrueTypeFont hpString;
	private TrueTypeFont mpString;
	private float xPos;
	private float yPos;
	private float size;
	private float velocity;

	public Player(int xMiniGame) {
		name = "Joe";
		xPos = 500;
		yPos = 500;
		size = 40;
		lp = 100;
		mp = 100;
		velocity = 1;
		playerShape = new Circle(xPos, yPos, size);
		playerClass = new Wizard(this);

		initializeUI(xMiniGame);
	}

	private void initializeUI(int xMiniGame) {
		font = new Font("Times New Roman", font.BOLD , 16);
		hpString = new TrueTypeFont(font, true);
		mpString = new TrueTypeFont(font, true);
		hpBarMax = new Rectangle(xMiniGame + 5,5,100 + 5,20 + 5);
		currentHp = new Rectangle(xMiniGame + 7,7,100,20);
		mpBarMax = new Rectangle(hpBarMax.getX(),hpBarMax.getY() + hpBarMax.getHeight() + 3,hpBarMax.getWidth(),hpBarMax.getHeight());
		currentMp = new Rectangle(currentHp.getX(),mpBarMax.getY() + 2,currentHp.getWidth(),currentHp.getHeight());
		skill1 = new Rectangle(xMiniGame + hpBarMax.getWidth() + 20, 5, 75, 75);
		skill2 = new Rectangle(skill1.getWidth() + skill1.getX() + 10, skill1.getY(), skill1.getWidth(), skill1.getHeight());
		skill3 = new Rectangle(skill1.getWidth() + skill2.getX() + 10, skill2.getY(), skill1.getWidth(), skill1.getHeight());
		skill4 = new Rectangle(skill1.getWidth() + skill3.getX() + 10, skill3.getY(), skill1.getWidth(), skill1.getHeight());
	}

	public void update(Dungeon dungeon, GameContainer container, int delta, Input input) throws SlickException {
		updatePlayerHealth(dungeon, container, delta, input);
		movePlayer(dungeon, container, delta, input);
		playerClass.update(container, delta, input, this);
	}
	
    private void updatePlayerHealth(Dungeon dungeon, GameContainer container, int delta, Input input) {
		int maxHealth = (int) (hpBarMax.getWidth() - 5);
		int maxMana = (int) (mpBarMax.getWidth() - 5);
		if (lp != 100) {
			currentHp.setWidth(maxHealth *  lp / 100);
		} else {
			currentHp.setWidth(maxHealth);
		}
		if (mp != 100) {
			currentMp.setWidth(maxMana *  mp / 100);
		} else {
			currentMp.setWidth(maxMana);
		}
    }
    
	private void movePlayer(Dungeon dungeon, GameContainer container, int delta, Input input) {
		if (container.getInput().isKeyDown(Input.KEY_D)) {
			xPos = xPos + velocity;
			playerShape.setCenterX(xPos);
			if (checkForWallCollision(dungeon, 1)) {
				xPos = xPos - velocity;
				playerShape.setCenterX(xPos);
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_A)) {
			xPos = xPos - velocity;
			playerShape.setCenterX(xPos);
			if (checkForWallCollision(dungeon, 3)) {
				xPos = xPos + velocity;
				playerShape.setCenterX(xPos);
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_W)) {
			yPos = yPos - velocity;
			playerShape.setCenterY(yPos);
			if (checkForWallCollision(dungeon, 0)) {
				yPos = yPos + velocity;
				playerShape.setCenterX(xPos);
			}
		}
		if (container.getInput().isKeyDown(Input.KEY_S)) {
			yPos = yPos + velocity;
			playerShape.setCenterY(yPos);
			if (checkForWallCollision(dungeon, 2)) {
				yPos = yPos - velocity;
				playerShape.setCenterX(xPos);
			}
		}
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

	public void render(GameContainer container, Graphics g, Player player) {
		g.setColor(Color.green);
		g.fill(playerShape);

		drawUI(g);

		playerClass.render(container,g, player);
	}

	private void drawUI(Graphics g) {
		g.setColor(Color.white);
		g.fill(hpBarMax);
		g.fill(mpBarMax);
		if (skillsAvailable) {
			g.fill(skill1);
			g.fill(skill2);
			g.fill(skill3);
			g.fill(skill4);
		}
		g.setColor(Color.red);
		g.fill(currentHp);
		g.setColor(Color.blue);
		g.fill(currentMp);
		hpString.drawString(hpBarMax.getCenterX() - hpBarMax.getWidth() / 2 + 4, hpBarMax.getCenterY() - currentHp.getHeight() / 2, "HP: " + getLp() + "/" + maxHpAsInt, Color.black);
		mpString.drawString(mpBarMax.getCenterX() - mpBarMax.getWidth() / 2 + 4, mpBarMax.getCenterY() - currentMp.getHeight() / 2, "MP: " + mp + "/" + maxMpAsInt, Color.black);
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
