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

    public Node(Segment segment, List<Node> children) {
        this.segment = segment;
        this.children = children;
    }

    public Node(Segment segment) {
        this.segment = segment;
        // empty list for child nodes that may be filled later
        this.children = new ArrayList<>();
    }

    // if it is a root node, there is no segment
    public Node(){
        this.segment = null;
        this.children = new ArrayList<>();
    }

    public String toString() {
        if (this.segment!= null)
            return this.segment.toString()+children.toString();
        else return ("Root Node ")+children.toString();
    }

    // Factory method: each node has a segment
    public static Node n(Segment segment) {
        return new Node(segment);
    }

    // except if it's a root node
    public static Node n(){
        return new Node();
    }

    // Builder pattern: a segment node has one or more child nodes as a list
    // child nodes could be empty (leaf node) or have again one or more child nodes
    public Node children (Node... children) {
        this.children.addAll(Arrays.asList(children));
        return this;
    }

}
