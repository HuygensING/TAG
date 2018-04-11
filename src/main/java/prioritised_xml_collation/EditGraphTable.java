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

    String cellToString(Cell cell) {
        if (cell.isRoot()) {
            return "root";
        }
        if (cell.movedVertical()) {
            // get the type from one side
            XMLToken tokenB = tokensB.get(cell.y - 1);
            return tokenB.toString();
        } else if (cell.movedHorizontal()) {
            // get the type from one side
            XMLToken tokenA = tokensA.get(cell.x - 1);
            return tokenA.toString();
        }
        if (cell.match) {
            // get the type from one side
            XMLToken tokenA = tokensA.get(cell.x - 1);
            return tokenA.toString()+" (aligned)";
        }

        XMLToken tokenA = tokensA.get(cell.x - 1);
        XMLToken tokenB = tokensB.get(cell.y - 1);
        return tokenA + " : " + tokenB;
    }

    //TODO: merge establishTypeOfCell and determineUniqueCellType!
    CellType establishTypeOfCell(Cell cell) {
        if (cell.isRoot()) {
            return CellType.root;
        }
        if (cell.x == 0) {
            XMLToken tokenB = tokensB.get(cell.y - 1);
            return convertTokenTypeIntoCellType(determineTypeOfToken(tokenB));
        }
        if (cell.y == 0) {
            XMLToken tokenA = tokensA.get(cell.x - 1);
            return convertTokenTypeIntoCellType(determineTypeOfToken(tokenA));
        }
        XMLToken tokenA = tokensA.get(cell.x - 1);
        XMLToken tokenB = tokensB.get(cell.y - 1);
        Token.Type typeTokenA = determineTypeOfToken(tokenA);
        Token.Type typeTokenB = determineTypeOfToken(tokenB);

        if (typeTokenA == typeTokenB) {
            return convertTokenTypeIntoCellType(typeTokenA);
        } else {
            return CellType.mix;
        }
    }

    //TODO: move to XMLToken class and Tokeniser
    static Token.Type determineTypeOfToken(XMLToken token) {
        boolean punctuationType = (token.content.matches("\\W+"));
        boolean contentType = (token.content.matches("\\w+") && token instanceof TextToken);
        boolean markupType = (token instanceof ElementToken);
        if (punctuationType) {
            return Token.Type.punctuation;
        } else if (contentType) {
            return Token.Type.text;
        } else if (markupType) {
            return Token.Type.markup;
        }
        throw new RuntimeException("Unknown token type!");
    }


    //TODO: Change order around! First look at move!
    CellType determineUniqueCellType(Cell cell) {
        CellType type = establishTypeOfCell(cell);
        if (type == CellType.mix) {
            if (cell.movedVertical()) {
                // get the type from one side
                XMLToken tokenB = tokensB.get(cell.y - 1);
                return convertTokenTypeIntoCellType(determineTypeOfToken(tokenB));
            } else if (cell.movedHorizontal()) {
                // get the type from one side
                XMLToken tokenA = tokensA.get(cell.x - 1);
                return convertTokenTypeIntoCellType(determineTypeOfToken(tokenA));
            } else {
                // We have a replacement of a text type with markup or something similar and there is no way we can
                // resolve that by looking at neighbours.
                return CellType.mix;
            }
        }
        return type;
    }

    private CellType convertTokenTypeIntoCellType(Token.Type type) {
        if (type == Token.Type.markup) {
            return CellType.markup;
        }
        if (type == Token.Type.punctuation) {
            return CellType.punctuation;
        }
        if (type == Token.Type.text) {
            return CellType.text;
        }
        throw new RuntimeException("Unknown Token.Type!");
    }
}
