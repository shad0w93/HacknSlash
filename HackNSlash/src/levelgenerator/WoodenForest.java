package levelgenerator;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.w3c.dom.css.Rect;

public class WoodenForest implements DungeonState {
	public int level;
	private static final int RECTANGLESIZE = 50;
	Random random;
	// [Reihe][spalte][0 = Oben, 1 = Rechts, 2 = Unten, 3 = Links];
	public static int[][][] levelRoomDoors;

	public WoodenForest(Dungeon dungeon) {
		random = new Random();
		levelRoomDoors = new int[dungeon.walls.length][dungeon.walls[0].length][4];
		newDungeonLevel(dungeon);
		spawnMobs(dungeon);

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
		// Die T¸ren werden je nach Dungeonart auf eine andere Art generiert
		generateDoors(dungeon);
		for (int xWalls = 0; xWalls < dungeon.walls.length; xWalls++) {
			for (int yWalls = 0; yWalls < dungeon.walls[xWalls].length; yWalls++) {
				setRoomDoors(dungeon, xWalls, yWalls);
			}
		}
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
	
	//Setzt in jeden Raum auﬂenrum W‰nde
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
	
	//Bestimmt die Reihenfolge welcher Raum zuerst die T¸ren bekommt und welcher zuletzt.
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
	
	//Bestimmt die T¸ren zu einem raum von generateDoors()
	public void generateRoomDoors(Dungeon dungeon, int roomX, int roomY) {
		int[] roomsAroundX = { roomX, roomX + 1, roomX, roomX - 1 };
		int[] roomsAroundY = { roomY + 1, roomY, roomY - 1, roomY };
		boolean[] doorCanBeThere = new boolean[4];
		boolean[] otherRoomHasAlreadyADoorThere = new boolean[4];

		// doorSide: 0 = Oben, 1 = Rechts, 2 = Unten, 3 = Links
		// Erstmal ist jede Seite eine Seite mit einer mˆglichkeit f¸r eine T¸r. Erst
		// wenn auf der anderen Seite schon eine ist kann hier nicht noch eine erstellt
		// werden
		for (int i = 0; i < doorCanBeThere.length; i++) {
			doorCanBeThere[i] = true;
		}
		// Erstmal hat auﬂenrum kein Raum eine T¸r
		for (int i = 0; i < otherRoomHasAlreadyADoorThere.length; i++) {
			otherRoomHasAlreadyADoorThere[i] = false;
		}
		boolean enoughDoorsAreThere = false;
		while (!enoughDoorsAreThere) {
			for (int doorSide = 0; doorSide < levelRoomDoors[0][0].length; doorSide++) {
				// von dem Anderen Raum die T¸r Position berechnen
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
	
	//Erstellt die jeweiligen T¸ren von generateRoomDoors()
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

	//Lˆscht die jeweiligen W‰nde wo jetzt T¸ren sind (von den jeweils mitgegebenen Raum
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
	public void spawnMobs(Dungeon dungeon) {
		// TODO Auto-generated method stub

	}

	
	
}