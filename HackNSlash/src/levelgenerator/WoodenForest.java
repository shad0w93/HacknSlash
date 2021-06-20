package levelgenerator;

import enemies.Enemy;
import enemies.Gnome;
import enemies.Shapeshooter;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import player.Player;

import java.util.ArrayList;
import java.util.Random;

public class WoodenForest implements DungeonState {
	public int level;
	private static final int RECTANGLESIZE = 50;
	Random random;
	// [Reihe][spalte][0 = Oben, 1 = Rechts, 2 = Unten, 3 = Links];
	public static int[][][] levelRoomDoors;
	
	//Enemies
	private ArrayList<Enemy> enemies[][];
	private int dmgCollision = 5;
	private float damageToEnemy = 100;

	public WoodenForest(Dungeon dungeon) {
		random = new Random();
		levelRoomDoors = new int[dungeon.walls.length][dungeon.walls[0].length][4];
		newDungeonLevel(dungeon);
		spawnEnemies(dungeon);
	}

	public void newDungeonLevel(Dungeon dungeon) {
		// destroyAllWallsAndDoors(dungeon);
		destroyAllWallsAndDoors(dungeon);
		levelRoomDoors = new int[dungeon.walls.length][dungeon.walls[0].length][4];
		
		for (int xWalls = 0; xWalls < dungeon.walls.length; xWalls++) {
			for (int yWalls = 0; yWalls < dungeon.walls[xWalls].length; yWalls++) {
				buildWalls(dungeon, xWalls, yWalls);
			}
		}
		// Die Türen werden je nach Dungeonart auf eine andere Art generiert
		generateDoors(dungeon);
		for (int xWalls = 0; xWalls < dungeon.walls.length; xWalls++) {
			for (int yWalls = 0; yWalls < dungeon.walls[xWalls].length; yWalls++) {
				setRoomDoors(dungeon, xWalls, yWalls);
			}
		}
		generateNextLevelStair(dungeon);
		spawnEnemies(dungeon);
	}

	private void generateNextLevelStair(Dungeon dungeon) {
		dungeon.nextLevelStair = new Rectangle[dungeon.walls.length][dungeon.walls[0].length];
		dungeon.xNextLevelStair = random.nextInt(dungeon.walls.length);
		dungeon.yNextLevelStair = random.nextInt(dungeon.walls[0].length);
		int xStairPosition = random.nextInt(600) + 200;
		int yStairPosition = random.nextInt(600) + 200;
		
		dungeon.nextLevelStair[dungeon.xNextLevelStair][dungeon.yNextLevelStair] = new Rectangle(xStairPosition, yStairPosition, RECTANGLESIZE, RECTANGLESIZE);
	}
	
	private void destroyAllWallsAndDoors(Dungeon dungeon) {
		for (int x = 0; x < dungeon.doors.length; x++) {
			for (int y = 0; y < dungeon.doors.length; y++) {
				for (int direction = 0; direction < 4; direction++) {
					for (int i = dungeon.walls[x][y][direction].size() - 1; i >= 0; i--) {
						dungeon.walls[x][y][direction].remove(i);
					}
					for (int i = dungeon.doors[x][y][direction].size() - 1; i >= 0; i--) {
						dungeon.doors[x][y][direction].remove(i);
					}
				}
			}
		}
	}
	
	//Setzt in jeden Raum außenrum Wände
	public void buildWalls(Dungeon dungeon, int roomX, int roomY) {
		int x;
		int y;
		// TopWalls
		x = 0;
		y = 0;
		while (x < dungeon.dungeonWidth) {
			dungeon.walls[roomX][roomY][0].add(new Rectangle(x, y, RECTANGLESIZE, RECTANGLESIZE));
			x += RECTANGLESIZE;
		}
		// LeftWalls
		x = 0;
		y = RECTANGLESIZE;
		while (y < (dungeon.dungeonHeight - RECTANGLESIZE)) {
			dungeon.walls[roomX][roomY][3].add(new Rectangle(x, y, RECTANGLESIZE, RECTANGLESIZE));
			y += RECTANGLESIZE;
		}
		// BottomWalls
		x = 0;
		y = dungeon.dungeonHeight - RECTANGLESIZE;
		while (x < dungeon.dungeonWidth) {
			dungeon.walls[roomX][roomY][2].add(new Rectangle(x, y, RECTANGLESIZE, RECTANGLESIZE));
			x += RECTANGLESIZE;
		}
		// RightWalls
		x = dungeon.dungeonWidth - RECTANGLESIZE;
		y = RECTANGLESIZE;
		while (y < (dungeon.dungeonHeight - RECTANGLESIZE)) {
			dungeon.walls[roomX][roomY][1].add(new Rectangle(x, y, RECTANGLESIZE, RECTANGLESIZE));
			y += RECTANGLESIZE;
		}
	}
	
