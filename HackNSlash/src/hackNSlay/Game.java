package hackNSlay;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Game extends BasicGame {

    private Input input;
    private Rectangle mainGame;
    private int xMainGame;
    private int yMainGame;

    public Game() {
        super("Hack´n´Slash");
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
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        input = container.getInput();
        setGameField();
        container.setMinimumLogicUpdateInterval(5);
        container.setMaximumLogicUpdateInterval(5);

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            container.exit();
        }
    }

    public void setGameField() {
        xMainGame = 0;
        yMainGame = 0;
        mainGame = new Rectangle(xMainGame,yMainGame,1000,1000);
    }
}
