package prioritised_xml_collation;

/**
 * Created by ellibleeker on 06/04/2017.
 */
public class Cell {
    public int globalScore = 0;
    int x;
    int y;
    int previousX;
    int previousY;
    Boolean match;

    Cell(Boolean match, int x, int y, Cell parent, int globalScore) {
        this.match = match;
        this.x = x;
        this.y = y;
        this.previousX = parent == null ? -1 : parent.x;
        this.previousY = parent == null ? -1 : parent.y;
        this.globalScore = globalScore;
    }

    @Override
    public String toString() {
        return "[" + this.y + "," + this.x + "]:" + this.globalScore;
    }

    public boolean movedHorizontal() {
        int differenceHorizontal = x - previousX;
        int differenceVertical = y - previousY;
        return differenceHorizontal != 0 && differenceVertical == 0;
    }

    public boolean movedVertical() {
        int differenceHorizontal = x - previousX;
        int differenceVertical = y - previousY;
        return differenceHorizontal == 0 && differenceVertical != 0;
    }

    public Boolean isRoot() {
        return x == 0 && y == 0;
    }

}
