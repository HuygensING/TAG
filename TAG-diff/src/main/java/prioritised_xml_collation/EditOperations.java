package prioritised_xml_collation;

import java.util.HashSet;
import java.util.Set;

/*
 * Created on 16/04/2018 by Ronald Haentjens Dekker
 * This code takes inspiration from the AlignmentAndTypeSegmenter.
 * Aligned stuff is ignored.
 */
public class EditOperations {
    public static Set<EditOperation> calculate(EditGraphTable editGraphTable) {
        Set<EditOperation> editOperations = new HashSet<>();
        // We set last cell to the first iterable cell. (lower right corner)
        Cell lastCell = editGraphTable.iterator().next();
        // For loop iterates cells of the editTable:
        // As long as the pointer can move up in the editTable
        // move one cell up
        for (Cell currentCell : editGraphTable) {
            // stateChange if cell is root or the alignment of the lastCell is not the same as the currentCell
            Boolean stateChange = currentCell.isRoot() || lastCell.match != currentCell.match;
            if (!stateChange) {
                // stateChange if type is different and we are within a modification.
                stateChange = !lastCell.match && editGraphTable.determineCellType(currentCell) != editGraphTable.determineCellType(lastCell);
            }
            if (stateChange) {
                // NOTE: we skip aligned segments.
                if (lastCell.match) {
                    lastCell = currentCell;
                    continue;
                }
                // create an EditOperation going from the lastCell to the currentCell
                EditOperation newEditOperation = editGraphTable.createEditOperationOfCells(lastCell, currentCell);
                System.out.println(newEditOperation);
                editOperations.add(newEditOperation);
                lastCell = currentCell;
            }
        }

        return editOperations;
    }
}
