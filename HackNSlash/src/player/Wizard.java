package player;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import enemies.Enemy;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import java.util.ArrayList;

public class Wizard implements PlayerClass{
	//spell = new Circle(playerWizard.getxPos(), playerWizard.getyPos(), 20);
	private int mana;
	private int manaRegenerationDelay;
	private int travelSpeedProjectile = 1;
	private ArrayList<PlayerProjectile> playerProjectiles;
	private int manaCost = 5;
	private int manaRegeneration = 2;
	private int direction = 8;

	Wizard(Player player){
		mana = 100;
		player.playerProjectiles = new ArrayList<PlayerProjectile>();
	}
		
	//Getter and Setter
	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public void attack(Input input, Player player) {
		// Direction of attack (bspw. W + D schräg oben rechts)
		if (input.isKeyDown(input.KEY_W)) {
			direction = 0;
		}
		if (input.isKeyDown(input.KEY_D)) {
			direction = 1;
		}
		if (input.isKeyDown(input.KEY_S)) {
			direction = 2;
		}
		if (input.isKeyDown(input.KEY_A)) {
			direction = 3;
		}
		if (input.isKeyDown(input.KEY_W) && input.isKeyDown(input.KEY_D)) {
			direction = 4;
		}
		if (input.isKeyDown(input.KEY_S) && input.isKeyDown(input.KEY_D)) {
			direction = 5;
		}
		if (input.isKeyDown(input.KEY_S) && input.isKeyDown(input.KEY_A)) {
			direction = 6;
		}
		if (input.isKeyDown(input.KEY_W) && input.isKeyDown(input.KEY_A)) {
			direction = 7;
		}

		if(player.playerProjectiles.size() < 4) {
			player.setMp(player.getMp() - manaCost);
			PlayerProjectile playerProjectile = new PlayerProjectile(direction,travelSpeedProjectile,player.getxPos(),player.getyPos());
			player.playerProjectiles.add(playerProjectile);
		}
	}

	public void inflictEnemyDamage(int dmgAmount, Enemy enemy) {
		enemy.setHp(enemy.getHp() - dmgAmount);
	}

	@Override
	public void update(GameContainer container, int delta, Input input, Player player) {
		if (container.getInput().isKeyPressed(Input.KEY_SPACE) && player.getMp() >= manaCost) {
			attack(input, player);
		}

		for (int i = player.playerProjectiles.size() - 1; i >= 0; i--) {
			PlayerProjectile c = player.playerProjectiles.get(i);
			c.updateProjectile();
			if (c.deleteProjectiles(c,1000)) {
				player.playerProjectiles.remove(c);
			}
		}
		
		manaRegenerationDelay += delta;
		if(manaRegenerationDelay >= 1000 && player.getMp() <= 95) {
			manaRegenerationDelay = 0;
			player.setMp(player.getMp() + manaRegeneration);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g, Player player) {
		for (int i = player.playerProjectiles.size() - 1; i >= 0; i--) {
			PlayerProjectile c = player.playerProjectiles.get(i);
			g.setColor(Color.orange);
			g.fill(c.getShape());
		}
	}

	public ArrayList<PlayerProjectile> getPlayerProjectiles() {
		return playerProjectiles;
	}

	public void setPlayerProjectiles(ArrayList<PlayerProjectile> playerProjectiles) {
		this.playerProjectiles = playerProjectiles;
	}
}
