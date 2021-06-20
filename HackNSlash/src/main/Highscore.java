package main;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.*;

public class Highscore extends BasicGameState {

    private Input input;
    private Application application;
    private static int highscore1,highscore2,highscore3;
    private int newHighscore;
    private Rectangle pointFrame, score1, score2, score3, returnButton;
    private float buttonHeight=50, buttonWidth=400, startPositionX, startPositionY, margin=20;
    private String playText = "Play", scoreText = "Score", optionsText = "Options", exitText = "Exit";

    public Highscore() {
    }

    public Highscore(int newHighscore) {
        this.newHighscore = newHighscore;
        if (newHighscore > highscore1) {
            highscore1 = newHighscore;
        }
        if (newHighscore > highscore2 && newHighscore < highscore1) {
            highscore2 = newHighscore;
        }
        if (newHighscore > highscore3 && newHighscore < highscore2) {
            highscore3 = newHighscore;
        }

        if (newHighscore > highscore1 || newHighscore > highscore2 || newHighscore > highscore3) {
            writeHighscore();
        }
    }

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        input = gameContainer.getInput();
        application = new Application();

        startPositionX = (gameContainer.getWidth() - buttonWidth) / 2;
        startPositionY = 150;
        pointFrame = new Rectangle(startPositionX,startPositionY,buttonWidth,500);
        returnButton = new Rectangle(pointFrame.getX(), pointFrame.getY() + pointFrame.getHeight() + margin,buttonWidth,buttonHeight);


        try {
            readHighscore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.setColor(Color.white);
        graphics.draw(pointFrame);
        application.drawCenteredOnX("Highscores", pointFrame, graphics, 16, pointFrame.getY() + margin);
        graphics.drawLine(pointFrame.getMinX(), pointFrame.getY() + margin * 2, pointFrame.getMaxX(), pointFrame.getY() + margin * 2);
        graphics.drawString("1. " + Integer.toString(highscore1), startPositionX + margin, pointFrame.getY() + margin * 3);
        graphics.drawString("2. " + Integer.toString(highscore2), startPositionX + margin, pointFrame.getY() + margin * 4);
        graphics.drawString("3. " + Integer.toString(highscore3), startPositionX + margin, pointFrame.getY() + margin * 5);
        application.drawCentered("Zurück", returnButton, graphics, 16);
        graphics.setColor(Color.darkGray);
        graphics.draw(returnButton);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
            if (returnButton.contains(input.getMouseX(),input.getMouseY())) {
                stateBasedGame.enterState(1);
            }
        }
    }


    public static void readHighscore() throws IOException {
        File save = new File("highscores.txt");
        if (!save.exists()){
            save.createNewFile();
            System.out.println("\n----------------------------------");
            System.out.println("The file has been created.");
            System.out.println("------------------------------------");
            //Places the default 0 values in the file for the high scores
            PrintWriter writer = new PrintWriter("highscores.txt", "UTF-8");
            writer.println(String.valueOf(0));
            writer.println(String.valueOf(0));
            writer.println(String.valueOf(0));
            writer.close();
        }
        //Read string values of high score and convert to int to put into variables
        BufferedReader in = new BufferedReader(new FileReader("highscores.txt"));
        String line;
        line = in.readLine();
        highscore1 = Integer.parseInt(line);
        line = in.readLine();
        highscore2 = Integer.parseInt(line);
        line = in.readLine();
        highscore3 = Integer.parseInt(line);

        in.close();
    }

    public void writeHighscore() {
        try{
            PrintWriter writer = new PrintWriter("highscores.txt", "UTF-8");
            writer.println(String.valueOf(highscore1));
            writer.println(String.valueOf(highscore2));
            writer.println(String.valueOf(highscore3));
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
