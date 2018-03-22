package prioritised_xml_collation;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Parts of the code (ScoreIterator) written by Bram Buitendijk (for the Subst case).
 * Parts of the code (segments) will be ported from code written by Elli Bleeker
 */
public class EditGraphAligner {
    private final AbstractScorer scorer;
    private final SegmenterInterface segmenter;
    Score[][] cells;

    public EditGraphAligner(AbstractScorer scorer, SegmenterInterface segmenter) {
        this.scorer = scorer;
        this.segmenter = segmenter;
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
        return segmenter.calculateSegmentation(cells, tokensA, tokensB);
    }
    public ScoreType establishTypeOfCell(Score cell, List<XMLToken> tokensA, List<XMLToken> tokensB){
        XMLToken tokenA = tokensA.get(cell.x - 1);
        XMLToken tokenB = tokensB.get(cell.y - 1);
        System.out.println(tokenA);
        System.out.println(tokenB);
        boolean punctuationType = (tokenA.content.matches("\\W+") && tokenB.content.matches("\\W+"));
        boolean contentType = (tokenA.content.matches("\\w+") && tokenA instanceof TextToken && tokenB.content.matches("\\w+") && tokenB instanceof TextToken);
        boolean markupType = (tokenA instanceof ElementToken) && (tokenB instanceof ElementToken);
        if(punctuationType) {
            return ScoreType.punctuation;
        }
        else if (contentType) {
            return ScoreType.text;
        }
        else if (markupType) {
            return ScoreType.markup;
        }
        else return ScoreType.mix;
    }
}
