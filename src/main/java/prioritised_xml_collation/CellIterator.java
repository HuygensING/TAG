package prioritised_xml_collation;

import java.util.Iterator;

/**
 * Created by ellibleeker on 06/04/2017.
 */
public class CellIterator implements Iterator<Cell> {
    private Integer y;
    private Integer x;
    private Cell[][] matrix;

    CellIterator(Cell[][] matrix) {
        this.matrix = matrix;
        this.x = matrix[0].length - 1;
        this.y = matrix.length - 1;
    }

    @Override
    public boolean hasNext() {
            return !(this.x == 0 && this.y == 0);
        }

    @Override
    public Cell next() {
    Cell currentCell = this.matrix[this.y][this.x];
    this.x = currentCell.previousX;
    this.y = currentCell.previousY;
    return currentCell;
    }
}