	//Bestimmt die Reihenfolge welcher Raum zuerst die Türen bekommt und welcher zuletzt.
	public void generateDoors(Dungeon dungeon) {
		int middleX = levelRoomDoors.length / 2;
		int middleY = levelRoomDoors[0].length / 2;

		// Start in the middle
		boolean middleDoorsGenerated = false;
		while (middleDoorsGenerated == false) {
			for (int i = 0; i < levelRoomDoors[middleX][middleY].length; i++) {
				levelRoomDoors[middleX][middleY][i] = random.nextInt(2);
			}
			int counter = 0;
			for (int i = 0; i < levelRoomDoors[middleX][middleY].length; i++) {
				if (levelRoomDoors[middleX][middleY][i] == 1) {
					counter++;
				}
			}
			if (counter > 1) {
				middleDoorsGenerated = true;
			}
		}
		// Jetzt in der Mitte nach oben und unten gehen
		int tempSaveMiddleY = middleY;
		middleY += 1;
		while (middleY < levelRoomDoors[0].length) {
			generateRoomDoors(dungeon, middleX, middleY);
			middleY += 1;
		}

		middleY = tempSaveMiddleY;
		middleY -= 1;
		while (middleY != -1) {
			generateRoomDoors(dungeon, middleX, middleY);
			middleY -= 1;
		}
		// Jetzt nach Rechts und dort immer nach oben und unten
		middleY = tempSaveMiddleY;
		int tempSaveMiddleX = middleX;
		middleX += 1;
		while (middleX < levelRoomDoors.length) {
			tempSaveMiddleY = middleY;
			while (middleY < levelRoomDoors[0].length) {
				generateRoomDoors(dungeon, middleX, middleY);
				middleY += 1;
			}

			middleY = tempSaveMiddleY;
			while (middleY != -1) {
				generateRoomDoors(dungeon, middleX, middleY);
				middleY -= 1;
			}
			middleX += 1;
		}
		// Zuletzt das selbe noch nach Links
		middleX = tempSaveMiddleX;
		middleY = tempSaveMiddleY;
		middleX -= 1;
		while (middleX != -1) {
			tempSaveMiddleY = middleY;
			while (middleY < levelRoomDoors.length) {
				generateRoomDoors(dungeon, middleX, middleY);
				middleY += 1;
			}

			middleY = tempSaveMiddleY;
			while (middleY != -1) {
				generateRoomDoors(dungeon, middleX, middleY);
				middleY -= 1;
			}
			middleX -= 1;
		}
	}
	
