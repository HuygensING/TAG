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
        SegmenterInterface contentSegmenter = new AlignedNonAlignedSegmenter();
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer);
        // align on content
        List<Segment> contentSegments = contentAligner.alignAndSegment(tokensWa, tokensWb, contentSegmenter);
        // set root node
        // rootNode has no segment
        Node rootNode = Node.n();
        // for each segment in list segment
        for (Segment segment : contentSegments) {
            // create child node with segment
            Node node = Node.n(segment);
            // add node to root node
            rootNode.addChildren(node);
        }
        // for each segment type replaced
        for (Node childNode : rootNode.children) {
            if (childNode.segment != null && childNode.segment.type.equals(Segment.Type.replacement)) {
                AbstractScorer typeScorer = new TypeScorer();
                SegmenterInterface typeSegmenter = new ContentTypeSegmenter();
                EditGraphAligner typeAligner = new EditGraphAligner(typeScorer);
                // align again on type with typeScorer
                List<Segment> typeSegments = typeAligner.alignAndSegment(childNode.segment.tokensWa, childNode.segment.tokensWb, typeSegmenter);
                for (Segment segment : typeSegments) {
                    Node node = Node.n(segment);
                    // add segments as nodes to child node
                    childNode.addChildren(node);
                }
                System.out.println(childNode.children);
            }
        }
        return rootNode;
    }
}
