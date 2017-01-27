package prioritised_xml_collation;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Original created by Elli Bleeker.
 */
public class XMLTokenContentMatcher extends BaseMatcher {
    private final String expectedContent;

    private XMLTokenContentMatcher(String expectedContent) {
        this.expectedContent = expectedContent;
    }

    public boolean matches(Object o) {
        if (!(o instanceof XMLToken)) {
            return false;
        }
        XMLToken other = (XMLToken) o;
        return this.expectedContent.equals(other.content);
    }

    public void describeTo(Description description) {
        description.appendText("Expected content to be " + this.expectedContent + ".");
    }

    static XMLTokenContentMatcher t(String expectedContent) {
        return new XMLTokenContentMatcher(expectedContent);
    }

    static List<XMLTokenContentMatcher> tokenContent(String... content) {
        List<XMLTokenContentMatcher> tokenMatchers = new ArrayList<>();
        for (String t : content) {
            XMLTokenContentMatcher matcher = new XMLTokenContentMatcher(t);
            tokenMatchers.add(matcher);
        }
        return tokenMatchers;
    }
}
