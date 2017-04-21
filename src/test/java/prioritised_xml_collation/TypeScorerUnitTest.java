package prioritised_xml_collation;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public class TypeScorerUnitTest {

    @Test
    public void testScoreXMLmatchPunctuation() {
        XMLToken tokenA = new TextToken(",");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scorePunctuation = new TypeScorer();
        boolean resultScorer = scorePunctuation.match(tokenA, tokenB);
        assertThat(resultScorer, is(true));
    }

    @Test
    public void testScoreXMLnonMatchPunctuation() {
        XMLToken tokenA = new TextToken("black");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scorePunctuation = new TypeScorer();
        boolean resultScorer = scorePunctuation.match(tokenA, tokenB);
        assertThat(resultScorer, is(false));
    }

    @Test
    public void testScoreXMLmatchCharacters() {
        XMLToken tokenA = new TextToken("cat");
        XMLToken tokenB = new TextToken("white");
        AbstractScorer scorePunctuation = new TypeScorer();
        boolean resultScorer = scorePunctuation.match(tokenA, tokenB);
        assertThat(resultScorer, is(true));
    }

    @Test
    public void testScoreXMLMatchElement() {
        XMLToken tokenA = new ElementToken("s");
        XMLToken tokenB = new ElementToken("p");
        AbstractScorer scorePunctuation = new TypeScorer();
        boolean resultScorer = scorePunctuation.match(tokenA, tokenB);
        assertThat(resultScorer, is(true));
    }

    @Test
    public void testScoreXMLnonMatchElement() {
        XMLToken tokenA = new ElementToken("p");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scorePunctuation = new TypeScorer();
        boolean resultScorer = scorePunctuation.match(tokenA, tokenB);
        assertThat(resultScorer, is(false));
    }

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
// TODO create four extra tests to assert each possible situation.