	//Bestimmt die Türen zu einem raum von generateDoors()
	public void generateRoomDoors(Dungeon dungeon, int roomX, int roomY) {
		int[] roomsAroundX = { roomX, roomX + 1, roomX, roomX - 1 };
		int[] roomsAroundY = { roomY + 1, roomY, roomY - 1, roomY };
		boolean[] doorCanBeThere = new boolean[4];
		boolean[] otherRoomHasAlreadyADoorThere = new boolean[4];

		// doorSide: 0 = Oben, 1 = Rechts, 2 = Unten, 3 = Links
		// Erstmal ist jede Seite eine Seite mit einer möglichkeit für eine Tür. Erst
		// wenn auf der anderen Seite schon eine ist kann hier nicht noch eine erstellt
		// werden
		for (int i = 0; i < doorCanBeThere.length; i++) {
			doorCanBeThere[i] = true;
		}
		// Erstmal hat außenrum kein Raum eine Tür
		for (int i = 0; i < otherRoomHasAlreadyADoorThere.length; i++) {
			otherRoomHasAlreadyADoorThere[i] = false;
		}
		boolean enoughDoorsAreThere = false;
		while (!enoughDoorsAreThere) {
			for (int doorSide = 0; doorSide < levelRoomDoors[0][0].length; doorSide++) {
				// von dem Anderen Raum die Tür Position berechnen
				int otherDoorSide = 0;
				if (doorSide == 0) {
					otherDoorSide = 2;
				} else if (doorSide == 1) {
					otherDoorSide = 3;
				} else if (doorSide == 2) {
					otherDoorSide = 0;
				} else if (doorSide == 3) {
					otherDoorSide = 1;
				}
				try {
					if (levelRoomDoors[roomsAroundX[doorSide]][roomsAroundY[doorSide]][otherDoorSide] == 1) {
						otherRoomHasAlreadyADoorThere[doorSide] = true;
						levelRoomDoors[roomX][roomY][doorSide] = 1;
					} else {
						levelRoomDoors[roomX][roomY][doorSide] = random.nextInt(2);
						levelRoomDoors[roomsAroundX[doorSide]][roomsAroundY[doorSide]][otherDoorSide] = levelRoomDoors[roomX][roomY][doorSide];
					}
				} catch (Exception e) {
					// Es gibt keine raum im bereich -1
					doorCanBeThere[doorSide] = false;
				}
			}
			int enoughtDoorsCounter = 0;
			for (int counter = 0; counter < levelRoomDoors[0][0].length; counter++) {
				if (doorCanBeThere[counter] && !otherRoomHasAlreadyADoorThere[counter]
						&& levelRoomDoors[roomX][roomY][counter] == 1) {
					enoughDoorsAreThere = true;
				} else {
					if (!doorCanBeThere[counter] || otherRoomHasAlreadyADoorThere[counter]) {
						enoughtDoorsCounter++;
					}
				}
			}
			if (enoughtDoorsCounter == levelRoomDoors[0][0].length) {
				enoughDoorsAreThere = true;
			}
		}
	}
	
	//Erstellt die jeweiligen Türen von generateRoomDoors()
	public void setRoomDoors(Dungeon dungeon, int i, int f) {
		for (int d = 0; d < levelRoomDoors[0][0].length; d++) {
			if (levelRoomDoors[i][f][d] == 1) {
				if (d == 0) {// Oben
					int doorY = 0;
					int xLength = dungeon.dungeonWidth - (RECTANGLESIZE * 3);
					int doorX = 0 + (int) (Math.random() * ((xLength - 0) + 1));
					while (doorX % RECTANGLESIZE != 0) {
						doorX++;
					}
					dungeon.doors[i][f][d].add(new Rectangle(doorX, doorY, RECTANGLESIZE, RECTANGLESIZE));
					dungeon.doors[i][f][d]
							.add(new Rectangle((doorX + RECTANGLESIZE), doorY, RECTANGLESIZE, RECTANGLESIZE));
				} else if (d == 1) { // Rechts
					int doorX = dungeon.dungeonWidth - RECTANGLESIZE;
					int yLength = dungeon.dungeonHeight - (RECTANGLESIZE * 3);
					int doorY = 0 + (int) (Math.random() * ((yLength - 0) + 1));
					while (doorY % RECTANGLESIZE != 0) {
						doorY++;
					}
					dungeon.doors[i][f][d].add(new Rectangle(doorX, doorY, RECTANGLESIZE, RECTANGLESIZE));
					dungeon.doors[i][f][d]
							.add(new Rectangle(doorX, (doorY + RECTANGLESIZE), RECTANGLESIZE, RECTANGLESIZE));
				} else if (d == 2) { // Unten
					int doorY = dungeon.dungeonHeight - RECTANGLESIZE;
					int xLength = dungeon.dungeonWidth - (RECTANGLESIZE * 3);
					int doorX = 0 + (int) (Math.random() * ((xLength - 0) + 1));
					while (doorX % RECTANGLESIZE != 0) {
						doorX++;
					}
					dungeon.doors[i][f][d].add(new Rectangle(doorX, doorY, RECTANGLESIZE, RECTANGLESIZE));
					dungeon.doors[i][f][d]
							.add(new Rectangle((doorX + RECTANGLESIZE), doorY, RECTANGLESIZE, RECTANGLESIZE));
				} else if (d == 3) { // links
					int doorX = 0;
					int yLength = dungeon.dungeonHeight - (RECTANGLESIZE * 3);
					int doorY = 0 + (int) (Math.random() * ((yLength - 0) + 1));
					while (doorY % RECTANGLESIZE != 0) {
						doorY++;
					}
					dungeon.doors[i][f][d].add(new Rectangle(doorX, doorY, RECTANGLESIZE, RECTANGLESIZE));
					dungeon.doors[i][f][d]
							.add(new Rectangle(doorX, (doorY + RECTANGLESIZE), RECTANGLESIZE, RECTANGLESIZE));
				}
			}
		}
		destroyWallWhereTheDoorsAre(dungeon, i, f);
	}

