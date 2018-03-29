package prioritised_xml_collation;

import org.junit.Test;

import java.io.File;
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
        Node node = n(s(Segment.Type.replacement).tokensWa("c").tokensWb("a"));
        Node children = n(s(Segment.Type.replacement).tokensWa("c").tokensWb("a"));
        node.addChildren(children);
        NodeMatcher nodeMatcher = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
        NodeMatcher childrenMatcher = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
        nodeMatcher.childrenMatcher(childrenMatcher);
        assertThat(node, is(nodeMatcher));
    }

    // This test only tests the nodes of the first phase of the alignment
    @Test
    public void testAlignTokens() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
        TwoPhasedAligner firstAlignment = new TwoPhasedAligner();
        Node rootNode = firstAlignment.alignTokensAndReturnRootNode(tokensWa, tokensWb);
        NodeMatcher rootNodeMatcher = nM();
        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")));
        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI")));
        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2, childrenMatcher3);
        assertThat(rootNode, is(rootNodeMatcher));
    }

    // This test uses the same examples as the previous one, but tests the nodes of the first and second phase.
    // In this case there is no difference between the result of the first phase and the second one.
    @Test
    public void testAlignTokens2() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
        TwoPhasedAligner aligner = new TwoPhasedAligner();
        Node rootNode = aligner.alignTokensAndReturnRootNode(tokensWa, tokensWb);
        NodeMatcher rootNodeMatcher = nM();
        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")));
        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI")));
        NodeMatcher childrenMatcher4 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
       System.out.println(rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2.childrenMatcher(childrenMatcher4), childrenMatcher3));
        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2.childrenMatcher(childrenMatcher4), childrenMatcher3);
        assertThat(rootNode, is(rootNodeMatcher));
    }

    @Test
   // @Ignore("Second round of alignment (on type) not entirely successful")
    public void testAlignTokensS21() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
        TwoPhasedAligner aligner = new TwoPhasedAligner();
        Node rootNode = aligner.alignTokensAndReturnRootNode(tokensWa, tokensWb);
        NodeMatcher rootNodeMatcher = nM();
        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"), t("body"), t("div"), t("s"),t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")));
        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.omission).tokensWb(t("lb"), t("/lb")));
        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")).tokensWb(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")));
        NodeMatcher childrenMatcher4 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWa(t("lb"), t("/lb")));
        NodeMatcher childrenMatcher5 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw")));
        NodeMatcher childrenMatcher6 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
        NodeMatcher childrenMatcher7 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWb(t(","), t("!")));
        NodeMatcher childrenMatcher8 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWb(t("/s"), t("s")));
        NodeMatcher childrenMatcher9 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
        NodeMatcher childrenMatcher10 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("voor"), t("de"), t("liefelijke"), t("toestemming")).tokensWb(t("voor"), t("de"), t("liefelijke"), t("toestemming")));
        NodeMatcher childrenMatcher11 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("!")).tokensWb(t(".")));
        NodeMatcher childrenMatcher12 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("!")).tokensWb(t(".")));
        NodeMatcher childrenMatcher13 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/div"), t("/body"), t("/text")).tokensWb(t("/s"), t("/div"), t("/body"), t("/text")));
        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2, childrenMatcher3, childrenMatcher4, childrenMatcher5, childrenMatcher6.childrenMatcher(childrenMatcher7, childrenMatcher8, childrenMatcher9), childrenMatcher10, childrenMatcher11.childrenMatcher(childrenMatcher12), childrenMatcher13);
        assertThat(rootNode, is(rootNodeMatcher));
    }

    @Test
    // @Ignore("Second round of alignment (on type) not entirely successful")
    public void testAlignSelection21() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        TwoPhasedAligner aligner = new TwoPhasedAligner();
        Node rootNode = aligner.alignTokensAndReturnRootNode(tokensWa, tokensWb);
        NodeMatcher rootNodeMatcher = nM();
        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("text"), t("s"), t("vrouw")));
        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWb(t(","), t("!")));
        NodeMatcher childrenMatcher4 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWb(t("/s"), t("s")));
        NodeMatcher childrenMatcher5 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
        NodeMatcher childrenMatcher6 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("!")).tokensWb(t(".")));
        NodeMatcher childrenMatcher7 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("!")).tokensWb(t(".")));
        NodeMatcher childrenMatcher8 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/text")).tokensWb(t("/s"), t("/text")));
        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2.childrenMatcher(childrenMatcher3, childrenMatcher4, childrenMatcher5), childrenMatcher6.childrenMatcher(childrenMatcher7), childrenMatcher8);
        assertThat(rootNode, is(rootNodeMatcher));
    }
}



// TODO Create more tests for NodeMatcher:
// 1. Expect root node that is root node
// 2. Expect root node that is not root node
// 3. Expect child node that is child node
// 4. Expect child node that is not child node

