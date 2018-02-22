package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ellibleeker on 06/04/2017.
 */
public interface SegmenterInterface {
    // Interface cannot have instance variables
    // Interface is instantiated by methods in Segmenter
    List<Segment> calculateSegmentation(Score[][] editTable, List<XMLToken> tokensA, List<XMLToken> tokensB);
}