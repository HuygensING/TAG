package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.SegmentMatcher.s;
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
        assertThat(segment, is(s(EditGraphAligner.Score.Type.aligned).tokensWa(t("a"), t("b")).tokensWb(t("a"), t("b"))));
    }

    @Test
    public void testSegmentAligned() throws Exception {
        File input_tokensA = new File("input_xml/witA-simple.xml");
        File input_tokensB = new File("input_xml/witB-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentMatchScorer contentScorer = new ContentMatchScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        // actualSegment = one segment object with two lists of token(s) and a type
        Segment actualSegment = segments.get(0);
        SegmentMatcher expectedSegment = s(EditGraphAligner.Score.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s"));
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
        ContentMatchScorer contentScorer = new ContentMatchScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        // actualSegment = one segment object with two lists of token(s) and a type
        Segment actualSegment = segments.get(1);
        System.out.println(segments);
        SegmentMatcher expectedSegment = s(EditGraphAligner.Score.Type.replacement).tokensWa(t("c")).tokensWb(t("a"));
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
        ContentMatchScorer contentScorer = new ContentMatchScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // take that output
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        assertThat(segments, contains(s(EditGraphAligner.Score.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")), s(EditGraphAligner.Score.Type.replacement).tokensWa(t("c")).tokensWb(t("a")), s(EditGraphAligner.Score.Type.aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI"))));
    }
 }


