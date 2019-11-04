package za.co.pjpdev.dungeongen.mazedungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

/**
 * Created by PJ.Pretorius on 27/01/2017.
 */
public class Room {

    private int x1, x2;
    private int y1, y2;
    private int w, h;

    public ArrayList<VecPoint> doors;

    public Room(int x, int y, int w, int h) {
		/* CREATE ROOM */

        this.x1 = x;
        this.x2 = x + w;

        this.y1 = y;
        this.y2 = y + h;

        this.h = h;
        this.w = w;

        doors = new ArrayList<VecPoint>();
    }

    public boolean overlaps(Room room) {
        return (x1 <= room.x2 && x2 >= room.x1 && y1 <= room.y2 && y2 >= room.y1);
    }

    public int getX() {
        return x1;
    }

    public int getY() {
        return y1;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public void render(ShapeRenderer renderer) {
        // Render room
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                renderer.setColor(Color.BLUE);
                int drawX = (x1 * 5) + (x * 5);
                int drawY = (y1 * 5) + (y * 5);
                renderer.rect(drawX, drawY, 5, 5);
            }
        }
    }
}
