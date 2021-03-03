package levelgenerator;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class WoodenForest implements DungeonState {
	public int level;
	public static int rectangleSize = 25;
	Random random;

	public WoodenForest(Dungeon dungeon) {
		random = new Random();
		drawField(dungeon);
		generateDoors(dungeon);
		generateWalls(dungeon);
		generateMobs(dungeon);
	}

	@Override
	public void drawField(Dungeon dungeon) {

	}

	@Override
	public void generateDoors(Dungeon dungeon) {
		int middleX = dungeon.levelRoomDoors.length / 2;
		int middleY = dungeon.levelRoomDoors[0].length / 2;

		//System.out.println(middleX);
		//System.out.println(middleY);

		// Start in the middle
		boolean middleDoorsGenerated = false;
		while (middleDoorsGenerated == false) {
			for (int i = 0; i < dungeon.levelRoomDoors[middleX][middleY].length; i++) {
				dungeon.levelRoomDoors[middleX][middleY][i] = random.nextInt(2);
				System.out.println(dungeon.levelRoomDoors[middleX][middleY][i]);
			}
			for (int i = 0; i < dungeon.levelRoomDoors[middleX][middleY].length; i++) {
				if (dungeon.levelRoomDoors[middleX][middleY][i] == 1) {
					middleDoorsGenerated = true;
				}
			}
		}
		int tempSaveMiddleX = middleX;
		while (middleX < dungeon.levelRoomDoors.length) {
			middleX += 1;
			generateRoomDoors(dungeon, middleX, middleY);
		}
		middleX = tempSaveMiddleX;
		while(middleX >= 0) {
			middleX -= 1;
			generateRoomDoors(dungeon, middleX, middleY);
		}
		
		
	}
	
	public void generateRoomDoors(Dungeon dungeon, int roomX,int roomY) {
		int[] roomsAroundX = {roomX +1, roomX, roomX -1, roomX};
		int[] roomsAroundY = {roomY, roomY +1, roomY, roomY -1};
		boolean[][][] doorCanBeGenerated = new boolean[dungeon.levelRoomDoors.length][dungeon.levelRoomDoors[0].length][dungeon.levelRoomDoors[0][0].length];
		
		//Prüfe ob außenrum nochmal ein Raum existiert, da man ja ansonsten dort auch keine Tür hinmachen kann.
		for(int i = 0; i < dungeon.levelRoomDoors.length; i++) {
			//Raum existiert?
			if(roomsAroundX[i] <= dungeon.levelRoomDoors.length && roomsAroundY[i] <= dungeon.levelRoomDoors[i].length) {
				//Ist von der aktuellen Türgenerationsposition (oben unten etc...) das gegenteil
				int otherSide;
				if(i == 0) {
					otherSide = 2;
				} else if (i == 1) {
					otherSide = 3;
				} else if (i == 2) {
					otherSide = 0;
				} else if (i == 3) {
					otherSide = 1;
				}
				//Ist denn in dem Raum der daran anschließt bereits eine Tür (Sollte dort ein Raum existieren)?
				if(dungeon.levelRoomDoors[roomsAroundX[i]][roomsAroundY[i]][otherSide] == 1) {
					doorCanBeGenerated[roomX][roomY][i] = true;
				} else {
					int checkRoomX;
					int checkRoomY;
					boolean nextRoomHasNoDoors = false;
					for (int y = 0; y < dungeon.levelRoomDoors[middleX][middleY].length; y++) {
						if (dungeon.levelRoomDoors[middleX][middleY][i] == 1) {
							middleDoorsGenerated = true;
						}
					}
				}
			}
		}
		
		
	}
	
	@Override
	public void generateWalls(Dungeon dungeon) {
		int x;
		int y;
		boolean[][][] doorIsGenerated = new boolean[dungeon.levelRoomDoors.length][dungeon.levelRoomDoors[0].length][dungeon.levelRoomDoors[0][0].length];
		// TopWalls
		x = 0;
		y = 0;
		while (x < dungeon.dungeonWidth) {
			if (x == 0 || x == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			x += rectangleSize;
		}
		// LeftWalls
		x = 0;
		y = rectangleSize;
		while (y < (dungeon.dungeonHeight - rectangleSize)) {
			if (y == 0 || y == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			y += rectangleSize;
		}

		// BottomWalls
		x = 0;
		y = dungeon.dungeonHeight - rectangleSize;
		while (x < dungeon.dungeonWidth) {
			if (x == 0 || x == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			x += rectangleSize;
		}
		// RightWalls
		x = dungeon.dungeonWidth - rectangleSize;
		y = rectangleSize;
		System.out.println(x);

		while (y < (dungeon.dungeonHeight - rectangleSize)) {
			if (y == 0 || y == (dungeon.dungeonWidth - rectangleSize)) {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			} else {
				dungeon.walls.add(new Rectangle(x, y, rectangleSize, rectangleSize));
			}
			System.out.println(y);
			y += rectangleSize;
		}
	}

	@Override
	public void generateMobs(Dungeon dungeon) {
		// TODO Auto-generated method stub

	}

}
