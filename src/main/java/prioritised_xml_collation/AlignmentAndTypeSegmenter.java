package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * AlignmentAndTypeSegmenter
 * The goal is two firs separate on Aligned / non-aligned.
 * If content is non-aligned we also separate on type.
 *
 * Created by Ronald Haentjens Dekker
 * on 09/04/2018
 * Work In Progress
 *
 */
public class AlignmentAndTypeSegmenter implements SegmenterInterface {
    @Override
    public List<Segment> calculateSegmentation(EditGraphTable editTable) {
        List<Segment> result = new ArrayList<>();
        // We set last cell to the first iterable cell. (lower right corner)
        Cell lastCell = editTable.iterator().next();
        // CellIterator iterates cells:
        Iterator<Cell> iterateTable = editTable.iterator();
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            Cell currentCell = iterateTable.next();
            // stateChange if the type of the lastCell is not the same as the currentCell
            // TODO: a is root function would be faster
            Boolean stateChange = editTable.establishTypeOfCell(currentCell) == CellType.root || lastCell.match != currentCell.match;
            if (stateChange) {
                Segment newSegment = editTable.createSegmentOfCells(currentCell, lastCell);
                result.add(0, newSegment);
                lastCell = currentCell;
            }
        }
        return result;
    }
}
