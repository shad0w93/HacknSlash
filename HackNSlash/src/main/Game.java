package main;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import levelgenerator.*;
import player.*;
import enemies.*;

public class Game extends BasicGame {

	private Input input;
	private Rectangle mainGame;
	private int xMainGame;
	private int yMainGame;
	private int mainGameWidth;
	private int mainGameHeight;
	private Dungeon dungeon;
	private Player player;
	Gnome gnome;
	Shapeshooter shapeshooter;

	public Game() {
		super("Hack'n'Slash");
		gnome = new Gnome();
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new Game());
		container.setDisplayMode(1500, 1000, false);
		container.start();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.setColor(Color.darkGray);
		g.fill(mainGame);

		g.setColor(Color.green);
		//g.fill(spell);
		player.render(container, g);
		dungeon.render(container, g);
		gnome.render(container, g);
		shapeshooter.render(container, g);	
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		input = container.getInput();
		setGameField(container);
		container.setMinimumLogicUpdateInterval(5);
		container.setMaximumLogicUpdateInterval(5);
		dungeon = new Dungeon(mainGameWidth, mainGameHeight);
		player = new Player();

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		}
		player.update(container, delta, input);
		dungeon.update(container, delta, input, player.getplayerShape());
		gnome.update(container, delta, player.getxPos(), player.getyPos());
		shapeshooter.update(container, delta, player.getxPos(), player.getyPos());
	}

	public void setGameField(GameContainer container) {
		xMainGame = 0;
		yMainGame = 0;
		mainGameWidth = container.getWidth() - (container.getWidth() / 3);
		mainGameHeight = 1000;
		mainGame = new Rectangle(xMainGame, yMainGame, mainGameWidth, mainGameHeight);
		gnome = new Gnome();
		shapeshooter = new Shapeshooter();
	}
}
