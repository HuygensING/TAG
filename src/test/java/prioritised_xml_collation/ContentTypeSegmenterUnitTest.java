package prioritised_xml_collation;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ellibleeker on 21/04/2017.
 */
public class ContentTypeSegmenterUnitTest {
    @Test
    public void testSelectionS21() throws Exception {
        List<Segment> segmentsRound1 = firstRoundAlignment("input_xml/s21-focus-A.xml", "input_xml/s21-focus-B.xml");
        List<Segment> segmentsRound2 = alignSegmentBasedOnType(segmentsRound1.get(1));
        System.out.println(segmentsRound2);



        // SegmentMatcher expectedSegments = sM(Cell.Type.aligned).tokensWa(t("text"), t("s")).tokensWb(t("text"), t("s")); sM(Cell.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw")); sM(Cell.Type.aligned).tokensWa(t(",")).tokensWb(t("!")); sM(Cell.Type.addition).tokensWb(t("/s"), t("s")); sM(Cell.Type.aligned).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")); sM(Cell.Type.aligned).tokensWa(t("?")).tokensWb(t(".")); sM(Cell.Type.aligned).tokensWa(t("s"), t("text")).tokensWb(t("s"), t("text"));
        // assertThat(segmentsRound2, contains(expectedSegments));
    }

    private List<Segment> firstRoundAlignment(String filename_w1, String filename_w2) throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensA = tokenizer.convertXMLFileIntoTokens(new File(filename_w1));
        List<XMLToken> tokensB = tokenizer.convertXMLFileIntoTokens(new File(filename_w2));
        AbstractScorer contentScorer = new ContentScorer();
        SegmenterInterface contentSegmenter = new AlignedNonAlignedSegmenter();
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer);
        // System.out.println(segmentsRound1);
        return contentAligner.alignAndSegment(tokensA, tokensB, contentSegmenter);
    }
    private List<Segment> alignSegmentBasedOnType(Segment segmentReplaced) {
        // Take the replaced segment and get its tokens
        List<XMLToken> tokensAtype = segmentReplaced.tokensWa;
        List<XMLToken> tokensBtype = segmentReplaced.tokensWb;

        // Do the actual second phase alignment
        AbstractScorer scoreType = new TypeScorer();
        SegmenterInterface typeSegmenter = new ContentTypeSegmenter();
        EditGraphAligner typeAligner = new EditGraphAligner(scoreType);
        return typeAligner.alignAndSegment(tokensAtype, tokensBtype, typeSegmenter);
    }


}
