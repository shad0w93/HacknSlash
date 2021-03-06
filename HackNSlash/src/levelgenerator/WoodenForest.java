package levelgenerator;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.w3c.dom.css.Rect;

public class WoodenForest implements DungeonState {
	public int level;
	public static int rectangleSize = 25;
	Random random;
	// [Reihe][spalte][0 = Oben, 1 = Rechts, 2 = Unten, 3 = Links];
	public int[][][] levelRoomDoors;

	public WoodenForest(Dungeon dungeon) {
		levelRoomDoors = new int[dungeon.walls.length][dungeon.walls[0].length][4];
		random = new Random();
		drawField(dungeon);
		for (int xWalls = 0; xWalls < dungeon.walls.length; xWalls++) {
			for (int yWalls = 0; yWalls < dungeon.walls[xWalls].length; yWalls++) {
				generateWalls(dungeon, xWalls, yWalls);
			}
		}
		for (int xWalls = 0; xWalls < dungeon.walls.length; xWalls++) {
			for (int yWalls = 0; yWalls < dungeon.walls[xWalls].length; yWalls++) {
				generateWalls(dungeon, xWalls, yWalls);
			}
		}
		// Hier wird nicht einfach nur durch das Array gegangen das hier immer nach
		// einer anderen Logik die Türen generiert werden
		generateDoors(dungeon);
		for (int xWalls = 0; xWalls < dungeon.walls.length; xWalls++) {
			for (int yWalls = 0; yWalls < dungeon.walls[xWalls].length; yWalls++) {
				setRoomDoors(dungeon, xWalls, yWalls);
			}
		}
		generateMobs(dungeon);
	}

	@Override
	public void drawField(Dungeon dungeon) {

	}

	public void setRoomDoors(Dungeon dungeon, int i, int f) {
		for (int d = 0; d < levelRoomDoors[0][0].length; d++) {
			if (levelRoomDoors[i][f][d] == 1) {
				if (d == 0) {// Oben
					int doorY = 0;
					int xLength = dungeon.dungeonWidth - (rectangleSize * 3);
					int doorX = 0 + (int) (Math.random() * ((xLength - 0) + 1));
					while (doorX % 25 != 0) {
						doorX++;
					}
					dungeon.doors[i][f].add(new Rectangle(doorX, doorY, rectangleSize, rectangleSize));
				} else if (d == 1) { // Rechts
					int doorX = dungeon.dungeonWidth - rectangleSize;
					int yLength = dungeon.dungeonHeight - (rectangleSize * 3);
					int doorY = 0 + (int) (Math.random() * ((yLength - 0) + 1));
					while (doorY % 25 != 0) {
						doorY++;
					}
					dungeon.doors[i][f].add(new Rectangle(doorX, doorY, rectangleSize, rectangleSize));
				} else if (d == 2) { // Unten
					int doorY = dungeon.dungeonHeight - rectangleSize;
					int xLength = dungeon.dungeonWidth - (rectangleSize * 3);
					int doorX = 0 + (int) (Math.random() * ((xLength - 0) + 1));
					while (doorX % 25 != 0) {
						doorX++;
					}
					dungeon.doors[i][f].add(new Rectangle(doorX, doorY, rectangleSize, rectangleSize));
				} else if (d == 3) { // links
					int doorX = 0;
					int yLength = dungeon.dungeonHeight - (rectangleSize * 3);
					int doorY = 0 + (int) (Math.random() * ((yLength - 0) + 1));
					while (doorY % 25 != 0) {
						doorY++;
					}
					dungeon.doors[i][f].add(new Rectangle(doorX, doorY, rectangleSize, rectangleSize));
				}
			}
		}
		destroyWall(dungeon, i, f);
	}

	public void destroyWall(Dungeon dungeon, int roomX, int roomY) {
		for (Rectangle rectangle : dungeon.doors[roomX][roomY]) {
			for (int i = dungeon.walls[roomX][roomY].size() - 1; i >= 0; i--) {
				Rectangle c = dungeon.walls[roomX][roomY].get(i);
				if (c.getCenterX() == rectangle.getCenterX() && c.getCenterY() == rectangle.getCenterY()) {
					dungeon.walls[roomX][roomY].remove(i);
				}
			}
		}
	}

	@Override
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
					doorCanBeThere[doorSide] = false;
					// Es gibt keine raum im bereich -1
				}
			}
			int enoughtDoorsCounter = 0;
			for (int counter = 0; counter < levelRoomDoors[0][0].length; counter++) {
				if (doorCanBeThere[counter] && !otherRoomHasAlreadyADoorThere[counter]
						&& levelRoomDoors[roomX][roomY][counter] == 1) {
					enoughDoorsAreThere = true;
					if(roomX == 0 && roomY == 1) {
						System.out.println(levelRoomDoors[roomX][roomY][counter]);
					}
					if(roomX == 2 && roomY == 1) {
						System.out.println(levelRoomDoors[roomX][roomY][counter]);
					}
				} else {
					if (!doorCanBeThere[counter] || otherRoomHasAlreadyADoorThere[counter]) {
						enoughtDoorsCounter++;
						if(roomX == 0 && roomY == 1) {
							System.out.println(enoughtDoorsCounter);
						}
						if(roomX == 2 && roomY == 1) {
							System.out.println(enoughtDoorsCounter);
						}
					}
				}
			}
			if (enoughtDoorsCounter == levelRoomDoors[0][0].length) {
				enoughDoorsAreThere = true;
				if(roomX == 0 && roomY == 1) {
					System.out.println(enoughtDoorsCounter);
				}
				if(roomX == 2 && roomY == 1) {
					System.out.println(enoughtDoorsCounter);
				}
			}
		}
	}

	@Override
	public void generateWalls(Dungeon dungeon, int roomX, int roomY) {
		int x;
		int y;
		boolean[][][] doorIsGenerated = new boolean[levelRoomDoors.length][levelRoomDoors[0].length][levelRoomDoors[0][0].length];
		// TopWalls
		x = 0;
		y = 0;
		while (x < dungeon.dungeonWidth) {
			if (x == 0 || x == (dungeon.dungeonWidth - (rectangleSize *3))) {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			x += rectangleSize;
		}
		// LeftWalls
		x = 0;
		y = rectangleSize;
		while (y < (dungeon.dungeonHeight - rectangleSize)) {
			if (y == 0 || y == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			y += rectangleSize;
		}

		// BottomWalls
		x = 0;
		y = dungeon.dungeonHeight - rectangleSize;
		while (x < dungeon.dungeonWidth) {
			if (x == 0 || x == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			x += rectangleSize;
		}
		// RightWalls
		x = dungeon.dungeonWidth - rectangleSize;
		y = rectangleSize;
		while (y < (dungeon.dungeonHeight - rectangleSize)) {
			if (y == 0 || y == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls[roomX][roomY].add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			y += rectangleSize;
		}
	}

	@Override
	public void generateMobs(Dungeon dungeon) {
		// TODO Auto-generated method stub

	}

}
