package enemies;

import player.Player;

public interface EnemyState {
	void update(Enemy enemy, int time, Player player);
}
