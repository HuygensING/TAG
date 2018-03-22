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
//        // pointer is set in lower right corner at "lastCell"
//        int lastY = editTable.rows() - 1; // why the minus one?
//        int lastX = editTable.columns() - 1; // why the minus one?
//        Cell lastCell = editTable[lastY][lastX];
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            Cell currentCell = iterateTable.next();
            int x = currentCell.x;
            int y = currentCell.y;
            // stateChange if the type of the lastCell is not the same as the currentCell
//            Boolean stateChange = lastCell.match != currentCell.match;
//            if (stateChange) {
//                System.out.println(lastCell.match + ", " + currentCell.match);
//                addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY, superwitness);
                // System.out.println(String.format("%d %d %d %d", lastX, lastY, x, y));
                // change the pointer
//                lastY = y;
//                lastX = x;
//                lastCell = editTable[lastY][lastX];
            }
//        }
        // process the final cell in de EditGraphTable (additions/omissions at the beginning of the witnesses
//        Cell currentCell = editTable[0][0];
//        addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY, superwitness);
        // System.out.println(String.format("%d %d %d %d", lastX, lastY, 0, 0));
        return superwitness;
    }

}
