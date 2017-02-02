package prioritised_xml_collation;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Parts of the code (Score) written by Bram Buitendijk (for the Subst case).
 * Parts of the code (segments) will be ported from code written by Elli Bleeker
 */
public class EditGraphAligner {
    private final Scorer scorer;
    private Score[][] cells;

    public EditGraphAligner(Scorer scorer) {
        this.scorer = scorer;
    }
    // tokensA is x
    // tokensB is y
    public void align(List<XMLToken> tokensA, List<XMLToken> tokensB) {
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

    class Scorer {

        public boolean match(XMLToken a, XMLToken b) {
            //note: performance: whitespace normalization and matching happens over and over again.
            //note: in the production version of CollateX both these things happen before alignment.
           return a.content.trim().equals(b.content.trim());
        }

        public Score gap(int x, int y, Score parent) {
            Score.Type type = determineType(x, y, parent);
            return new Score(type, x, y, parent, parent.globalScore - 1);
        }

        public Score score(int x, int y, Score parent, boolean match) {
            if (match) {
                return new Score(Score.Type.match, x, y, parent, parent.globalScore);
            }
            return new Score(Score.Type.mismatch, x, y, parent, parent.globalScore - 2);
        }

        private Score.Type determineType(int x, int y, Score parent) {
            if (x == parent.x) {
                return Score.Type.addition;
            }
            if (y == parent.y) {
                return Score.Type.deletion;
            }
            return Score.Type.empty;
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
}
