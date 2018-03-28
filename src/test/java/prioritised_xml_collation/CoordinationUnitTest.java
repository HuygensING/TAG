package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.Segment.Type.aligned;
import static prioritised_xml_collation.Segment.Type.replacement;
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
}