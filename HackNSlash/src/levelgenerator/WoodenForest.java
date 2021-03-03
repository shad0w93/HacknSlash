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
	
	public void generateRoomDoors(Dungeon dungeon, int middleRoomX,int middleRoomY) {
		int[] roomX = {middleRoomX, middleRoomX +1, middleRoomX, middleRoomX -1};
		int[] roomY = {middleRoomY +1, middleRoomY, middleRoomY -1, middleRoomY};
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
