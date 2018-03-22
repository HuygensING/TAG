package prioritised_xml_collation;

import java.util.List;

/*
 * Abstract Segmenter
 * Created by Ronald Haentjens Dekker
 * 22/03/2018
 */
abstract class AbstractSegmenter implements SegmenterInterface {
    // TODO: move this method to the EditGrapHTable class
    // TODO: remove the remaining duplication in the return statements
    Segment createSegmentOfCells(Cell currentCell, Cell lastCell, EditGraphTable table) {
        List<XMLToken> segmentTokensA = table.tokensA.subList(currentCell.x, lastCell.x);
        List<XMLToken> segmentTokensB = table.tokensB.subList(currentCell.y, lastCell.y);
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
            return  segment;
        }
        // it's an omission: no TokensB
        // if last cell is not a match/addition/replacement it is an omission
        // this condition is always true, but these lines are kept for reasons of completeness
        else {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Segment.Type.omission);
            return segment;
        }
    }

}
