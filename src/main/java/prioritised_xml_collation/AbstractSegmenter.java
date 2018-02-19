package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSegmenter implements SegmenterInterface {
    public List<Segment> calculateSegmentation(Score[][] editTable, List<XMLToken> tokensA, List<XMLToken> tokensB) {
        ArrayList<Segment> superwitness = new ArrayList<>();
        // ScoreIterator iterates cells:
        ScoreIterator iterateTable = new ScoreIterator(editTable);
        // pointer is set in lower right corner at "lastCell"
        int lastY = editTable.length - 1;
        int lastX = editTable[0].length - 1;
        Score lastCell = editTable[lastY][lastX];
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            Score currentCell = iterateTable.next();
            int x = currentCell.x;
            int y = currentCell.y;
            // stateChange if the type of the lastCell is not the same as the currentCell
            Boolean stateChange = lastCell.match != currentCell.match;
            if (stateChange) {
                System.out.println(lastCell.match + ", " + currentCell.match);
                addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY, superwitness);
                // System.out.println(String.format("%d %d %d %d", lastX, lastY, x, y));
                // change the pointer
                lastY = y;
                lastX = x;
                lastCell = editTable[lastY][lastX];
            }
        }
        // process the final cell in de EditGraphTable (additions/omissions at the beginning of the witnesses
        Score currentCell = editTable[0][0];
        addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY, superwitness);
        // System.out.println(String.format("%d %d %d %d", lastX, lastY, 0, 0));
        return superwitness;
    }

    private void addCelltoSuperwitness(Score currentCell, List<XMLToken> tokensA, List<XMLToken> tokensB, int lastX, int lastY, List<Segment> superwitness) {
        int x = currentCell.x;
        int y = currentCell.y;
        List<XMLToken> segmentTokensA = tokensA.subList(x, lastX);
        List<XMLToken> segmentTokensB = tokensB.subList(y, lastY);
        // if currentCell has tokens of type "match", lastcell is replacement (because stateChange)
        if (currentCell.match == Boolean.TRUE) {
            // if cell contains tokens from both witnesses its a replacement
            if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.replacement);
                // insert the segment to the list at the first position (position "0")
                superwitness.add(0, segment);
            }
            // addition: no TokensA
            else if (segmentTokensA.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.addition);
                superwitness.add(0, segment);
            }
            // omission: no TokensB
            else if (segmentTokensB.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.omission);
                superwitness.add(0, segment);
            }
        }
        // aligned
        else {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.aligned);
            superwitness.add(0, segment);
        }
    }
}
