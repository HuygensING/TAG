package prioritised_xml_collation;
import com.codepoetics.protonpack.StreamUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

/**
 * Created by ellibleeker on 24/02/2017.
 */
public class NodeMatcher extends BaseMatcher<Node> {
    // a node object has 2 fields:
    private final SegmentMatcher segmentMatcher;
    private final List<NodeMatcher> childMatcher;
    // unless specified otherwise, we do not expect the given node to be a root node
    private Boolean isRootNode;

    private NodeMatcher(SegmentMatcher segmentMatcher) {

        this.segmentMatcher = segmentMatcher;
        this.childMatcher = new ArrayList<>();
        this.isRootNode = false;
    }

    private NodeMatcher() {
        this.segmentMatcher = null;
        this.childMatcher = new ArrayList<>();
        this.isRootNode = true;
    }


    @Override
    // check whether given object is Node object:
    public boolean matches(Object o) {
        if (!(o instanceof Node)) {
            return false;
        }
        Node node = (Node) o;
        // List expectations
        // Check whether NodeMatcher has field segmentMatcher
        if (this.segmentMatcher != null) {
            // check if given Node object has same segments as NodeMatcher
            // NB this is done by the SegmentMatcher
            if (!(this.segmentMatcher.matches(node.segment))){
                return false;
            }
            // TODO we don't do anything if the object has no SegmentMatcher
            // (for instance in the case of a root node)
        }
        // NB Node object has list of nodes as childMatcher >> use zip
        // NB two lists for childMatcher: NodeMatcher and actual Node
        Stream<NodeMatcher> streamChildren = this.childMatcher.stream();
        Stream<Node> streamActualChildren = node.children.stream();
        List<Boolean> zippedChildren = StreamUtils.zip(streamChildren, streamActualChildren, (matcher, actual) -> matcher.matches(actual))
                .collect(Collectors.toList());
        if (zippedChildren.contains(false)) {
            return false;
        }
        // Check whether child node has childMatcher (i.e., segment type "replacement")
        // NB this could be infinite!? childMatcher of childMatcher of childMatcher
        return true;
    }

    @Override
    public void describeTo(Description description) {
        if (segmentMatcher == null) {
        description.appendText("Root node");
        }
        else {
        description.appendText(segmentMatcher.toString()+" ");
        }
        description.appendText(childMatcher.toString()+" ");
    }

    @Override
    public void describeMismatch(Object object, Description description) {
        if (object instanceof Node){
            Node node = (Node) object;
            if (node.segment == null) {
                description.appendText("Node is root node ");
            }
            else {
            description.appendText("with segment " + node.segment.toString());
            }
            if (!node.children.isEmpty()) {
                description.appendText("with children ");
                for (Node child : node.children) {
                    describeMismatch(child, description);
                }
             }
        }
        else {description.appendText("but actual object is "+object.getClass().getName());
        }
    }

    // Factory method: each node has a segment...
    public static NodeMatcher nM(SegmentMatcher segmentMatcher) {
        return new NodeMatcher(segmentMatcher);
    }

    // ... except the root node
    public static NodeMatcher nM(){
        return new NodeMatcher();
    }

    // Builder pattern: a Segment Node has one or more child nodes (list)
    // Child nodes could be empty (leaf node) or have one or more child nodes (list) again
    // "One or more" is indicated with '...'
    public NodeMatcher childrenMatcher(NodeMatcher... childrenMatcher) {
        this.childMatcher.addAll(Arrays.asList(childrenMatcher));
        return this;
    }

// TODO make unittest for NodeMatcher

}
