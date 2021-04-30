package player;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Wizard implements PlayerClass{
	//spell = new Circle(playerWizard.getxPos(), playerWizard.getyPos(), 20);

	private Shape spell;

	Wizard(){
		
	}
	
	private int mana = 100;
	
	//Getter and Setter
		public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	@Override
	public void attack(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update (GameContainer container, int delta, Input input) {
		
	}
}
