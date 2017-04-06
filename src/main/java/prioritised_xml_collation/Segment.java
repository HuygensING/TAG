package prioritised_xml_collation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class Segment {
    final List<XMLToken>tokensWa;
    final List<XMLToken>tokensWb;
    final Score.Type type;

    public Segment(List tokensWa, List tokensWb,Score.Type type) {
        this.tokensWa = tokensWa;
        this.tokensWb = tokensWb;
        this.type = type;

        // Segments of superwitness with tokensWa and tokensWb
    }

    public Segment(Score.Type type) {
        this.tokensWa = new ArrayList<>();
        this.tokensWb = new ArrayList<>();
        this.type = type;
    }

    // Factory method
    public static Segment s(Score.Type type) {
        return new Segment(type);
    }

    // Builder pattern
    // Fields tokensWa and tokensWb as list of strings
        // input: one array of strings
    public Segment tokensWa(String... tokenA) {
        // transform to one stream of strings
        Arrays.stream(tokenA)
                // iterate each string in the stream
                // and transform into XMLToken object
                .map(content -> new XMLToken(content))
                // forEach runs pipeline; ordered in given order
                .forEachOrdered(tokensWa::add);
        return this;
    }

    public Segment tokensWb(String... tokenB) {
        // transform to one stream of strings
        Arrays.stream(tokenB)
                // iterate each string in the stream
                // and transform into XMLToken object
                .map(content -> new XMLToken(content))
                // forEach runs pipeline; ordered in given order
                .forEachOrdered(tokensWb::add);
        return this;
    }

    @Override
    public String toString() {
        return this.type.toString()+this.tokensWa.toString()+this.tokensWb.toString();
    }
}
