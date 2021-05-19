package enemies;

import org.newdawn.slick.GameContainer;

public interface EnemyState {
	void update(Enemy enemy, int time, float x, float y);
}
