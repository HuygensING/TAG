package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlignedNonAlignedSegmenter implements SegmenterInterface {
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

    private void addCelltoSuperwitness(Cell currentCell, Cell lastCell, List<Segment> superwitness, EditGraphTable table) {
        List<XMLToken> segmentTokensA = table.tokensA.subList(currentCell.x, lastCell.x);
        List<XMLToken> segmentTokensB = table.tokensB.subList(currentCell.y, lastCell.y);
        // if cell contains tokens from both witnesses its a replacement or a match
        if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
            // if currentCell has tokens of type "match", look at lastcell
            // if lastCell is addition/omission/replacement stateChange occured and a new segment can be made
            if (lastCell.match == Boolean.FALSE) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.replacement);
                // insert the segment to the superwitness list at the first position (position "0")
                superwitness.add(0, segment);
            } else {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.aligned);
                superwitness.add(0, segment);
            }
        }
        // addition: no TokensA
        else if (segmentTokensA.isEmpty()) {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.addition);
            superwitness.add(0, segment);
        }
        // it's an omission: no TokensB
        // if last cell is not a match/addition/replacement it is an omission
        // this condition is always true, but these lines are kept for reasons of completeness
        else {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.omission);
            superwitness.add(0, segment);
        }
    }
}
