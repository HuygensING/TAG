package prioritised_xml_collation;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public class  TypeScorerUnitTest {

    @Test
    public void testScoreXMLmatchPunctuation() {
        XMLToken tokenA = new TextToken(",");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.match));
    }

    @Test
    public void testScoreXMLnonMatchPunctuation() {
        XMLToken tokenA = new TextToken("black");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.not_matched));
    }

    @Test
    public void testScoreXMLmatchCharacters() {
        XMLToken tokenA = new TextToken("cat");
        XMLToken tokenB = new TextToken("white");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.match));
    }

    @Test
    public void testScoreXMLMatchElement() {
        XMLToken tokenA = new ElementToken("s");
        XMLToken tokenB = new ElementToken("p");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.match));
    }

    @Test
    public void testScoreXMLnonMatchElement() {
        XMLToken tokenA = new ElementToken("p");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.not_matched));
    }

}
// TODO create four extra tests to assert each possible situation.
