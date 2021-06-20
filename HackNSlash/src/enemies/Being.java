package enemies;

public abstract class Being {

	public float hp;
	float ms;
	float xPos;
	float yPos;
	String name;

	public void setHp(float hp) {
		this.hp = hp;
	}

	public float getHp() {
		return hp;
	}
}
