package prioritised_xml_collation;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static prioritised_xml_collation.Node.n;
import static prioritised_xml_collation.NodeMatcher.nM;
import static prioritised_xml_collation.Segment.s;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

/**
 * Created by ellibleeker on 02/03/2017.
 */
public class NodeUnitTest {

    @Test
    public void testNodeMatcher() throws Exception {
       Node node = n(s(EditGraphAligner.Score.Type.replacement).tokensWa("c").tokensWb("a"));
       Node children = n(s(EditGraphAligner.Score.Type.replacement).tokensWa("c").tokensWb("a"));
       node.children(children);

       NodeMatcher nodeMatcher = nM(SegmentMatcher.sM(EditGraphAligner.Score.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
       NodeMatcher childrenMatcher = nM(SegmentMatcher.sM(EditGraphAligner.Score.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
       nodeMatcher.childrenMatcher(childrenMatcher);

       assertThat(node, is(nodeMatcher));
    }
}
// TODO Create more tests for NodeMatcher:
// 1. Expect root node that is root node
// 2. Expect root node that is not root node
// 3. Expect child node that is child node
// 4. Expect child node that is not child node

