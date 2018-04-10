package prioritised_xml_collation;

import java.util.Iterator;
import java.util.List;

/*
 * Created by Ronald Haentjens Dekker
 * 22/03/2018
 * Contains the cells
 * and the list of tokens for witness A and B
 * Provides iterator; that code originally written by Bram Buitendijk (slightly changed to include upperleft cell)
 * Provides cell type: code originally written by Elli Bleeker.
 */
public class EditGraphTable implements Iterable<Cell> {
    Cell[][] matrix;
    private List<XMLToken> tokensA;
    private List<XMLToken> tokensB;

    EditGraphTable(Cell[][] cells, List<XMLToken> tokensA, List<XMLToken> tokensB) {
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

    // TODO: remove the remaining duplication in the return statements
    // TODO: by separating out the segment type determination code.
    Segment createSegmentOfCells(Cell currentCell, Cell lastCell) {
        List<XMLToken> segmentTokensA = tokensA.subList(currentCell.x, lastCell.x);
        List<XMLToken> segmentTokensB = tokensB.subList(currentCell.y, lastCell.y);
        // if cell contains tokens from both witnesses its a replacement or a match
        if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
            // if currentCell has tokens of type "match", look at lastcell
            // if lastCell is addition/omission/replacement stateChange occured and a new segment can be made
            if (lastCell.match == Boolean.FALSE) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.replacement);
                return segment;
            } else {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.aligned);
                return segment;
            }
        }
        // addition: no TokensA
        else if (segmentTokensA.isEmpty()) {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.addition);
            return segment;
        }
        // it's an omission: no TokensB
        // if last cell is not a match/addition/replacement it is an omission
        // this condition is always true, but these lines are kept for reasons of completeness
        else {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.omission);
            return segment;
        }
    }

    private String cellToString(Cell cell) {
        XMLToken tokenA = tokensA.get(cell.x - 1);
        XMLToken tokenB = tokensB.get(cell.y - 1);
        return tokenA + " : " + tokenB;
    }

    CellType establishTypeOfCell(Cell cell) {
        if (cell.x == 0 && cell.y == 0) {
            return CellType.root;
        }
        if (cell.x == 0) {
            XMLToken tokenB = tokensB.get(cell.y - 1);
            return determineTypeOfToken(tokenB);
        }
        if (cell.y == 0) {
            XMLToken tokenA = tokensA.get(cell.x - 1);
            return determineTypeOfToken(tokenA);
        }
        XMLToken tokenA = tokensA.get(cell.x - 1);
        XMLToken tokenB = tokensB.get(cell.y - 1);
        boolean punctuationType = (tokenA.content.matches("\\W+") && tokenB.content.matches("\\W+"));
        boolean contentType = (tokenA.content.matches("\\w+") && tokenA instanceof TextToken && tokenB.content.matches("\\w+") && tokenB instanceof TextToken);
        boolean markupType = (tokenA instanceof ElementToken) && (tokenB instanceof ElementToken);
        if (punctuationType) {
            return CellType.punctuation;
        } else if (contentType) {
            return CellType.text;
        } else if (markupType) {
            return CellType.markup;
        } else return CellType.mix;
    }

    private CellType determineTypeOfToken(XMLToken tokenA) {
        boolean punctuationType = (tokenA.content.matches("\\W+"));
        boolean contentType = (tokenA.content.matches("\\w+") && tokenA instanceof TextToken);
        boolean markupType = (tokenA instanceof ElementToken);
        if (punctuationType) {
            return CellType.punctuation;
        } else if (contentType) {
            return CellType.text;
        } else if (markupType) {
            return CellType.markup;
        } else return CellType.mix;
    }

    CellType determineUniqueCellType(Cell cell) {
        CellType type = establishTypeOfCell(cell);
        if (type == CellType.mix) {
            if (cell.movedVertical()) {
                // get the type from one side
                XMLToken tokenB = tokensB.get(cell.y - 1);
                return determineTypeOfToken(tokenB);
            } else if (cell.movedHorizontal()) {
                // get the type from one side
                XMLToken tokenA = tokensA.get(cell.x - 1);
                return determineTypeOfToken(tokenA);
            } else {
                // We have a replacement of a text type with markup or something similar and there is no way we can
                // resolve that by looking at neighbours.
                return CellType.mix;
            }
        }
        return type;
    }
}
