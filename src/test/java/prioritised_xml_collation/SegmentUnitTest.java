package prioritised_xml_collation;

import org.junit.Test;

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
}
