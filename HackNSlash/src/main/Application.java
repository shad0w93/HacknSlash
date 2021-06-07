package main;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.Font;

public class Application extends StateBasedGame {

    // Game state identifiers
    public static final int startingScreen = 0;
    public static final int mainMenu = 1;
    public static final int game = 2;
    public static final int gameOver = 3;
    public static final int highscore = 4;
    public static final int options = 5;

    // Application Properties
    public static final int width = 1500;
    public static final int height = 1000;
    public static final int fps = 60;
    public static final double version = 1.0;

    // Class Constructor
    public Application(String appName) {
        super(appName);
    }

    public Application() {
        super("Hack'n'Slash " + version);
    }

    @Override
    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        // The first state added will be the one that is loaded first, when the application is launched
        this.addState(new StartingScreen()); // id 0
        this.addState(new MainMenu()); // id 1
        this.addState(new Game()); // id 2
        this.addState(new GameOver()); // id 3
        this.addState(new Highscore()); // id 4
        this.addState(new Options()); // id 5
    }

    // Main Method
    public static void main(String[] args) {
        try {
            AppGameContainer app = new AppGameContainer(new Application("Hack'n'Slash " + version));
            app.setDisplayMode(width, height, false);
            app.setTargetFrameRate(fps);
            app.setShowFPS(true);
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }
    public void drawCentered(String chars, Rectangle r, Graphics g, int fontSize){
        Font fontType;
        TrueTypeFont textToDraw;
        fontType = new Font("Times New Roman", Font.BOLD, fontSize);
        textToDraw = new TrueTypeFont(fontType, true);

        int width = textToDraw.getWidth(chars);
        int height = textToDraw.getHeight(chars);

        g.drawString(chars,(r.getX() + r.getWidth() / 2) - (width / 2),
                (r.getY() + r.getHeight() / 2) - (height / 2));
    }
    public void drawCenteredOnX(String chars, Rectangle r, Graphics g, int fontSize, float yPos){
        Font fontType;
        TrueTypeFont textToDraw;
        fontType = new Font("Times New Roman", Font.BOLD, fontSize);
        textToDraw = new TrueTypeFont(fontType, true);

        int width = textToDraw.getWidth(chars);

        g.drawString(chars,(r.getX() + r.getWidth() / 2) - (width / 2), yPos);
    }
}