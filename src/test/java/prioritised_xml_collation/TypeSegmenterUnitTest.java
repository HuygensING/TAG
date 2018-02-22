package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

/**
 * Created by ellibleeker on 21/04/2017.
 */
public class TypeSegmenterUnitTest {
    @Test
    public void testSelectionS21() throws Exception {
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
        // take the replaced segment
        Segment segmentReplaced = segmentsRound1.get(1);
        // and align again on type
        List<XMLToken> tokensAtype = segmentReplaced.tokensWa;
        List<XMLToken> tokensBtype = segmentReplaced.tokensWb;
        List<Segment> segmentsRound2 = typeAligner.align(tokensAtype, tokensBtype);
        System.out.println(segmentsRound2);
        // SegmentMatcher expectedSegments = sM(Score.Type.aligned).tokensWa(t("text"), t("s")).tokensWb(t("text"), t("s")); sM(Score.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw")); sM(Score.Type.aligned).tokensWa(t(",")).tokensWb(t("!")); sM(Score.Type.addition).tokensWb(t("/s"), t("s")); sM(Score.Type.aligned).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")); sM(Score.Type.aligned).tokensWa(t("?")).tokensWb(t(".")); sM(Score.Type.aligned).tokensWa(t("s"), t("text")).tokensWb(t("s"), t("text"));
        // assertThat(segmentsRound2, contains(expectedSegments));
    }

}
