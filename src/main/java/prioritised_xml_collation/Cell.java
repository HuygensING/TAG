package prioritised_xml_collation;

/**
 * Created by ellibleeker on 06/04/2017.
 */
public class Cell {
        private Cell parent;
        public int globalScore = 0;
        int x;
        int y;
        int previousX;
        int previousY;
        Boolean match;

        Cell(Boolean match, int x, int y, Cell parent, int i) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent == null ? -1 : parent.x;
            this.previousY = parent == null ? -1 : parent.y;
            this.globalScore = i;
        }

        public Cell(Boolean match, int x, int y, Cell parent) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent.x;
            this.previousY = parent.y;
            this.globalScore = parent.globalScore;
        }

        @Override
        public String toString() {
            return "[" + this.y + "," + this.x + "]:" + this.globalScore;
        }

}
