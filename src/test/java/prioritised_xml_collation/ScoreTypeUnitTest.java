package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScoreTypeUnitTest {

    @Test
    public void TestScoreType() throws Exception{
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensA = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<XMLToken> tokensB = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        AbstractScorer contentScorer = new ContentScorer();
        Segmenter contentSegmenter = new Segmenter();
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer, contentSegmenter);
        List<Segment> segmentsRound1 = contentAligner.align(tokensA, tokensB);
        // System.out.println(segmentsRound1);
        AbstractScorer scoreType = new TypeScorer();
        Segmenter typeSegmenter = new Segmenter();
        EditGraphAligner typeAligner = new EditGraphAligner(scoreType, typeSegmenter);
        Segment segmentReplaced = segmentsRound1.get(1);
        List<XMLToken> tokensAtype = segmentReplaced.tokensWa;
        List<XMLToken> tokensBtype = segmentReplaced.tokensWb;
        System.out.println(tokensAtype);
        System.out.println(tokensBtype);
        List<Segment> segmentsRound2 = typeAligner.align(tokensAtype, tokensBtype);
        Score[][] cells = typeAligner.cells;
        assertEquals(ScoreType.text, typeAligner.establishTypeOfCell(cells[7][2], tokensAtype, tokensBtype));
        assertEquals(ScoreType.punctuation, typeAligner.establishTypeOfCell(cells[1][1], tokensAtype, tokensBtype));
        assertEquals(ScoreType.mix, typeAligner.establishTypeOfCell(cells[3][2], tokensAtype, tokensBtype));


    }

}

