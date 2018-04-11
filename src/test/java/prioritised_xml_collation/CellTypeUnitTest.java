package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CellTypeUnitTest {

    @Test
    public void TestScoreType() throws Exception{
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
        EditGraphTable table = aligner.alignAndReturnTable(tokensWa, tokensWb);
        Cell[][] cells = table.matrix;
        assertEquals(CellType.text, table.determineUniqueCellType(cells[10][5]));
        assertEquals(CellType.punctuation, table.determineUniqueCellType(cells[4][4]));
        assertEquals(CellType.mix, table.determineUniqueCellType(cells[6][5]));
    }

}

