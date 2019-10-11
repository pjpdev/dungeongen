package za.co.pjpdev.dungeongen.viralreplication;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

/**
 * Created by PJ.Pretorius on 03/02/2017.
 */
public class Generator {

    private int mapWidth;
    private int mapHeight;
    private int[][] tileMap;

    private Random rng;

    private float mapX = 0;
    private float mapY = 0;
    private float tileSize = 5;

    public Generator(int width, int height) {
        // Class constructor

        mapWidth = width;
        mapHeight = height;

        tileMap = new int[mapWidth][mapHeight];

        rng = new Random();
    }

    public void render(ShapeRenderer renderer) {
        // Render tilemap

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x=0; x < mapWidth; x++) {
            for (int y=0; y < mapHeight; y++) {
                if (tileMap[x][y] > 0) {
                    renderer.setColor(Color.GREEN);
                } else {
                    renderer.setColor(Color.BLACK);
                }

                float drawX = mapX + (tileSize * x);
                float drawY = mapY + (tileSize * y);

                renderer.rect(drawX, drawY, tileSize, tileSize);
            }
        }
        renderer.end();
    }

    public void generate() {
        // Fill map with negative values.
        for (int i=0; i < mapWidth; i++) {
            for (int k=0; k < mapHeight; k++) {
                tileMap[i][k] = 0;
            }
        }

        // Generate the map

        int q = randRange(2, 254);
        int k1 = randRange(0, 100);
        int k2 = randRange(0, 9999);
        int k3 = randRange(0, 100);

        System.out.println("Q: " + q);
        System.out.println("K1: " + k1);
        System.out.println((float)k1 / 100);
        System.out.println("K2: " + k2);
        System.out.println((float)k2 / 100000);
        System.out.println("K3: " + k3);
        System.out.println((float)k3 / 100);

        int halfMap = (mapWidth * mapHeight) / 2;
        while (halfMap > 0) {
            int x = randRange(0, mapWidth-1);
            int y = randRange(0, mapHeight-1);

            if (tileMap[x][y] != q) {
                tileMap[x][y] = q;
                halfMap -= 1;
            }
        }

        int infected = 0;
        int mapSize = mapWidth * mapHeight;
        for (int i=0; i < mapSize; i++) {
            int x = randRange(0, mapWidth-1);
            int y = randRange(0, mapHeight-1);

            if (tileMap[x][y] > 0) {
                if (tileMap[x][y] == q) {
                    float k2Chance = (float) k2 / 100000;
                    float k2Value = rng.nextFloat();
                    if (k2Value <= k2Chance) {
                        infected += 1;
                        tileMap[x][y] = q-1;
                    } else {
                        //--
                    }
                } else if ((tileMap[x][y] > 1) && (tileMap[x][y] < q)) {
                    tileMap[x][y] -= 1;
                } else if (tileMap[x][y] == 1) {
                    float k1Chance = (float) k1 / 100;
                    float k1Value = rng.nextFloat();
                }
            }
        }
        System.out.println("Infected: " + infected);
    }

    public int randRange(int min, int max) {
        return rng.nextInt((max - min) + 1) + min;
    }
}
