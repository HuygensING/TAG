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
        TwoPhasedAligner aligner = new TwoPhasedAligner();
        List<Segment> segmentsRound1 = aligner.alignmentPhaseOne(tokensA, tokensB);

        Segment segmentReplaced = segmentsRound1.get(1);
        List<XMLToken> tokensAtype = segmentReplaced.tokensWa;
        List<XMLToken> tokensBtype = segmentReplaced.tokensWb;
        System.out.println(tokensAtype);
        System.out.println(tokensBtype);

        EditGraphTable table = aligner.alignmentPhaseTwoWithoutSegments(tokensAtype, tokensBtype);
        Cell[][] cells = table.matrix;
        assertEquals(CellType.text, table.establishTypeOfCell(cells[7][2]));
        assertEquals(CellType.punctuation, table.establishTypeOfCell(cells[1][1]));
        assertEquals(CellType.mix, table.establishTypeOfCell(cells[3][2]));
    }

}

