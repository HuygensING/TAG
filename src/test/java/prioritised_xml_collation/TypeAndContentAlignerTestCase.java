package prioritised_xml_collation;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.Segment.Type.*;
import static prioritised_xml_collation.Segment.Type.aligned;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

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

        SegmentMatcher m1 = sM(aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit"));
        SegmentMatcher m2 = sM(omission).tokensWa(t("lb"), t("/lb"));
        SegmentMatcher m3 = sM(aligned).tokensWa("del", "werven", "om", "/del", "add", "trachten", "naar", "/add", "een");
        SegmentMatcher m4 = sM(addition).tokensWb("lb", "/lb");
        SegmentMatcher m5 = sM(aligned).tokensWa("vrouw");
        SegmentMatcher m6 = sM(replacement).tokensWa(",").tokensWb("!");
        SegmentMatcher m7 = sM(addition).tokensWb("/s", "s");
        SegmentMatcher m8 = sM(replacement).tokensWa("de", "ongewisheid").tokensWb("Die", "dagen", "van", "nerveuze","verwachting");
        SegmentMatcher m9 = sM(aligned).tokensWa("voor", "de");
        SegmentMatcher m10 = sM(addition).tokensWb("lb", "/lb");
        SegmentMatcher m11 = sM(aligned).tokensWa("liefelijke", "toestemming");
        SegmentMatcher m12 = sM(replacement).tokensWa("!").tokensWb(".");
        SegmentMatcher m13 = sM(aligned).tokensWa("/s", "/div", "/body", "/text");
        assertThat(segments, contains(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13));

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
