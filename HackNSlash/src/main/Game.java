package main;

import levelgenerator.Dungeon;
import minigame.Minigame;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import player.Player;

public class Game extends BasicGameState {

	private Input input;
	private Rectangle mainGame;
	private Rectangle miniGame;
	private int xMainGame;
	private int yMainGame;
	private int xMiniGame;
	private int mainGameWidth;
	private int mainGameHeight;
	private Minigame minigame;
	private Dungeon dungeon;
	private Player player;
	private Highscore highscore;

	@Override
	public int getID() {
		return 2;
	}

	@Override
	public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
		input = gameContainer.getInput();
		setGameField(gameContainer);
		gameContainer.setMinimumLogicUpdateInterval(5);
		gameContainer.setMaximumLogicUpdateInterval(5);
		minigame = new Minigame(xMiniGame);
		dungeon = new Dungeon(mainGameWidth, mainGameHeight);
		player = new Player(xMiniGame);
	}

	@Override
	public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
		graphics.setColor(Color.darkGray);
		graphics.fill(mainGame);

		graphics.setColor(Color.black);
		graphics.fill(miniGame);

		graphics.setColor(Color.green);
		//g.fill(spell);
		minigame.render(gameContainer, graphics);
		player.render(gameContainer, graphics, player);
		dungeon.render(gameContainer, graphics);
	}

	@Override
	public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			stateBasedGame.enterState(1);
		}

		minigame.update(gameContainer, i , input);
		player.update(dungeon, gameContainer, i, input);
		dungeon.update(gameContainer, i, input, player);

		if (player.getLp() <= 0) {
			highscore = new Highscore(minigame.getScore());
			stateBasedGame.enterState(3);
		}
	}

	public void setGameField(GameContainer container) {
		xMainGame = 0;
		yMainGame = 0;
		mainGameWidth = container.getWidth() - (container.getWidth() / 3);
		mainGameHeight = 1000;
		mainGame = new Rectangle(xMainGame, yMainGame, mainGameWidth, mainGameHeight);
		xMiniGame = mainGameWidth;
		miniGame = new Rectangle(xMainGame + mainGameWidth, yMainGame, container.getWidth()/3, container.getHeight());
	}
}
