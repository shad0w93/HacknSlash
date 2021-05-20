package main;

import levelgenerator.Dungeon;
import minigame.Minigame;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import player.Player;

public class Game extends BasicGame {

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

	public Game() {
		super("Hack'n'Slash");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new 
				Game());
		container.setDisplayMode(1500, 1000, false);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.darkGray);
		g.fill(mainGame);

		g.setColor(Color.black);
		g.fill(miniGame);

		g.setColor(Color.green);
		//g.fill(spell);
		minigame.render(container, g);
		player.render(container, g);
		dungeon.render(container, g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		input = container.getInput();
		setGameField(container);
		container.setMinimumLogicUpdateInterval(5);
		container.setMaximumLogicUpdateInterval(5);
		minigame = new Minigame(xMiniGame);
		dungeon = new Dungeon(mainGameWidth, mainGameHeight);
		player = new Player(xMiniGame);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		}
		minigame.update(container, delta, input);
		player.update(dungeon, container, delta, input);
		dungeon.update(container, delta, input, player);
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
