package za.co.pjpdev.dungeongen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

/**
 * Created by PJ.Pretorius on 27/01/2017.
 */
public class Generator {

    /* Tile Types */
    public static final int TILE_WALL = 0;
    public static final int TILE_FLOOR = 1;

    /* Direction Handler */
    public static final VecPoint DIR_NORTH = new VecPoint(0, -1);
    public static final VecPoint DIR_WEST = new VecPoint(-1, 0);
    public static final VecPoint DIR_SOUTH = new VecPoint(0, 1);
    public static final VecPoint DIR_EAST = new VecPoint(1, 0);

    /* Generator Variables */
    private int numRoomTries;
    private int extraConnectorSize = 20;
    private int roomExtraSize = 0;
    private int windingPercent = 0;

    private int minRoomSize = 3;
    private int maxRoomSize = 15;

    private int[][] tileMap;
    private int mapWidth;
    private int mapHeight;
    private int currentRegion = -1;

    private int[][] regions;

    private ArrayList<Room> rooms;
    //private ArrayList<Rectangle> rooms;

    private Random rng;

    public Generator(int mapWidth, int mapHeight, int numRooms) {
		/* CREATE GENERATOR */

        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        tileMap = new int[mapWidth][mapHeight];
        regions = new int[mapWidth][mapHeight];

        this.numRoomTries = numRooms;

        rooms = new ArrayList<Room>();
        //rooms = new ArrayList<Rectangle>();

        rng = new Random();
    }

    public void render(ShapeRenderer renderer) {
		/* RENDER MAP */

		renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (tileMap[x][y] == TILE_WALL) {
                    renderer.setColor(Color.GRAY);
                } else if (tileMap[x][y] == TILE_FLOOR) {
                    renderer.setColor(Color.GREEN);
                }

                int tileSize = 2;
                int mapX = 5;
                int mapY = 5;

                int drawX = mapX + (tileSize * x);
                int drawY = mapY + (tileSize * y);

                //if ((drawX >= -15) && (drawX <= container.getScreenHeight()+15) && (drawY >= -15) && (drawY <= container.getScreenHeight()+15)) {
                renderer.rect(drawX, drawY, tileSize, tileSize);
                //}
            }
        }
        renderer.end();
    }

    public void generate() {
		/* GENERATE DUNGEON */

        // Fill tilemap with TILE_WALL
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                tileMap[x][y] = TILE_WALL;
            }
        }

        // Generate rooms
        addRooms();

        // Generate Maze
        for (int x = 1; x < mapWidth; x += 2) {
            for (int y = 1; y < mapHeight; y += 2) {
                if (tileMap[x][y] == TILE_WALL) {
                    growMaze(new VecPoint(x, y));
                }
            }
        }

        //connectRegions();
    }

    private void addRooms() {
		/* GENERATE ROOMS */
        System.out.println(numRoomTries);

        for (int i = 0; i < numRoomTries; i++) {
            // Pick a random size.
            //int size = minRoomSize + rng.nextInt(maxRoomSize - minRoomSize + 1);
            int size = (1 + rng.nextInt(3 + roomExtraSize)) * 2 + 1;
            int rectangularity = rng.nextInt(1 + size / 2) * 2;
            int width = size;
            int height = size;
			/*if (rng.nextInt(2) == 1) {
				width += rectangularity;
			} else {
				height += rectangularity;
			}*/

            int x = rng.nextInt((mapWidth - width - 1) / 2) * 2 + 1;
            int y = rng.nextInt((mapHeight - height - 1) / 2) * 2 + 1;

            Room room = new Room(x, y, width, height);
            //Rectangle room = new Rectangle(x, y, width, height);

            boolean overlaps = false;

            for (Room r: rooms) {
                if (room.overlaps(r)) {
                    overlaps = true;
                    //System.out.println("Discarded " + room);
                    break;
                }
            }

            if (!overlaps) {
                rooms.add(room);

                startRegion();
                for (int xx = 0; xx < room.getWidth(); xx++) {
                    for (int yy = 0; yy < room.getWidth(); yy++) {
                        carve(xx + x, yy + y);
                    }
                }
            }
        }
    }

    private void growMaze(VecPoint start) {
        Vector<VecPoint> cells = new Vector<VecPoint>();

        VecPoint lastDir = new VecPoint(0, 0);

        startRegion();
        carve(start.x, start.y);

        cells.add(start);
        while (!cells.isEmpty()) {
            VecPoint cell = cells.lastElement();

            Vector<VecPoint> unmadeCells = new Vector<VecPoint>();

            if (canCarve(cell, DIR_NORTH))
                unmadeCells.add(DIR_NORTH);
            if (canCarve(cell, DIR_WEST))
                unmadeCells.add(DIR_WEST);
            if (canCarve(cell, DIR_SOUTH))
                unmadeCells.add(DIR_SOUTH);
            if (canCarve(cell, DIR_EAST))
                unmadeCells.add(DIR_EAST);

            if (!unmadeCells.isEmpty()) {
                VecPoint dir;

                if (unmadeCells.contains(lastDir) && rng.nextInt(100) > windingPercent) {
                    dir = lastDir;
                } else {
                    int index = rng.nextInt(unmadeCells.size());
                    dir = unmadeCells.get(index);
                }

                //VecPoint newCell = dir.multiply(2).add(cell);
                carve(cell.add(dir));
                carve(dir.multiply(2).add(cell));

                cells.add(dir.multiply(2).add(cell));
                lastDir = dir;
            } else {
                cells.removeElementAt(cells.size() - 1);

                lastDir = null;
            }
        }
    }

    private void connectRegions() {
		/* CONNECT THE REGIONS */
        HashMap<VecPoint, HashSet<Integer>> connectorRegions = new HashMap<VecPoint, HashSet<Integer>>();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (tileMap[x][y] != TILE_WALL) continue;

                HashSet<Integer> newRegions = new HashSet<Integer>();

                VecPoint pos = new VecPoint(x, y);

                VecPoint north = pos.add(DIR_NORTH);
                int region1 = regions[north.x][north.y];
                if (region1 != 0) newRegions.add(region1);

                VecPoint west = pos.add(DIR_WEST);
                int region2 = regions[west.x][west.y];
                if (region2 != 0) newRegions.add(region2);

                VecPoint south = pos.add(DIR_SOUTH);
                int region3 = regions[south.x][south.y];
                if (region3 != 0) newRegions.add(region3);

                VecPoint east = pos.add(DIR_EAST);
                int region4 = regions[east.x][east.y];
                if (region4 != 0) newRegions.add(region4);

                if (newRegions.size() < 2) continue;

                connectorRegions.put(pos, newRegions);
            }
        }

        //VecPoint[] connectors = connectorRegions.keySet().toArray(VecPoint);
    }

    private void startRegion() {
        currentRegion++;
    }

    private boolean canCarve(VecPoint pos, VecPoint direction) {
        //--
        //VecPoint posAdd = direction.multiply(2);
        VecPoint result = direction.multiply(2).add(pos);

        if ((result.x >= mapWidth) ||(result.x <= 0) || (result.y >= mapHeight) || (result.y <= 0)) {
            return false;
        }

        if (tileMap[result.x][result.y] == TILE_WALL) {
            //System.out.println("Can Carve: " + pos + " | " + direction);
            return true;
        } else {
            return false;
        }
    }

    private void carve(int x, int y) {
        tileMap[x][y] = TILE_FLOOR;
        regions[x][y] = currentRegion;

    }

    private void carve(VecPoint point) {
        carve(point.x, point.y);
    }

    private void fill(int x, int y) {
        tileMap[x][y] = TILE_WALL;
    }

}
