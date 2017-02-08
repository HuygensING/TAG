package prioritised_xml_collation;

import java.util.List;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class Segment {
    private final List tokensWa;
    private final List tokensWb;
    private final EditGraphAligner.Score.Type type;

    public Segment(List tokensWa, List tokensWb, EditGraphAligner.Score.Type type) {
        this.tokensWa = tokensWa;
        this.tokensWb = tokensWb;
        this.type = type;
    }
}
