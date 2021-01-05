package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2021 HuC DI (KNAW)
 * =======
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
    private List<TAGToken> tokensA;
    private List<TAGToken> tokensB;

    EditGraphTable(Cell[][] cells, List<TAGToken> tokensA, List<TAGToken> tokensB) {
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
        List<TAGToken> segmentTokensA = tokensA.subList(currentCell.x, lastCell.x);
        List<TAGToken> segmentTokensB = tokensB.subList(currentCell.y, lastCell.y);
        // if cell contains tokens from both witnesses its a replacement or a match
        if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
            // if currentCell has tokens of type "match", look at lastcell
            // if lastCell is addition/omission/replacement stateChange occurred and a new segment can be made
            if (lastCell.match == Boolean.FALSE) {
              return new Segment(segmentTokensA, segmentTokensB, Segment.Type.replacement);
            } else {
              return new Segment(segmentTokensA, segmentTokensB, Segment.Type.aligned);
            }
        }
        // addition: no TokensA
        else if (segmentTokensA.isEmpty()) {
          return new Segment(segmentTokensA, segmentTokensB, Segment.Type.addition);
        }
        // it's an omission: no TokensB
        // if last cell is not a match/addition/replacement it is an omission
        // this condition is always true, but these lines are kept for reasons of completeness
        else {
          return new Segment(segmentTokensA, segmentTokensB, Segment.Type.omission);
        }
    }

    String cellToString(Cell cell) {
        if (cell.isRoot()) {
            return "root";
        }
        if (cell.movedVertical()) {
            // get the type from one side
            TAGToken tokenB = tokensB.get(cell.y - 1);
            return tokenB.toString();
        } else if (cell.movedHorizontal()) {
            // get the type from one side
            TAGToken tokenA = tokensA.get(cell.x - 1);
            return tokenA.toString();
        }
        if (cell.match) {
            // get the type from one side
            TAGToken tokenA = tokensA.get(cell.x - 1);
            return tokenA.toString()+" (aligned)";
        }

        TAGToken tokenA = tokensA.get(cell.x - 1);
        TAGToken tokenB = tokensB.get(cell.y - 1);
        return tokenA + " : " + tokenB;
    }

    CellType determineCellType(Cell cell) {
        if (cell.isRoot()) {
            return CellType.root;
        }
        if (cell.movedVertical()) {
            // get the type from one side
            TAGToken tokenB = tokensB.get(cell.y - 1);
            return convertTokenTypeIntoCellType(tokenB.getType());
        } else if (cell.movedHorizontal()) {
            // get the type from one side
            TAGToken tokenA = tokensA.get(cell.x - 1);
            return convertTokenTypeIntoCellType(tokenA.getType());
        } else {
            TAGToken tokenA = tokensA.get(cell.x - 1);
            TAGToken tokenB = tokensB.get(cell.y - 1);
            Token.Type typeTokenA = tokenA.getType();
            Token.Type typeTokenB = tokenB.getType();
            if (typeTokenA == typeTokenB) {
                return convertTokenTypeIntoCellType(typeTokenA);
            }
            // We have a replacement of a text type with markup or something similar and there is no way we can
            // resolve that by looking at neighbours.
            return CellType.mix;
        }
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

    public EditOperation createEditOperationOfCells(Cell lastCell, Cell currentCell) {
        List<TAGToken> editOperationTokensA = tokensA.subList(currentCell.x, lastCell.x);
        List<TAGToken> editOperationTokensB = tokensB.subList(currentCell.y, lastCell.y);
        EditOperation.Type type = determineTypeOfEditOperation(lastCell, editOperationTokensA, editOperationTokensB);
        EditOperation newE = new EditOperation(editOperationTokensA, editOperationTokensB, type);
        return newE;

    }

    private EditOperation.Type determineTypeOfEditOperation(Cell lastCell, List<TAGToken> editOperationTokensA, List<TAGToken> editOperationTokensB) {
        // if cell contains tokens from both witnesses its a replacement or a match
        if (!editOperationTokensA.isEmpty() && !editOperationTokensB.isEmpty()) {
            // if currentCell has tokens of type "match", look at lastcell
            // if lastCell is addition/omission/replacement stateChange occurred and a new segment can be made
            if (lastCell.match == Boolean.FALSE) {
                return EditOperation.Type.replacement;
            } else {
                throw new RuntimeException("There is no edit operation here! This creation call should never have been done!");
            }
        }
        // addition: no TokensA
        else if (editOperationTokensA.isEmpty()) {
            return EditOperation.Type.addition;
        }
        // it's an omission: no TokensB
        // if last cell is not a match/addition/replacement it is an omission
        // this condition is always true, but these lines are kept for reasons of completeness
        else {
            return EditOperation.Type.omission;
        }
    }
}
