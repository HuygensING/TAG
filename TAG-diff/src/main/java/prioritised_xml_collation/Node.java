package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ellibleeker on 24/02/2017.
 */
public class Node {
    final Segment segment;
    final List<Node> children;

    // if it is a root node, there is no segment
    private Node(){
        this.segment = null;
        this.children = new ArrayList<>();
    }

    private Node(Segment segment) {
        this.segment = segment;
        // empty list for child nodes that may be filled later
        this.children = new ArrayList<>();
    }

    public String toString() {
        if (this.segment!= null)
            return this.segment.toString()+children.toString();
        else return ("Root node ")+children.toString();
    }

    void addChildren(Node... children) {
        this.children.addAll(Arrays.asList(children));
    }

    // Factory method: each node has a segment
    static Node n(Segment segment) {
        return new Node(segment);
    }

    // except if it's a root node
    static Node n(){
        return new Node();
    }

}
