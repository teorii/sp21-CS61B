package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Player {
    private int x;
    private int y;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(TETile[][] world, int horizontally, int vertically) {
        int newX = x + horizontally;
        int newY = y + vertically;
        if (world[newX][newY].equals(Tileset.FLOOR)) {
            world[x][y] = Tileset.FLOOR;
            world[newX][newY] = Tileset.AVATAR;
            x = newX;
            y = newY;
        }
    }
}
