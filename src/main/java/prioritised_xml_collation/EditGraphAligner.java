package prioritised_xml_collation;

import java.util.*;
import java.util.stream.IntStream;

import static prioritised_xml_collation.EditGraphAligner.Score.Type.empty;
import static prioritised_xml_collation.EditGraphAligner.Score.Type.match;
import static prioritised_xml_collation.EditGraphAligner.Score.Type.mismatch;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Parts of the code (Score) written by Bram Buitendijk (for the Subst case).
 * Parts of the code (segments) will be ported from code written by Elli Bleeker
 */
public class EditGraphAligner {
    private final AbstractScorer scorer;
    private Score[][] cells;

    public EditGraphAligner(AbstractScorer scorer) {
        this.scorer = scorer;
    }
    // tokensA is x
    // tokensB is y
    public List<Segment> align(List<XMLToken> tokensA, List<XMLToken> tokensB) {
        // init cells and scorer
        this.cells = new Score[tokensB.size()+1 ][tokensA.size()+1 ];

        // init 0,0
        this.cells[0][0] = new Score(Score.Type.empty, 0, 0, null, 0);

        // fill the first row with gaps
        IntStream.range(1, tokensA.size()+1).forEach(x -> {
            int previousX = x - 1;
            this.cells[0][x] = scorer.gap(x, 0, this.cells[0][previousX]);
        });

        // fill the first column with gaps
        IntStream.range(1, tokensB.size()+1).forEach(y -> {
            int previousY = y - 1;
            this.cells[y][0] = scorer.gap(0, y, this.cells[previousY][0]);
        });

        // fill the remaining cells
        // fill the rest of the cells in a  y by x fashion
        IntStream.range(1, tokensB.size()+1).forEach(y -> {
            IntStream.range(1, tokensA.size()+1).forEach(x -> {
                int previousY = y-1;
                int previousX = x-1;
                boolean match = scorer.match(tokensA.get(x-1), tokensB.get(y-1));
                Score upperLeft = scorer.score(x, y, this.cells[previousY][previousX], match);
                Score left = scorer.gap(x, y, this.cells[y][previousX]);
                Score upper = scorer.gap(x, y, this.cells[previousY][x]);
                //NOTE: performance: The creation of a List is a potential performance problem; better to do two
                //separate comparisons.
                Score max = Collections.max(Arrays.asList(upperLeft, left, upper), (score, other) -> score.globalScore - other.globalScore);
                this.cells[y][x] = max;
            });
        });
        return calculateAlignment(tokensA, tokensB);
    }

    public static class Score {

        public Type type;
        public Score parent;
        public int globalScore = 0;
        int x;
        int y;
        int previousX;
        int previousY;
        public Score(Type type, int x, int y, Score parent, int i) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent == null ? 0 : parent.x;
            this.previousY = parent == null ? 0 : parent.y;
            this.globalScore = i;
        }

        public Score(Type type, int x, int y, Score parent) {
            this.type = type;
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
            match, mismatch, addition, deletion, empty
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

    private List<Segment> calculateAlignment(List<XMLToken>tokensA, List<XMLToken> tokensB) {
        Score[][] editTable = this.cells;
        // ScoreIterator iterates cells:
        ScoreIterator iterateTable = new ScoreIterator(editTable);
        int lastY = editTable.length - 1;
        int lastX = editTable[0].length -1;
        Score lastCell = editTable[lastY][lastX];
        while (iterateTable.hasNext()) {
            Score currentCell = iterateTable.next();
            int x = currentCell.x;
            int y = currentCell.y;
            Boolean editOperation = lastCell.type != currentCell.type || !iterateTable.hasNext();
            if (editOperation) {
                addCelltoSuperwitness(currentCell, tokensA, tokensB, lastX, lastY);
                System.out.println(String.format("%d %d %d %d", lastX, x, lastY, y));
                lastY = y;
                lastX = x;
                lastCell = editTable[lastY][lastX];
            }
        }
        return null;
    }

    private List<Segment> addCelltoSuperwitness(Score currentCell, List<XMLToken>tokensA, List<XMLToken>tokensB, int lastX, int lastY) {
        List<Segment> superwitness = new ArrayList<>();
        int x = currentCell.x;
        int y = currentCell.y;
        List<XMLToken> segmentTokensA = tokensA.subList(x, lastX);
        List<XMLToken> segmentTokensB = tokensB.subList(y, lastY);
        if (currentCell.type == match) {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.match);
            if (!segmentTokensA.isEmpty() && !segmentTokensB.isEmpty()) {
                superwitness.add(0, segment);
            }
        }
        if (currentCell.type == mismatch) {
            Segment segment = new Segment(segmentTokensA, segmentTokensB, Score.Type.mismatch);
            superwitness.add(0, segment);
        }
 //       if (currentCell.type == )

        return null;

    }
}
