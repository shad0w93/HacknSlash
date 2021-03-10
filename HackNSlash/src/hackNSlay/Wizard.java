package hackNSlay;



public class Wizard extends Character{
	
	Wizard(String name){
		setName(name);
	}
	
	private int mana = 100;


	
	
	//Getter and Setter
		public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
}
