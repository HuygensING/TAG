package prioritised_xml_collation;

import java.util.List;

/**
 * Created by ellibleeker on 24/02/2017.
 */
public class Node {
    final Segment segment;
    final List<Node> children;

    public Node(Segment segment, List<Node> children) {
        this.segment = segment;
        this.children = children;
    }

    public String toString() {
        return this.segment.toString()+children.toString();
    }

}
