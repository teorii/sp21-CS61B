package byow.Core;

import java.util.Comparator;
import java.util.Random;

public class Room implements Comparable<Room> {
    private static final int MAXSIZE = 9;
    private static final int MINSIZE = 4;

    private final int topLeftX;
    private final int topLeftY;
    private final int bottomRightX;
    private final int bottomRightY;

    public Room(int tpX, int tlY, int brX, int brY) {
        this.topLeftX = tpX;
        this.topLeftY = tlY;
        this.bottomRightX = brX;
        this.bottomRightY = brY;
    }

    public static Room getRandomRoom(Random randomGenerator) {
        int randomLength = randomGenerator.nextInt(MAXSIZE - MINSIZE);
        int roomLength = randomLength + MINSIZE;

        int randomWidth = randomGenerator.nextInt(MAXSIZE - MINSIZE);
        int roomWidth = randomWidth + MINSIZE;

        int randomX = randomGenerator.nextInt(WorldGeneration.getWidth() - (1 + MAXSIZE));
        int randomY = randomGenerator.nextInt(WorldGeneration.getHeight() - (1 + MAXSIZE));

        int X = randomX;
        int Y = randomY + MAXSIZE;

        return new Room(X, Y, X + roomWidth, Y - roomLength);
    }

    public static int[] findRoomCenter(Room room) {
        int x;
        int y;
        x = room.topLeftX + ((room.bottomRightX - room.topLeftX) / 2);
        y = room.bottomRightY + ((room.topLeftY - room.bottomRightY) / 2);
        return new int[]{x, y};
    }

    public int compareTo(Room otherRoom) {
        int[] thisCenter = findRoomCenter(this);
        int[] otherCenter = findRoomCenter(otherRoom);

        int thisDist = (int) (Math.pow(thisCenter[0], 2) + Math.pow(thisCenter[1], 2));
        int otherDist = (int) (Math.pow(otherCenter[0], 2) + Math.pow(otherCenter[1], 2));

        return thisDist - otherDist;

    }


    public static class CenterComparator implements Comparator<Room> {
        public int compare(Room a, Room b) {
            return a.compareTo(b);
        }
    }

    public static class CenterXComparator implements Comparator<Room> {
        public int compare(Room a, Room b) {
            int[] thisCenter = findRoomCenter(a);
            int[] otherCenter = findRoomCenter(b);
            return thisCenter[0] - otherCenter[0];
        }
    }

    public int getTopLeftX() {
        return this.topLeftX;
    }

    public int getTopLeftY() {
        return this.topLeftY;
    }

    public int getBottomRightX() {
        return this.bottomRightX;
    }

    public int getBottomRightY() {
        return this.bottomRightY;
    }
}
