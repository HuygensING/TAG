package prioritised_xml_collation;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class SegmentUnitTest {


    @Test
    public void testSegmentMatcher() throws Exception {
        List<XMLToken> tokensWa = Arrays.asList(new XMLToken("a"), new XMLToken("b"));
        List<XMLToken> tokensWb = Arrays.asList(new XMLToken("a"), new XMLToken("b"));
        EditGraphAligner.Score.Type type = EditGraphAligner.Score.Type.aligned;

        Segment segment = new Segment(tokensWa, tokensWb, type);
        assertThat(segment, is(sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("a"), t("b")).tokensWb(t("a"), t("b"))));
    }

    @Test
    public void testSegmentFactory() throws Exception {
        Segment segment = Segment.s(EditGraphAligner.Score.Type.aligned).tokensWa("a").tokensWb("a");
        assertThat(segment, is(sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("a")).tokensWb(t("a"))));
    }

    @Test
    public void testSegmentAligned() throws Exception {
        File input_tokensA = new File("input_xml/witA-simple.xml");
        File input_tokensB = new File("input_xml/witB-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentScorer contentScorer = new ContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        // actualSegment = one segment object with two lists of token(s) and a type
        Segment actualSegment = segments.get(0);
        SegmentMatcher expectedSegment = sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s"));
        // assert that the segment contains the tokens and the type we want it to have
        assertThat(actualSegment, is(expectedSegment));
    }

    @Test
    public void testSegmentReplaced() throws Exception {
        File input_tokensA = new File("input_xml/witA-simple.xml");
        File input_tokensB = new File("input_xml/witB-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentScorer contentScorer = new ContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        // actualSegment = one segment object with two lists of token(s) and a type
        Segment actualSegment = segments.get(1);
        System.out.println(segments);
        SegmentMatcher expectedSegment = sM(EditGraphAligner.Score.Type.replacement).tokensWa(t("c")).tokensWb(t("a"));
        // assert that the segment contains the tokens and the type we want it to have
        assertThat(actualSegment, is(expectedSegment));
    }

    @Test
    public void testAllSegments() throws Exception {
        File input_tokensA = new File("input_xml/witA-simple.xml");
        File input_tokensB = new File("input_xml/witB-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentScorer contentScorer = new ContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        assertThat(segments, contains(sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")), sM(EditGraphAligner.Score.Type.replacement).tokensWa(t("c")).tokensWb(t("a")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI"))));
    }

    @Test
    public void testSegmentS21() throws Exception {
        File input_tokensA = new File("input_xml/s21-A.xml");
        File input_tokensB = new File("input_xml/s21-B.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentScorer contentScorer = new ContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output and align
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        System.out.println(segments);
        assertThat(segments, contains(sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"),t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")), sM(EditGraphAligner.Score.Type.omission).tokensWa(t("lb"), t("/lb")).tokensWb(t("")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")).tokensWb(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")), sM(EditGraphAligner.Score.Type.addition).tokensWa(t("")).tokensWb(t("lb"), t("/lb")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw")), sM(EditGraphAligner.Score.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("voor"), t("de")).tokensWb(t("voor"), t("de")), sM(EditGraphAligner.Score.Type.addition).tokensWa(t("")).tokensWb(t("lb"), t("/lb")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("liefelijke"), t("toestemming")).tokensWb(t("liefelijke"), t("toestemming")), sM(EditGraphAligner.Score.Type.replacement).tokensWa(t("!")).tokensWb(t(".")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("/s"), t("/div"), t("/body"), t("/text")).tokensWb(t("/s"), t("/div"), t("/body"), t("/text"))));
    }

    @Ignore("Test fails when root node is not a match")
    @Test
    public void testLastSegment() throws Exception {
        File input_tokensA = new File("input_xml/witA-simple2.xml");
        File input_tokensB = new File("input_xml/witB-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentScorer contentScorer = new ContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output and align
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        assertThat(segments, contains(sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"),t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")), sM(EditGraphAligner.Score.Type.omission).tokensWa(t("lb"), t("/lb")).tokensWb(t("")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")).tokensWb(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")), sM(EditGraphAligner.Score.Type.addition).tokensWa(t("")).tokensWb(t("lb"), t("/lb")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw")), sM(EditGraphAligner.Score.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("vóór"), t("de")).tokensWb(t("vóór"), t("de")), sM(EditGraphAligner.Score.Type.addition).tokensWa(t("")).tokensWb(t("lb"), t("/lb")), sM(EditGraphAligner.Score.Type.aligned).tokensWa(t("liefelijke"), t("toestemming")).tokensWb(t("liefelijke"), t("toestemming")), sM(EditGraphAligner.Score.Type.replacement).tokensWa(t("!")).tokensWb(t("."))));
    }
    // TODO When root node is not a match the test fails: adjust typing
 }


