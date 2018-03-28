package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellibleeker on 03/03/2017.
 * Extended by Ronald Haentjens Dekker 28/03/2018.
 */
class Coordination {

    List<Segment> alignTokens(List<XMLToken> tokensWa, List<XMLToken> tokensWb) {
        Node node = alignTokensAndReturnRootNode(tokensWa, tokensWb);
        List<Segment> result = new ArrayList<>();
        // traverse the leaf nodes of the nodes.
        List<Node> toTraverse = new ArrayList<>(node.children);
        while (!toTraverse.isEmpty()) {
            Node top = toTraverse.remove(0);
            // System.out.println("Traversing: "+top);
            // Is it a leaf? Yes, add the segment to the result; No: keep traversing.
            if (top.children.isEmpty()) {
                result.add(top.segment);
                //System.out.println(top.segment);
            } else {
                //NOTE: It matters where I add this stuff, want depth first traversal
                toTraverse.addAll(0, top.children);
            }
        }
        return result;
    }

    Node alignTokensAndReturnRootNode(List<XMLToken> tokensWa, List<XMLToken> tokensWb) {
        List<Segment> contentSegments = alignmentPhaseOne(tokensWa, tokensWb);
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
                // align again on type with typeScorer
                List<Segment> typeSegments = alignmentPhaseTwo(childNode.segment.tokensWa, childNode.segment.tokensWb);
                for (Segment segment : typeSegments) {
                    /* If the tokens are aligned on type in the second round, it means that the content is replaced
                     * in the first round, so override type of segment!
                     */
                    if (segment.type == Segment.Type.aligned) {
                        segment = new Segment(segment, Segment.Type.replacement);
                    }
                    Node node = Node.n(segment);
                    // add segments as nodes to child node
                    childNode.addChildren(node);
                }
//                System.out.println(childNode.children);
            }
        }
        return rootNode;
    }

    private List<Segment> alignmentPhaseOne(List<XMLToken> tokensWa, List<XMLToken> tokensWb) {
        // align on content
        AbstractScorer contentScorer = new ContentScorer();
        EditGraphAligner contentAligner = new EditGraphAligner(contentScorer);
        SegmenterInterface contentSegmenter = new AlignedNonAlignedSegmenter();
        return contentAligner.alignAndSegment(tokensWa, tokensWb, contentSegmenter);
    }

    private List<Segment> alignmentPhaseTwo(List<XMLToken> tokensWa, List<XMLToken> tokensWb) {
        // align on type
        AbstractScorer typeScorer = new TypeScorer();
        EditGraphAligner typeAligner = new EditGraphAligner(typeScorer);
        SegmenterInterface typeSegmenter = new ContentTypeSegmenter();
        return typeAligner.alignAndSegment(tokensWa, tokensWb, typeSegmenter);
    }

}
