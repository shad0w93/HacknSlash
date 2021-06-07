package player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public interface PlayerClass {
	public void update(GameContainer container, int delta, Input input);
	public void attack(Input input);
}
