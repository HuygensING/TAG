package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2018 HuC DI (KNAW)
 * =======
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ellibleeker on 08/02/2017.
 */
public class Segment {
    final List<XMLToken>tokensWa;
    final List<XMLToken>tokensWb;
    final Type type;

    public Segment(List tokensWa, List tokensWb, Type type) {
        this.tokensWa = tokensWa;
        this.tokensWb = tokensWb;
        this.type = type;

        // Segments of superwitness with tokensWa and tokensWb
    }

    public Segment(Type type) {
        this.tokensWa = new ArrayList<>();
        this.tokensWb = new ArrayList<>();
        this.type = type;
    }

    public Segment(Segment original, Type type) {
        this.tokensWa = original.tokensWa;
        this.tokensWb = original.tokensWb;
        this.type = type;
    }

    // Factory method
    public static Segment s(Type type) {
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
                .map(XMLToken::new)
                // forEach runs pipeline; ordered in given order
                .forEachOrdered(tokensWa::add);
        return this;
    }

    public Segment tokensWb(String... tokenB) {
        // transform to one stream of strings
        Arrays.stream(tokenB)
                // iterate each string in the stream
                // and transform into XMLToken object
                .map(XMLToken::new)
                // forEach runs pipeline; ordered in given order
                .forEachOrdered(tokensWb::add);
        return this;
    }

    @Override
    public String toString() {
        return this.type.toString()+this.tokensWa.toString()+this.tokensWb.toString();
    }

    public enum Type {
        aligned, replacement, addition, omission, empty, not_aligned, semanticVariation
    }
}
