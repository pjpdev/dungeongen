package za.co.pjpdev.dungeongen.mazedungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class RoomGenerator {

    enum Tiles {
        VOID,
        WALL,
        FLOOR
    }

    enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    //public static Tiles

    private int width;
    private int height;

    private int minRooms = 5;
    private int maxRooms = 20;

    private int tileSize = 5;
    private float mapX = 0;
    private float mapY = 0;

    private Tiles mapGrid[][];
    private ArrayList<Room> rooms;



    public RoomGenerator(int width, int height) {
        this.width = width;
        this.height = height;

        mapGrid = new Tiles[width][height];
        rooms = new ArrayList<Room>();
    }

    public void generate() {

        /*-------------------------------------------
          Start to generate map
         -------------------------------------------*/


        // Fill map with walls.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                mapGrid[x][y] = Tiles.VOID;
            }
        }

        int startX = width / 2;
        int startY = height / 2;
        System.out.println(startX);
        Room derp = new Room(startX, startY, 5, 5);
        derp.doors.add(new VecPoint(2, 0));
        derp.doors.add(new VecPoint(0, 2));
        rooms.add(derp);

    }

    public void render(ShapeRenderer renderer) {
        /* RENDER MAP */

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        /*for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (mapGrid[x][y] == Tiles.VOID) {
                    renderer.setColor(Color.BLACK);
                } else if (mapGrid[x][y] == Tiles.WALL) {
                    renderer.setColor(Color.BLUE);
                } else if (mapGrid[x][y] == Tiles.FLOOR) {
                    renderer.setColor(Color.NAVY);
                }

                float drawX = mapX + (tileSize * x);
                float drawY = mapY + (tileSize * y);

                //if ((drawX >= -15) && (drawX <= container.getScreenHeight()+15) && (drawY >= -15) && (drawY <= container.getScreenHeight()+15)) {
                renderer.rect(drawX, drawY, tileSize, tileSize);
                //}
            }
        }*/

        for (Room room : rooms) {
            room.render(renderer);
        }
        renderer.end();
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
        mapGrid[pos.x][pos.y] = Tiles.FLOOR;
    }

    private void carve(int x, int y) {
        carve(new VecPoint(x, y));
    }
}
