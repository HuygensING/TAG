package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CellTypeUnitTest {

    @Test
    public void TestScoreType() throws Exception{
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensA = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<XMLToken> tokensB = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        List<Segment> segmentsRound1 = roundOneAlignment(tokensA, tokensB);


        // System.out.println(segmentsRound1);

        Segment segmentReplaced = segmentsRound1.get(1);
        List<XMLToken> tokensAtype = segmentReplaced.tokensWa;
        List<XMLToken> tokensBtype = segmentReplaced.tokensWb;
        System.out.println(tokensAtype);
        System.out.println(tokensBtype);

        EditGraphTable table = alignmentRound2(tokensAtype, tokensBtype);
        Cell[][] cells = table.matrix;
        assertEquals(CellType.text, table.establishTypeOfCell(cells[7][2]));
        assertEquals(CellType.punctuation, table.establishTypeOfCell(cells[1][1]));
        assertEquals(CellType.mix, table.establishTypeOfCell(cells[3][2]));
    }

    private List<Segment> roundOneAlignment(List<XMLToken> tokensA, List<XMLToken> tokensB) {
        // round one alignment
        AbstractScorer contentScorer = new ContentScorer();
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer);
        AlignedNonAlignedSegmenter contentSegmenter = new AlignedNonAlignedSegmenter();
        return contentAligner.alignAndSegment(tokensA, tokensB, contentSegmenter);
    }

    private EditGraphTable alignmentRound2(List<XMLToken> tokensAtype, List<XMLToken> tokensBtype) {
        AbstractScorer scoreType = new TypeScorer();
        EditGraphAligner typeAligner = new EditGraphAligner(scoreType);
        return typeAligner.align(tokensAtype, tokensBtype);
    }
}

