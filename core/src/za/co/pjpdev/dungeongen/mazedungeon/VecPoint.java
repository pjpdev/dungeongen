package za.co.pjpdev.dungeongen.mazedungeon;

/**
 * Created by PJ.Pretorius on 27/01/2017.
 */
public class VecPoint {

    public int x;
    public int y;

    public VecPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public VecPoint add(VecPoint point) {
        return new VecPoint(this.x + point.x, this.y + point.y);
    }

    public VecPoint subtract(VecPoint point) {
        return new VecPoint(this.x - point.x, this.y - point.y);
    }

    public VecPoint multiply(int size) {
        return new VecPoint(this.x * size, this.y * size);
    }

}
