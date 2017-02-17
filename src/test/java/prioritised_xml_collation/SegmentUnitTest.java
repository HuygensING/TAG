package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.SegmentMatcher.s;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class SegmentUnitTest {


    @Test
    public void testSegmentMatcher() throws Exception {
        List<XMLToken> tokensWa = Arrays.asList(new XMLToken("a"), new XMLToken("b"));
        List<XMLToken> tokensWb = Arrays.asList(new XMLToken("a"), new XMLToken("b"));
        EditGraphAligner.Score.Type type = EditGraphAligner.Score.Type.match;

        Segment segment = new Segment(tokensWa, tokensWb, type);
        assertThat(segment, is(s(EditGraphAligner.Score.Type.match).tokensWa(t("a"), t("b")).tokensWb(t("a"), t("b"))));
    }

    @Test
    public void testSegment() throws Exception {
        File input_tokensA = new File("/Users/ellibleeker/IdeaProjects/prioritised_xml_collation_java/prioritised_xml_collation_java/input_xml/witA-simple.xml");
        File input_tokensB = new File("/Users/ellibleeker/IdeaProjects/prioritised_xml_collation_java/prioritised_xml_collation_java/input_xml/witB-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(input_tokensA);
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(input_tokensB);
        ContentMatchScorer contentScorer = new ContentMatchScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        // take that output
        // actualSegment = one segment object with two lists of token(s) and a type
        SegmentMatcher expectedSegment = s(EditGraphAligner.Score.Type.match).tokensWa(t("a"), t("b")).tokensWb(t("a"),t("b"));// describe expectations based on SegmentMatcher

        // assert that the segment contains the tokens and the type we want it to have
     //   assertThat(expectedSegment, is(actualSegment);
}
}


