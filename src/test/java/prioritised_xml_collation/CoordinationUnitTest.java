package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.Segment.Type.*;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

/*
 * Created by Ronald Haentjens Dekker
 * 28/03/2018
 */
public class CoordinationUnitTest {

    /*
     * In this testcase the second alignment phase does not change anything, it can not be improved upon.
     */
    @Test
    public void testAlignTokens() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
        Coordination aligner = new Coordination();
        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb);
        assertThat(segments, contains(sM(aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")), sM(replacement).tokensWa(t("c")).tokensWb(t("a")), sM(aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI"))));
    }

    /*
     * In this testcase the second alignment phase does matter, sentences are split up etc.
     */
    @Test
    public void testSentence21() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
        Coordination aligner = new Coordination();
        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb);
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

    /*
     * In this testcase the witnesses are reversed, the split becomes a join, addition becomes omission and so on.
     */
    @Test
    public void testSentence21Reversed() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
        Coordination aligner = new Coordination();
        List<Segment> segments = aligner.alignTokens(tokensWb, tokensWa);
        for (Segment s : segments) {
            System.out.println(s);
        }
        SegmentMatcher m1 = sM(aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit"));
        SegmentMatcher m2 = sM(addition).tokensWb("lb", "/lb");
        SegmentMatcher m3 = sM(aligned).tokensWa("del", "werven", "om", "/del", "add", "trachten", "naar", "/add", "een");
        SegmentMatcher m4 = sM(omission).tokensWa("lb", "/lb");
        SegmentMatcher m5 = sM(aligned).tokensWa("vrouw");
        SegmentMatcher m6 = sM(replacement).tokensWb(",").tokensWa("!");
        SegmentMatcher m7 = sM(omission).tokensWa("/s", "s");
        SegmentMatcher m8 = sM(replacement).tokensWb("de", "ongewisheid").tokensWa("Die", "dagen", "van", "nerveuze","verwachting");
        SegmentMatcher m9 = sM(aligned).tokensWa("voor", "de");
        SegmentMatcher m10 = sM(omission).tokensWa("lb", "/lb");
        SegmentMatcher m11 = sM(aligned).tokensWa("liefelijke", "toestemming");
        SegmentMatcher m12 = sM(replacement).tokensWa(".").tokensWb("!");
        SegmentMatcher m13 = sM(aligned).tokensWa("/s", "/div", "/body", "/text");
        assertThat(segments, contains(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13));
    }
}