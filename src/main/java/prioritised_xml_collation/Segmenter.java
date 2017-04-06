package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ellibleeker on 06/04/2017.
 */
public class Segmenter {
    private List<Segment> superwitness;

    public List<Segment> calculateSegmentation(List<XMLToken> tokensA, List<XMLToken> tokensB, EditGraphAligner.Score[][] editTable) {
        this.superwitness = new ArrayList<>();
        // ScoreIterator iterates cells:
        ScoreIterator iterateTable = new ScoreIterator(editTable);
        // pointer is set in lower right corner at "lastCell"
        int lastY = editTable.length - 1;
        int lastX = editTable[0].length - 1;
        EditGraphAligner.Score lastCell = editTable[lastY][lastX];
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            EditGraphAligner.Score currentCell = iterateTable.next();
            int x = currentCell.x;
            int y = currentCell.y;
            // stateChange if the type of the lastCell is not the same as the currentCell
            Boolean stateChange = lastCell.match != currentCell.match;
            if (stateChange) {
                System.out.println(lastCell.match+", "+currentCell.match);
                addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY);
                // System.out.println(String.format("%d %d %d %d", lastX, lastY, x, y));
                // change the pointer
                lastY = y;
                lastX = x;
                lastCell = editTable[lastY][lastX];
            }
        }
        // process the final cell in de EditGraphTable (additions/omissions at the beginning of the witnesses
        EditGraphAligner.Score currentCell = editTable[0][0];
        addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY);
        // System.out.println(String.format("%d %d %d %d", lastX, lastY, 0, 0));
        return superwitness;
    }

    private void addCelltoSuperwitness(EditGraphAligner.Score currentCell, List<XMLToken> tokensA, List<XMLToken> tokensB, int lastX, int lastY) {
        int x = currentCell.x;
        int y = currentCell.y;
        List<XMLToken> segmentTokensA = tokensA.subList(x, lastX);
        List<XMLToken> segmentTokensB = tokensB.subList(y, lastY);
        // if currentCell has tokens of type "aligned", lastcell is replacement (because stateChange)
        if (currentCell.match == Boolean.TRUE) {
            // if cell contains tokens from both witnesses its a replacement
            if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, EditGraphAligner.Score.Type.replacement);
                // insert the segment to the list at the first position (position "0")
                superwitness.add(0, segment);
            }
            // addition: no TokensA
            else if (segmentTokensA.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, EditGraphAligner.Score.Type.addition);
                superwitness.add(0, segment);
            }
            // omission: no TokensB
            else if (segmentTokensB.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, EditGraphAligner.Score.Type.omission);
                superwitness.add(0, segment);
            }
        }
        // aligned
        else {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, EditGraphAligner.Score.Type.aligned);
            superwitness.add(0, segment);
        }
    }

    public static class ScoreIterator implements Iterator<EditGraphAligner.Score> {
        Integer y;
        Integer x;
        private EditGraphAligner.Score[][] matrix;

        ScoreIterator(EditGraphAligner.Score[][] matrix) {
            this.matrix = matrix;
            this.x = matrix[0].length - 1;
            this.y = matrix.length - 1;
        }

        @Override
        public boolean hasNext() {
            return !(this.x == 0 && this.y == 0);
        }

        @Override
        public EditGraphAligner.Score next() {
            EditGraphAligner.Score currentScore = this.matrix[this.y][this.x];
            this.x = currentScore.previousX;
            this.y = currentScore.previousY;
            return currentScore;
        }
    }
}
