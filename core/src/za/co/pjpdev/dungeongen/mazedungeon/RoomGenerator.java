package za.co.pjpdev.dungeongen.mazedungeon;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class RoomGenerator {

    enum Tiles {
        WALL,
        ROOM,
        START,
        BOSS
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    private int width;
    private int height;

    private int minRooms = 5;
    private int maxRooms = 20;

    Tiles mapGrid[][];
    ArrayList<Room> rooms;

    public RoomGenerator(int width, int height) {
        this.width = width;
        this.height = height;

        mapGrid = new int[width][height];
        rooms = new ArrayList<Room>();
    }

    public void generate() {

        /*-------------------------------------------
          Start to generate map
         -------------------------------------------*/


        // Fill map with walls.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                fill(new VecPoint(x, y));
            }
        }
    }

    public void render(ShapeRenderer renderer) {
        //--
    }

    private void addRoom(int width, int height) {
        //--
    }

    private void fill(VecPoint pos) {
        mapGrid[pos.x][pos.y] = Tiles.WALL;
    }

    private void fill(int x, int y) {
        fill(new VecPoint(x, y));
    }

    private void carve(VecPoint pos) {
        mapGrid[pos.x][pos.y] = Tiles.ROOM;
    }

    private void carve(int x, int y) {
        carve(new VecPoint(x, y));
    }
}
