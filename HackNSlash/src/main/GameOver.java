package main;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {

    private Input input;
    private Application application;
    private Rectangle deadRect, restartButton, scoreButton, optionsButton, exitButton;
    private float buttonHeight=50, buttonWidth=400, startPositionX, startPositionY, margin=20;
    private String deadText = "YOU DIED!", restartText = "Restart", scoreText = "Score", optionsText = "Options", exitText = "Exit";

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = gameContainer.getInput();
        application = new Application();

        startPositionX = (gameContainer.getWidth() - buttonWidth) / 2;
        startPositionY = 350;
        deadRect = new Rectangle(startPositionX, 0 + margin, buttonWidth,buttonHeight);
        restartButton = new Rectangle(startPositionX,startPositionY,buttonWidth,buttonHeight);
        scoreButton = new Rectangle(startPositionX, restartButton.getY() + restartButton.getHeight() + margin, buttonWidth, buttonHeight);
        optionsButton = new Rectangle(startPositionX, scoreButton.getY() + scoreButton.getHeight() + margin, buttonWidth, buttonHeight);
        exitButton = new Rectangle(startPositionX, optionsButton.getY() + optionsButton.getHeight() + margin, buttonWidth, buttonHeight);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.darkGray);
        graphics.fill(restartButton);
        graphics.fill(scoreButton);
        graphics.fill(optionsButton);
        graphics.fill(exitButton);
        graphics.fill(deadRect);
        graphics.setColor(Color.red);
        application.drawCentered(deadText, deadRect, graphics,22);
        graphics.setColor(Color.white);
        application.drawCentered(restartText,restartButton,graphics,16);
        application.drawCentered(scoreText,scoreButton,graphics,16);
        application.drawCentered(optionsText,optionsButton,graphics,16);
        application.drawCentered(exitText,exitButton,graphics,16);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            // Spiel starten
            if (restartButton.contains(input.getMouseX(), input.getMouseY())) {
                stateBasedGame.getState(2).init(gameContainer, stateBasedGame);
                stateBasedGame.enterState(2);
            }
            // TODO:Zur Score wechseln (WIP)
            if (scoreButton.contains(input.getMouseX(), input.getMouseY())) {
                System.out.println("Score Test");
                //stateBasedGame.enterState(2);
            }
            // TODO:Zu Options wechseln (WIP)
            if (optionsButton.contains(input.getMouseX(), input.getMouseY())) {
                System.out.println("Options Test");
                //stateBasedGame.enterState(2);
            }
            // Spiel schlieﬂen
            if (exitButton.contains(input.getMouseX(), input.getMouseY())) {
                gameContainer.exit();
            }
        }
    }


}
