package prioritised_xml_collation;

import java.util.List;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class Segment {
    final List<XMLToken>tokensWa;
    final List<XMLToken>tokensWb;
    final EditGraphAligner.Score.Type type;

    public Segment(List tokensWa, List tokensWb, EditGraphAligner.Score.Type type) {
        this.tokensWa = tokensWa;
        this.tokensWb = tokensWb;
        this.type = type;

        // Segments of superwitness with tokensWa and tokensWb


    }

    @Override
    public String toString() {
        return this.type.toString()+this.tokensWa.toString();
    }
}
