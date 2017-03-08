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
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer);
        // align on content
        List<Segment> contentSegments = contentAligner.align(tokensWa, tokensWb);
        // set root node
        // rootNode has no segment
        Node rootNode = Node.n();
        // for each segment in list segment
        for (Segment segment : contentSegments) {
        // create child node with segment
            Node node = Node.n(segment);
            // add node to root node
            rootNode.children(node);
        }
//        // for each segment type replaced
//        for (Segment.replacement : children) { // get all Nodes with type = replacement
//            AbstractScorer typeScorer = new TypeScorer();
//            EditGraphAligner typeAligner = new EditGraphAligner(typeScorer);
//            // align again on type with typeScorer
//            List<Segment> typeSegments = typeAligner.align(tokensWa, tokensWb);
//            for (Segment segment : typeSegments)
//            {
//                Node node = Node.n(segment);
//                // add segments as nodes to child node
//                rootNode.children(node);
//            }
//        }
        // return root node (and with it the whole tree)
        return rootNode;
    }
}
