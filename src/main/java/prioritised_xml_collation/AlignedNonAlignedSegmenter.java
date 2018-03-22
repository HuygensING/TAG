package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlignedNonAlignedSegmenter extends AbstractSegmenter {
    public List<Segment> calculateSegmentation(EditGraphTable editTable) {
        ArrayList<Segment> superwitness = new ArrayList<>();
        // We set last cell to the first iterable cell. (lower right corner)
        Cell lastCell = editTable.iterator().next();
        Cell currentCell = null;
        // CellIterator iterates cells:
        Iterator<Cell> iterateTable = editTable.iterator();
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            currentCell = iterateTable.next();
            // stateChange if the type of the lastCell is not the same as the currentCell
            Boolean stateChange = lastCell.match != currentCell.match;
            if (stateChange) {
                addCelltoSuperwitness(currentCell, lastCell, superwitness, editTable);
                // change the pointer
                lastCell = currentCell;
            }
        }
        // process the final cell in de EditGraphTable (additions/omissions at the beginning of the witnesses)
        if (lastCell!=currentCell) {
            addCelltoSuperwitness(currentCell, lastCell, superwitness, editTable);
        }
        return superwitness;
    }

}