	//Löscht die jeweiligen Wände wo jetzt Türen sind (von den jeweils mitgegebenen Raum
	public void destroyWallWhereTheDoorsAre(Dungeon dungeon, int roomX, int roomY) {
		for (int direction = 0; direction < 4; direction++) {
			for (Rectangle rectangle : dungeon.doors[roomX][roomY][direction]) {
				for (int i = dungeon.walls[roomX][roomY][direction].size() - 1; i >= 0; i--) {
					Rectangle c = dungeon.walls[roomX][roomY][direction].get(i);
					if (c.getCenterX() == rectangle.getCenterX() && c.getCenterY() == rectangle.getCenterY()) {
						dungeon.walls[roomX][roomY][direction].remove(i);
					}
				}
			}
		}
	}
	
	@Override
	public void spawnEnemies(Dungeon dungeon) {
		initEnemies(dungeon);
		generateEnemies(dungeon);
	}

	private void generateEnemies(Dungeon dungeon) {
		for (int i = 0; i < enemies.length; i++) {
			for (int y = 0; y < enemies[0].length; y++) {
				int mobsToGenerate = 9 + random.nextInt(11);
				for(int e = 0; e <= mobsToGenerate; e++) {
					int mobType = random.nextInt(2);
					switch(mobType) {
					case 0:
						enemies[i][y].add(new Gnome());
						break;
					case 1:
						enemies[i][y].add(new Shapeshooter());
						break;
					}
				}
			}
		}			
	}

	private void initEnemies(Dungeon dungeon) {
		enemies = new ArrayList[dungeon.maxXPosition][dungeon.maxYPosition];
		for (int i = 0; i < enemies.length; i++) {
			for (int y = 0; y < enemies[0].length; y++) {
				enemies[i][y] = new ArrayList<Enemy>();
			}
		}			
	}

	@Override
	public void moveEnemies(Dungeon dungeon, GameContainer container, int delta, Input input, Player player) throws SlickException {
		for (int i = enemies[dungeon.levelPositionX][dungeon.levelPositionY].size() - 1; i >= 0; i--) {	
			enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(i).update(container, delta, player);
			if (player.getplayerShape().intersects(enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(i).circle)) {
				enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(i).inflictPlayerDamage(dmgCollision, player);
				enemies[dungeon.levelPositionX][dungeon.levelPositionY].remove(i);
			}
		}
	}

	@Override
	public void projectileHitCalculation(Dungeon dungeon, GameContainer container, int delta, Input input, Player player) {
		for (int i = player.playerProjectiles.size() - 1; i >= 0; i--) {
			for (int j = enemies[dungeon.levelPositionX][dungeon.levelPositionY].size() - 1; j >= 0; j--) {
				System.out.println("Player Pos: " + i + "\n Enemy Pos: " + j);
				if (player.playerProjectiles.get(i).getShape().intersects(enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(j).circle)) {
					enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(j).setHp(enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(j).getHp() - damageToEnemy);
					if (enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(j).getHp() <= 0) {
						enemies[dungeon.levelPositionX][dungeon.levelPositionY].remove(j);
					}
					player.playerProjectiles.remove(i);
				}
			}
		}
	}

	@Override
	public void renderEnemies(Dungeon dungeon, GameContainer container, Graphics g) throws SlickException {
		for (int i = enemies[dungeon.levelPositionX][dungeon.levelPositionY].size() - 1; i >= 0; i--) {
			enemies[dungeon.levelPositionX][dungeon.levelPositionY].get(i).render(container, g);
			
		}			
	}
}