package prioritised_xml_collation;

import java.util.List;

/**
 * Created by ellibleeker on 03/03/2017.
 */
public class Coordination {

    private List<XMLToken> tokensWa;
    private List<XMLToken> tokensWb;

    public Node alignTokens(List<XMLToken> tokensWa, List<XMLToken> tokensWb) {
        // input tokens from outside class
        this.tokensWa = tokensWa;
        this.tokensWb = tokensWb;
        AbstractScorer contentScorer = new ContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(contentScorer);
        // align on content
        List<Segment> segments = aligner.align(tokensWa, tokensWb);
        // set root node
        Node rootNode = Node.n();
        // add segments as nodes to root node

        // for each segment type replaced
        // align again on type with typeScorer
        // add segments as nodes to child node
        // return root node (and with it the whole tree)
        return null;
    }
}
