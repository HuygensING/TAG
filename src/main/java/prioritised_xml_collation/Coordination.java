package prioritised_xml_collation;

import java.util.List;

import static prioritised_xml_collation.Segment.s;

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
        AbstractSegmenter contentSegmenter = new ContentSegmenter();
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer, contentSegmenter);
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
        // for each segment type replaced
        for (Node childNode : rootNode.children) {
            if (childNode.segment != null && childNode.segment.type.equals(Score.Type.replacement)) {
                AbstractScorer typeScorer = new TypeScorer();
                AbstractSegmenter typeSegmenter = new TypeSegmenter();
                EditGraphAligner typeAligner = new EditGraphAligner(typeScorer, typeSegmenter);
                // align again on type with typeScorer
                List<Segment> typeSegments = typeAligner.align(childNode.segment.tokensWa, childNode.segment.tokensWb);
                for (Segment segment : typeSegments) {
                    Node node = Node.n(segment);
                    // add segments as nodes to child node
                    childNode.children(node);
                }
                //System.out.println(childNode.children);
            }
        }
        return rootNode;
    }
}
