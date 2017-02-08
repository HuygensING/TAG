package prioritised_xml_collation;

import org.hamcrest.BaseMatcher;
import prioritised_xml_collation.Segment;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class SegmentMatcher extends BaseMatcher<Segment> {

    @Override
    public boolean matches(Object o) {
        return false;
    }

    @Override
    public void describeTo(Description description) {

    }

    public static SegmentMatcher s(EditGraphAligner.Score.Type match) {
        return null;
    }

    public SegmentMatcher tokensWa(XMLTokenContentMatcher... tokens) {
        return null;
    }

    public SegmentMatcher tokensWb(XMLTokenContentMatcher... tokens) {
        return null;
    }
}
