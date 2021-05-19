package minigame;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import java.util.ArrayList;
import java.util.Random;

public class Minigame {

    private Shape circle;
    private ArrayList<Shape> circles;
    private Random random;
    private int xMiniGame;
    private int creationDelay;
    private int speed = 2;
    private int maxWindowSizeX = 500;
    private int increaseSize = 5;
    private int score;

    public Minigame(int xMiniGame) {
        random = new Random();
        circles = new ArrayList<Shape>();
        this.xMiniGame = xMiniGame;
        score = 0;
        addNewCircle(xMiniGame + random.nextInt(maxWindowSizeX), 0, 16);
    }

    public void update(GameContainer container, int delta, Input input) throws SlickException {
        creationDelay += delta;

        if (creationDelay >= 2000) {
            creationDelay = 0;
            if (circles.size() <= 5) {
                addNewCircle(xMiniGame + random.nextInt(maxWindowSizeX), 0, 16);
            }
        }

        for (int i = circles.size() - 1; i >= 0; i--) {
            Shape c = circles.get(i);

            c.setCenterY(c.getCenterY() + speed);

            if (c.getCenterY() > container.getHeight()) {
                addNewCircle(c.getCenterX(), 0, c.getBoundingCircleRadius() + increaseSize);
                circles.remove(i);
                score -= 25;
            } else if (checkInput(input, c)) {
                if (c.getBoundingCircleRadius() - increaseSize > 16) {
                    addNewCircle(c.getCenterX(), c.getCenterY(), c.getBoundingCircleRadius() - increaseSize);
                    circles.remove(i);
                } else {
                    circles.remove(i);
                    score += 150;
                }

            }
        }

    }

    private boolean checkInput(Input input, Shape currentCircle) {
        return input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) & currentCircle.contains(input.getMouseX(), input.getMouseY());
    }

    private void addNewCircle(float centerX, float centerY, float newCircleRadius) {
        circle = new Circle(centerX, centerY, newCircleRadius);
        circles.add(circle);
    }

    public void render(GameContainer container, Graphics g) {
        if (!circles.isEmpty()) {
            for (Shape currentCircle :
                    circles) {
                g.setColor(Color.darkGray);
                g.fill(currentCircle);
            }
        }
        if (score >= 0) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.red);
        }
        g.drawString("Score: " + score + " pts", container.getWidth() - (container.getWidth() / 3) + 10, 10);
        if (score < 0) {
            g.setColor(Color.red);
            g.drawString("WARNING: YOU ARE GETTING A PENALTY!", (container.getWidth() - container.getWidth() / 3) + 125, container.getHeight() / 2);
        }
    }
}
