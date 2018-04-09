package prioritised_xml_collation;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/*
 * Created by Ronald
 * 05/04/2018
 *
 */
public class TypeAndContentAlignerTestCase {


    @Test
    public void testSentence21() throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();

//        EditGraphTable editGraphTable = aligner.alignAndReturnTable(tokensWa, tokensWb);
//        Iterator<Cell> cells = editGraphTable.iterator();
//        for (; cells.hasNext();) {
//            Cell cell = cells.next();
//            System.out.println(editGraphTable.cellToString(cell));
//        }
        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb, new AlignmentAndTypeSegmenter());
        for (Segment s : segments) {
            System.out.println(s);
        }

    }

    @Test
    public void testPath() throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/test_path_A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/test_path_B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();

        EditGraphTable editGraphTable = aligner.alignAndReturnTable(tokensWa, tokensWb);
        Iterator<Cell> cells = editGraphTable.iterator();
        for (; cells.hasNext(); ) {
            Cell cell = cells.next();
            System.out.println(editGraphTable.cellToString(cell));
        }
    }
}
