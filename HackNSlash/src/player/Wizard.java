package player;

import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Wizard implements PlayerClass{
	//spell = new Circle(playerWizard.getxPos(), playerWizard.getyPos(), 20);
	private ArrayList<Circle> spells;
	private int mana;
	private int manaRegenerationDelay;


	Wizard(){
		mana = 100;
		spells = new ArrayList<Circle>();
	}
		
	//Getter and Setter
		public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	@Override
	public void attack(Input input) {
		if(spells.size() < 4) {
			mana -= 10;
			spells.add(new Circle(100, 100, 20, 20));
		}
	}
	
	@Override
	public void update (GameContainer container, int delta, Input input) {
		if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
			attack(input);
		}
		
		manaRegenerationDelay += delta;
		if(manaRegenerationDelay >= 1000 && mana <= 95) {
			manaRegenerationDelay = 0;
			mana += 5;
		}
		
	}
}
