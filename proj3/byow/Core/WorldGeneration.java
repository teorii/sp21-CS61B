package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGeneration {
    private static final int WIDTH = Engine.WIDTH;
    private static final int HEIGHT = Engine.HEIGHT;
    private static final int ROOMMAX = 20;
    private static final int ROOMMIN = 10;
    private static List<Room> roomList = new ArrayList<Room>();

    public static TETile[][] worldBuilder(long seed) {
        Random random = new Random(seed);
        return main(random);
    }

    public static TETile[][] main(Random random) {
        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int roomAmount = random.nextInt(ROOMMAX - ROOMMIN);
        roomAmount += ROOMMIN;
        placeRandomRooms(random, world, roomAmount);
        connectRooms(world);

        placeAvatar(world);

        return world;
    }

    private static void placeRandomRooms(Random randomGenerator, TETile[][] world, int amount) {
        for (int i = 0; i < amount; i++) {
            Room randomRoom = Room.getRandomRoom(randomGenerator);
            roomList.add(randomRoom);
            renderRoom(randomRoom, world);
        }
    }

    private static void renderRoom(Room room, TETile[][] world) {
        int roomWidth = room.getBottomRightX() - room.getTopLeftX();
        int roomLength = room.getTopLeftY() - room.getBottomRightY();

        renderWalls(world, room, roomWidth, roomLength);
        renderFloor(world, room, roomWidth, roomLength);
    }

    private static void renderFloor(TETile[][] world, Room room, int roomWidth, int roomLength) {

        /* makes floor */
        for (int i = 1; i < roomWidth; i++) {
            for (int j = 1; j < roomLength; j++) {
                placeTile((room.getTopLeftX() + i),
                        (room.getTopLeftY() - j),
                        Tileset.FLOOR, world);
            }
        }


    }

    private static void connectRooms(TETile[][] world) {
//        Room.CenterComparator cc = new Room.CenterComparator();
        Room.CenterXComparator cxc = new Room.CenterXComparator();
        roomList.sort(cxc);

        for (int r = 1; r < roomList.size(); r++) {
            Room pastRoom = roomList.get(r - 1);
            Room currentRoom = roomList.get(r);

            int[] pastCenter = Room.findRoomCenter(pastRoom);
            int[] currentCenter = Room.findRoomCenter(currentRoom);

            connectWithHallway(pastCenter[0],
                    pastCenter[1],
                    currentCenter[0],
                    currentCenter[1],
                    world);
        }
    }

    private static void connectWithHallway(int x1, int y1, int x2, int y2, TETile[][] world) {
        /* creates a hallway to connecting the x coordinates */
        int xDiff = x1 - x2;
        int xNormalize = xDiff / Math.max(Math.abs(xDiff), 1);
        renderHallway(x1, y1, Math.abs(xDiff), -xNormalize, 0, world);

        /* creates a hallway to connecting the x coordinates */
        int yDiff = y1 - y2;
        int yNormalize = yDiff / Math.max(Math.abs(yDiff), 1);
        renderHallway(x2, y1, Math.abs(yDiff), 0, -yNormalize, world);
    }



    private static void renderWalls(TETile[][] world, Room room, int roomWidth, int roomLength) {
        /* makes top wall */
        for (int i = 0; i < roomWidth; i++) {
            placeTile(room.getTopLeftX() + i, room.getTopLeftY(), Tileset.WALL, world);
        }
        /* make bottom wall */
        for (int i = 0; i < roomWidth; i++) {
            placeTile(room.getTopLeftX() + i, room.getBottomRightY(), Tileset.WALL, world);
        }
        /* makes left wall */
        for (int i = 0; i < roomLength; i++) {
            placeTile(room.getTopLeftX(), room.getTopLeftY() - i, Tileset.WALL, world);
        }
        /* makes right wall */
        for (int i = 0; i < roomLength + 1; i++) {
            placeTile(room.getBottomRightX(), room.getTopLeftY() - i, Tileset.WALL, world);
        }
    }

    private static void renderHallway(int X,
                                      int Y,
                                      int length,
                                      int horizontally,
                                      int vertically,
                                      TETile[][] world) {

        /* creates the floor of the hallway */
        for (int i = 0; i < length; i++) {
            placeTile(X + (horizontally * i), Y + (vertically * i), Tileset.FLOOR, world);
        }

        /* creates a wall around all tiles of each floor, never overwriting a floor with a wall */
        for (int i = 0; i < length; i++) {
            placeTile(X + (horizontally * i) + 1, Y + (vertically * i), Tileset.WALL, world);
            placeTile(X + (horizontally * i) + -1, Y + (vertically * i), Tileset.WALL, world);
            placeTile(X + (horizontally * i), Y + (vertically * i) + 1, Tileset.WALL, world);
            placeTile(X + (horizontally * i), Y + (vertically * i) - 1, Tileset.WALL, world);
            placeTile(X + (horizontally * i) + 1, Y + (vertically * i) + 1, Tileset.WALL, world);
            placeTile(X + (horizontally * i) + 1, Y + (vertically * i) - 1, Tileset.WALL, world);
            placeTile(X + (horizontally * i) - 1, Y + (vertically * i) + 1, Tileset.WALL, world);
            placeTile(X + (horizontally * i) - 1, Y + (vertically * i) - 1, Tileset.WALL, world);
        }
    }


    private static void placeTile(int X, int Y, TETile tile, TETile[][] world) {
        if ((!world[X][Y].equals(Tileset.FLOOR)) || (!tile.equals(Tileset.WALL))) {
            world[X][Y] = tile;
        }
    }

    private static void placeAvatar(TETile[][] world) {
        int[] roomCenter =  Room.findRoomCenter(roomList.get(0));
        placeTile(roomCenter[0], roomCenter[1], Tileset.AVATAR, world);
    }

    public static List<Room> getRooms() {
        return roomList;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT - 4;
    }
}

