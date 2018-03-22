package prioritised_xml_collation;

import java.util.Iterator;
import java.util.List;

/*
 * Created by Ronald Haentjens Dekker
 * 22/03/2018
 * Contains the cells
 * and the list of tokens for witness A and B
 * Provides iterator; that code originally written by Elli Bleeker
 */
public class EditGraphTable implements Iterable<Cell> {
    private Cell[][] matrix;
    List<XMLToken> tokensA;
    List<XMLToken> tokensB;

    public EditGraphTable(Cell[][] cells, List<XMLToken> tokensA, List<XMLToken> tokensB) {
        this.matrix = cells;
        this.tokensA = tokensA;
        this.tokensB = tokensB;
    }

    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            private int x, y;

            {
                this.x = matrix[0].length - 1;
                this.y = matrix.length - 1;
            }

            @Override
            public boolean hasNext() {
                return !(this.x == -1 && this.y == -1);
            }

            @Override
            public Cell next() {
                Cell currentCell = matrix[this.y][this.x];
                this.x = currentCell.previousX;
                this.y = currentCell.previousY;
                return currentCell;
            }
        };
    }
}
