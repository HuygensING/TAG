package prioritised_xml_collation;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

/**
 * Created by ellibleeker on 21/04/2017.
 */
public class ContentTypeSegmenterUnitTest {
    @Test
    public void testSelectionS21() throws Exception {
        List<Segment> segmentsRound1 = firstRoundAlignment("input_xml/s21-focus-A.xml", "input_xml/s21-focus-B.xml");
        List<Segment> segmentsRound2 = alignSegmentBasedOnType(segmentsRound1.get(1));
        System.out.println(segmentsRound2);

         // Segment 1 is aligned, not because of the content, but because they have the same type (punctuation)
         SegmentMatcher m1 = sM(Segment.Type.aligned).tokensWa(t(",")).tokensWb(t("!"));
         SegmentMatcher m2 = sM(Segment.Type.addition).tokensWb(t("/s"), t("s"));
         // When aligning on type, the length of tokens A and B does not have to be the same!
         SegmentMatcher m3 = sM(Segment.Type.aligned).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting"));
         SegmentMatcher m4 = sM(Segment.Type.aligned).tokensWa(t("?")).tokensWb(t("."));
         assertThat(segmentsRound2, contains(m1, m2, m3, m4));
    }

    private List<Segment> firstRoundAlignment(String filename_w1, String filename_w2) throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensA = tokenizer.convertXMLFileIntoTokens(new File(filename_w1));
        List<XMLToken> tokensB = tokenizer.convertXMLFileIntoTokens(new File(filename_w2));
        TwoPhasedAligner aligner = new TwoPhasedAligner();
        return aligner.alignmentPhaseOne(tokensA, tokensB);
    }

    private List<Segment> alignSegmentBasedOnType(Segment segmentReplaced) {
        // Take the replaced segment and get its tokens
        List<XMLToken> tokensAtype = segmentReplaced.tokensWa;
        List<XMLToken> tokensBtype = segmentReplaced.tokensWb;

        // Do the actual second phase alignment
        TwoPhasedAligner aligner = new TwoPhasedAligner();
        return aligner.alignmentPhaseTwo(tokensAtype, tokensBtype);
    }
}
