package prioritised_xml_collation;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by ellibleeker on 21/04/2017.
 */
public class TypeSegmenterTest {
    @Test
    public void testSelectionS21() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensA = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<XMLToken> tokensB = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        AbstractScorer scoreType = new TypeScorer();
        EditGraphAligner aligner = new EditGraphAligner(scoreType);
        List segments = aligner.align(tokensA, tokensB);
        System.out.println(segments);
        Assert.fail("Expected fail");
        // SegmentMatcher expectedSegments = sM(EditGraphAligner.ScoreIterator.Type.aligned).tokensWa(t("text"), t("s"), t("vrouw")).tokensWb(t("a"), t("s"), t("vrouw")); sM(EditGraphAligner.ScoreIterator.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid"), t("?")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("nerveuze"), t("verwachting"), t(".")); sM(EditGraphAligner.ScoreIterator.Type.replacement)

        // EditGraphAligner on Type

    }

}
