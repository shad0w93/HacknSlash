package main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class StartingScreen extends BasicGameState {

    private Input input;
    private String startGameText = "Press ENTER to start the game.";
    private Rectangle startingTextBox;
    private Application application;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = gameContainer.getInput();
        application = new Application();
        startingTextBox = new Rectangle((gameContainer.getWidth() - 400)/2, gameContainer.getHeight()/2,400,50);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        application.drawCentered(startGameText, startingTextBox, graphics, 20);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (gameContainer.getInput().isKeyPressed(Input.KEY_ENTER)) {
            stateBasedGame.enterState(1);
        }
    }
}
