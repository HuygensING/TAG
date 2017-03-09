package prioritised_xml_collation;

import java.util.*;
import java.util.stream.IntStream;

import static prioritised_xml_collation.EditGraphAligner.Score.Type.*;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Parts of the code (Score) written by Bram Buitendijk (for the Subst case).
 * Parts of the code (segments) will be ported from code written by Elli Bleeker
 */
public class EditGraphAligner {
    private final AbstractScorer scorer;
    private Score[][] cells;
    private List<Segment> superwitness;

    public EditGraphAligner(AbstractScorer scorer) {
        this.scorer = scorer;
    }

    // tokensA is x
    // tokensB is y
    public List<Segment> align(List<XMLToken> tokensA, List<XMLToken> tokensB) {
        // init cells and scorer
        this.cells = new Score[tokensB.size() + 1][tokensA.size() + 1];

        // init 0,0
        this.cells[0][0] = new Score(Boolean.FALSE, 0, 0, null, 0);

        // fill the first row with gaps
        IntStream.range(1, tokensA.size() + 1).forEach(x -> {
            int previousX = x - 1;
            this.cells[0][x] = scorer.gap(x, 0, this.cells[0][previousX]);
        });

        // fill the first column with gaps
        IntStream.range(1, tokensB.size() + 1).forEach(y -> {
            int previousY = y - 1;
            this.cells[y][0] = scorer.gap(0, y, this.cells[previousY][0]);
        });

        // fill the remaining cells
        // fill the rest of the cells in a  y by x fashion
        IntStream.range(1, tokensB.size() + 1).forEach(y -> {
            IntStream.range(1, tokensA.size() + 1).forEach(x -> {
                int previousY = y - 1;
                int previousX = x - 1;
                boolean match = scorer.match(tokensA.get(x - 1), tokensB.get(y - 1));
                Score upperLeft = scorer.score(x, y, this.cells[previousY][previousX], match);
                Score left = scorer.gap(x, y, this.cells[y][previousX]);
                Score upper = scorer.gap(x, y, this.cells[previousY][x]);
                //NOTE: performance: The creation of a List is a potential performance problem; better to do two
                //separate comparisons.
                Score max = Collections.max(Arrays.asList(upperLeft, left, upper), (score, other) -> score.globalScore - other.globalScore);
                this.cells[y][x] = max;
            });
        });
        calculateAlignment(tokensA, tokensB);
        return superwitness;
    }

    public static class Score {

        public Score parent;
        public int globalScore = 0;
        int x;
        int y;
        int previousX;
        int previousY;
        Boolean match;

        public Score(Boolean match, int x, int y, Score parent, int i) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent == null ? 0 : parent.x;
            this.previousY = parent == null ? 0 : parent.y;
            this.globalScore = i;
        }

        public Score(Boolean match, int x, int y, Score parent) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent.x;
            this.previousY = parent.y;
            this.globalScore = parent.globalScore;
        }

        public int getGlobalScore() {
            return this.globalScore;
        }

        public void setGlobalScore(int globalScore) {
            this.globalScore = globalScore;
        }

        @Override
        public String toString() {
            return "[" + this.y + "," + this.x + "]:" + this.globalScore;
        }

        public static enum Type {
            aligned, replacement, addition, omission, empty
        }
    }

    private static class ScoreIterator implements Iterator<Score> {
        Integer y;
        Integer x;
        private Score[][] matrix;

        ScoreIterator(Score[][] matrix) {
            this.matrix = matrix;
            this.x = matrix[0].length - 1;
            this.y = matrix.length - 1;
        }

        @Override
        public boolean hasNext() {
            return !(this.x == 0 && this.y == 0);
        }

        @Override
        public Score next() {
            Score currentScore = this.matrix[this.y][this.x];
            this.x = currentScore.previousX;
            this.y = currentScore.previousY;
            return currentScore;
        }
    }

    private void calculateAlignment(List<XMLToken> tokensA, List<XMLToken> tokensB) {
        this.superwitness = new ArrayList<>();
        Score[][] editTable = this.cells;
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
        Score currentCell = editTable[0][0];
        addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY);
        // System.out.println(String.format("%d %d %d %d", lastX, lastY, 0, 0));
    }

    private void addCelltoSuperwitness(Score currentCell, List<XMLToken> tokensA, List<XMLToken> tokensB, int lastX, int lastY) {
        int x = currentCell.x;
        int y = currentCell.y;
        List<Segment> superwitness = this.superwitness;
        List<XMLToken> segmentTokensA = tokensA.subList(x, lastX);
        List<XMLToken> segmentTokensB = tokensB.subList(y, lastY);
        // if currentCell has tokens of type "aligned", lastcell is replacement (because stateChange)
        if (currentCell.match == Boolean.TRUE) {
            // if cell contains tokens from both witnesses its a replacement/replacement
            if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
                Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.replacement);
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
