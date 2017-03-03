package prioritised_xml_collation;
import com.codepoetics.protonpack.StreamUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

/**
 * Created by ellibleeker on 24/02/2017.
 */
public class NodeMatcher extends BaseMatcher<Node> {
    // a node object has 2 fields:
    private SegmentMatcher segmentMatcher;
    private List<NodeMatcher> childrenMatcher;

    private NodeMatcher(SegmentMatcher segmentMatcher) {
        this.segmentMatcher = segmentMatcher;
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
        // NB Node object has list of nodes as childrenMatcher >> use zip
        // NB two lists for childrenMatcher: NodeMatcher and actual Node
        Stream<NodeMatcher> streamChildren = this.childrenMatcher.stream();
        Stream<Node> streamActualChildren = node.children.stream();
        List<Boolean> zippedChildren = StreamUtils.zip(streamChildren, streamActualChildren, (matcher, actual) -> matcher.matches(actual))
                .collect(Collectors.toList());
        if (zippedChildren.contains(false)) {
            return false;
        }
        // Check whether child node has childrenMatcher (i.e., segment type "replacement")
        // NB this could be infinite!? childrenMatcher of childrenMatcher of childrenMatcher
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(segmentMatcher.toString()+" ");
        description.appendText(childrenMatcher.toString()+" ");
    }

    @Override
    public void describeMismatch(Object object, Description description) {
        if (object instanceof Node){
            Node node = (Node) object;
            description.appendText("and segment is " + node.segment.toString());
            description.appendText("with children ");
            for (Node child : node.children) {
                description.appendText(child.toString()+" ");
            }
            description.appendText(".");
        }
        else {description.appendText("but actual object is "+object.getClass().getName());
        }
    }

    // Factory method: each node has a segment
    public static NodeMatcher nM(SegmentMatcher segmentMatcher) {
        return new NodeMatcher(segmentMatcher);
    }

    // Builder pattern: a Segment Node has one or more child nodes (list)
    // Child nodes could be empty (leaf node) or have one or more child nodes (list) again
    // "One or more" is indicated with '...'
    public NodeMatcher childrenMatcher(NodeMatcher... childrenMatcher) {
        this.childrenMatcher = Arrays.asList(childrenMatcher);
        return this;
    }

// TODO make unittest for NodeMatcher

}
