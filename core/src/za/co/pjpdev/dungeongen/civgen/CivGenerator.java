package za.co.pjpdev.dungeongen.civgen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class CivGenerator {

    private int mapWidth;
    private int mapHeight;

    private int mapData[][];
    private int chunkData[][];

    private int landmassModifier;

    private int tilesUsed;

    private Random rnd;

    private int tileSize = 10;
    private float mapX = 0;
    private float mapY = 0;

    public CivGenerator(int width, int height, int landmassModifier) {
        // Create object
        this.mapWidth = width;
        this.mapHeight = height;

        this.mapData = new int[width][height];
        this.chunkData = new int[width][height];

        this.landmassModifier = landmassModifier;

        this.rnd = new Random();
    }

    public void render(ShapeRenderer renderer) {
        /*--------------------------------------------
          Render map
         ---------------------------------------------*/

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (mapData[x][y] == 0) {
                    renderer.setColor(Color.BLUE);
                } else if (mapData[x][y] > 0) {
                    renderer.setColor(Color.GREEN);
                }

                float drawX = mapX + (tileSize * x);
                float drawY = mapY + (tileSize * y);

                //if ((drawX >= -15) && (drawX <= container.getScreenHeight()+15) && (drawY >= -15) && (drawY <= container.getScreenHeight()+15)) {
                renderer.rect(drawX, drawY, tileSize, tileSize);
                //}
            }
        }
        renderer.end();
    }

    public void generate() {
        /*--------------------------------------------
          Start generating map
         ---------------------------------------------*/

        // Clear mapData
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                mapData[x][y] = 0;
            }
        }

        // Set basic tile accounting parameters
        tilesUsed = 0;
        int maxTiles = (landmassModifier + 2) + 320;

        // Start generating chunks.
        while (tilesUsed < maxTiles) {
            generateChunk();
            mergeChunk();
            tilesUsed = calculateTiles();
        }
    }

    private void generateChunk() {
        /*--------------------------------------------
          Generate a single chunk
         ---------------------------------------------*/

        // Clear chunk data.
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                chunkData[x][y] = 0;
            }
        }

        // Set basic parameters
        int posX = 5 + rnd.nextInt(mapWidth - 5);
        int posY = 5 + rnd.nextInt(mapHeight - 5);
        int length = rnd.nextInt(64) + 1;

        // Start generating chunk
        while (length > 0) {
            // Fill tiles with land
            chunkData[posX][posY] = 15;
            chunkData[posX+1][posY] = 15;
            chunkData[posX][posY+1] = 15;

            // Select next direction
            int dir = rnd.nextInt(4); // 0 = N, 1 = E, 2 = S, 3 = W
            switch (dir) {
                case 0: {
                    posY += 1;
                    break;
                }
                case 1: {
                    posX += 1;
                    break;
                }
                case 2: {
                    posY -= 1;
                    break;
                }
                case 3: {
                    posX -= 1;
                }
            }

            // Check bounds.
            if ((posX < 5) || (posX > mapWidth-5) || (posY < 5) || (posY > mapHeight-5)) {
                length = 0;
            } else {
                length -= 1;
            }
        }

    }

    private void mergeChunk() {
        /*--------------------------------------------
          Merge chunk into map
         ---------------------------------------------*/

        for (int x = 0; x < mapWidth; x++){
            for (int y = 0; y < mapHeight; y++) {
                mapData[x][y] += chunkData[x][y];
            }
        }
    }

    private int calculateTiles() {
        /*--------------------------------------------
          Calculate tile count
         ---------------------------------------------*/

        int tempCount = 0;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if (mapData[x][y] > 0) tempCount += 1;
            }
        }

        return tempCount;
    }
}
