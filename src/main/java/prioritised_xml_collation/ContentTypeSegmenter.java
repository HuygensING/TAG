package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * ContentType Segmenter
 * Created by Ronald Haentjens Dekker
 * 22/03/2018
 *
 */
public class ContentTypeSegmenter implements SegmenterInterface {
    public List<Segment> calculateSegmentation(EditGraphTable editTable) {
        ArrayList<Segment> superwitness = new ArrayList<>();
        // CellIterator iterates cells:
        Iterator<Cell> iterateTable = editTable.iterator();
        // pointer is set in lower right corner at "lastCell"
        Cell lastCell = editTable.iterator().next();
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            Cell currentCell = iterateTable.next();
            // we change state based on type, in case of mixed that we look at the movement through the table
            if (editTable.determineUniqueCellType(currentCell) != editTable.determineUniqueCellType(lastCell)) {
                Segment newSegment = editTable.createSegmentOfCells(currentCell, lastCell);
                superwitness.add(newSegment);
                lastCell = currentCell;
            }
        }
        return superwitness;
    }
}
