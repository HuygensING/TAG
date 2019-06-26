package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2019 HuC DI (KNAW)
 * =======
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Parts of the code (segments) will be ported from code written by Elli Bleeker
 */
public class EditGraphAligner {
    private final AbstractScorer scorer;
    private Cell[][] cells;

    EditGraphAligner(AbstractScorer scorer) {
        this.scorer = scorer;
    }

    // tokensA is x
    // tokensB is y
    public EditGraphTable align(List<TAGToken> tokensA, List<TAGToken> tokensB) {
        // init cells and scorer
        this.cells = new Cell[tokensB.size() + 1][tokensA.size() + 1];

        //TODO: move this init stuff to the table class!
        // init 0,0
        this.cells[0][0] = new Cell(Boolean.FALSE, 0, 0, null, 0);

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
                AbstractScorer.Match match = scorer.match(tokensA.get(x - 1), tokensB.get(y - 1));
                Cell upperLeft = scorer.score(x, y, this.cells[previousY][previousX], match);
                Cell left = scorer.gap(x, y, this.cells[y][previousX]);
                Cell upper = scorer.gap(x, y, this.cells[previousY][x]);
                //NOTE: performance: The creation of a List is a potential performance problem; better to do two
                //separate comparisons.
                Cell max = Collections.max(Arrays.asList(upperLeft, left, upper), Comparator.comparingInt(score -> score.globalScore));
                this.cells[y][x] = max;
            });
        });
        return new EditGraphTable(cells, tokensA, tokensB);
    }

    public List<Segment> alignAndSegment(List<TAGToken> tokensA, List<TAGToken> tokensB, SegmenterInterface contentSegmenter) {
        EditGraphTable table = this.align(tokensA, tokensB);
        return contentSegmenter.calculateSegmentation(table);
    }

}
