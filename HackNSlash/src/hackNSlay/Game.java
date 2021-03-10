package hackNSlay;

import java.util.concurrent.TimeUnit;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

import levelgenerator.*;
import enemies.*;

public class Game extends BasicGame {

    private Input input;
    private Rectangle mainGame;
    private int xMainGame;
    private int yMainGame;
    private Wizard playerWizard;
    private Shape wizardShape;
    private Shape spell;
    private int mainGameWidth;
    private int mainGameHeigth;
    public Dungeon dungeon;
    Gnome gnom;

    public Game() {
        super("Hack�n�Slash");
        gnom = new Gnome();
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

        g.setColor(Color.blue);
        g.fill(wizardShape);
        g.setColor(Color.green);
        g.fill(spell);

        dungeon.render(container, g);
        gnom.render(container, g);
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        input = container.getInput();

        setGameField();
        container.setMinimumLogicUpdateInterval(5);
        container.setMaximumLogicUpdateInterval(5);

        setGameField(container);
        container.setMinimumLogicUpdateInterval(5);
        container.setMaximumLogicUpdateInterval(5);
        dungeon = new Dungeon(mainGameWidth, mainGameHeigth);
        playerWizard = new Wizard("Mag");
        playerWizard.setxPos(500);
        playerWizard.setyPos(300);
        playerWizard.setSize(40);
        playerWizard.setVelocity(20);;
        wizardShape = new Circle(playerWizard.getxPos(), playerWizard.getyPos(), playerWizard.getSize());
        spell = new Circle(playerWizard.getxPos(), playerWizard.getyPos(), 20);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            container.exit();
        }
        
        //wizardShape.setLocation(100, 100);
        
        if (container.getInput().isKeyPressed(Input.KEY_RIGHT)) {
        	spell.setCenterX(wizardShape.getCenterX() + playerWizard.getVelocity());
        	wizardShape.setCenterX(wizardShape.getCenterX() + playerWizard.getVelocity());
		}
        if (container.getInput().isKeyPressed(Input.KEY_LEFT)) {
        	spell.setCenterX(wizardShape.getCenterX() - playerWizard.getVelocity());
        	wizardShape.setCenterX(wizardShape.getCenterX() - playerWizard.getVelocity());
		}
        if (container.getInput().isKeyPressed(Input.KEY_UP)) {
        	spell.setCenterY(wizardShape.getCenterY() - playerWizard.getVelocity());
        	wizardShape.setCenterY(wizardShape.getCenterY() - playerWizard.getVelocity());
		}
        if (container.getInput().isKeyPressed(Input.KEY_DOWN)) {
        	spell.setCenterY(wizardShape.getCenterY() + playerWizard.getVelocity());
        	wizardShape.setCenterY(wizardShape.getCenterY() + playerWizard.getVelocity());
		}
               
        if (container.getInput().isKeyPressed(Input.KEY_SPACE)) {
        	
        	
        	while (spell.getCenterX()<1000) {
        		spell.setCenterX(spell.getCenterX()+1);        		
			}
        	
		}
        
        
    }
        dungeon.update(container, delta);
        gnom.update(container, delta);
        }

    public void setGameField(GameContainer container) {
        xMainGame = 0;
        yMainGame = 0;
        mainGameWidth = container.getWidth()-(container.getWidth()/3);
        mainGameHeigth = 1000;
        mainGame = new Rectangle(xMainGame,yMainGame,mainGameWidth,mainGameHeigth);
        gnom = new Gnome();
    }
}
