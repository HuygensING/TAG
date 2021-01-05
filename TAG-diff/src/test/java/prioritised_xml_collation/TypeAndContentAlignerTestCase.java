package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2021 HuC DI (KNAW)
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
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.Segment.Type.*;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.TAGTokenContentMatcher.t;

/*
 * Created by Ronald Haentjens Dekker
 * 28/03/2018
 */
public class TypeAndContentAlignerTestCase {

    /*
     * In this testcase the alignment on type does not change anything, it cannot be improved upon.
     */
    @Test
    public void testAlignTokens() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb);
        assertThat(segments, contains(sM(aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")), sM(replacement).tokensWa(t("c")).tokensWb(t("a")), sM(aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI"))));
    }

    /*
     * In this testcase the alignment on type does matter, sentences are split up etc.
     */
    @Test
    public void testSentence21() throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();

//        EditGraphTable editGraphTable = aligner.alignAndReturnTable(tokensWa, tokensWb);
//        Iterator<Cell> cells = editGraphTable.iterator();
//        for (; cells.hasNext();) {
//            Cell cell = cells.next();
//            System.out.println(editGraphTable.cellToString(cell));
//        }
        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb, new AlignmentAndTypeSegmenter());

        for (Segment s : segments) {
            System.out.println(s);
        }

        SegmentMatcher m1 = sM(aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit"));
        SegmentMatcher m2 = sM(omission).tokensWa(t("lb"), t("/lb"));
        SegmentMatcher m3 = sM(aligned).tokensWa("del", "werven", "om", "/del", "add", "trachten", "naar", "/add", "een");
        SegmentMatcher m4 = sM(addition).tokensWb("lb", "/lb");
        SegmentMatcher m5 = sM(aligned).tokensWa("vrouw");
        SegmentMatcher m6 = sM(replacement).tokensWa(",").tokensWb("!");
        SegmentMatcher m7 = sM(addition).tokensWb("/s", "s");
        SegmentMatcher m8 = sM(replacement).tokensWa("de", "ongewisheid").tokensWb("Die", "dagen", "van", "nerveuze","verwachting");
        SegmentMatcher m9 = sM(aligned).tokensWa("voor", "de");
        SegmentMatcher m10 = sM(addition).tokensWb("lb", "/lb");
        SegmentMatcher m11 = sM(aligned).tokensWa("liefelijke", "toestemming");
        SegmentMatcher m12 = sM(replacement).tokensWa("!").tokensWb(".");
        SegmentMatcher m13 = sM(aligned).tokensWa("/s", "/div", "/body", "/text");
        assertThat(segments, contains(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13));

    }

    //TODO: add asserts!
    @Test
    public void testPath() throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/test_path_A.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/test_path_B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();

        EditGraphTable editGraphTable = aligner.alignAndReturnTable(tokensWa, tokensWb);
        Iterator<Cell> cells = editGraphTable.iterator();
        for (; cells.hasNext(); ) {
            Cell cell = cells.next();
            System.out.println(editGraphTable.cellToString(cell));
        }
    }


    /*
     * In this testcase the witnesses are reversed, the split becomes a join, addition becomes omission and so on.
     */
    @Test
    public void testSentence21Reversed() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
        List<Segment> segments = aligner.alignTokens(tokensWb, tokensWa);
        for (Segment s : segments) {
            System.out.println(s);
        }
        SegmentMatcher m1 = sM(aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit"));
        SegmentMatcher m2 = sM(addition).tokensWb("lb", "/lb");
        SegmentMatcher m3 = sM(aligned).tokensWa("del", "werven", "om", "/del", "add", "trachten", "naar", "/add", "een");
        SegmentMatcher m4 = sM(omission).tokensWa("lb", "/lb");
        SegmentMatcher m5 = sM(aligned).tokensWa("vrouw");
        SegmentMatcher m6 = sM(replacement).tokensWb(",").tokensWa("!");
        SegmentMatcher m7 = sM(omission).tokensWa("/s", "s");
        SegmentMatcher m8 = sM(replacement).tokensWb("de", "ongewisheid").tokensWa("Die", "dagen", "van", "nerveuze","verwachting");
        SegmentMatcher m9 = sM(aligned).tokensWa("voor", "de");
        SegmentMatcher m10 = sM(omission).tokensWa("lb", "/lb");
        SegmentMatcher m11 = sM(aligned).tokensWa("liefelijke", "toestemming");
        SegmentMatcher m12 = sM(replacement).tokensWa(".").tokensWb("!");
        SegmentMatcher m13 = sM(aligned).tokensWa("/s", "/div", "/body", "/text");
        assertThat(segments, contains(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13));
    }

    // Note: milestone elements are tokenized as an open and close tag, this has the danger that one of those tags can be seem as
    // a replacement rather than an omission/addition.
    @Test
    public void testReplacementOfTextWithMarkup_AKA_ForcedMixedType() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-forced_mixed_replacement.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-forced_mixed_replacement.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
        List<Segment> segments = aligner.alignTokens(tokensWb, tokensWa);
        for (Segment s : segments) {
            System.out.println(s);
        }
        SegmentMatcher m1 = sM(aligned).tokensWa("text");
        SegmentMatcher m2 = sM(omission).tokensWa("lb");
        SegmentMatcher m3 = sM(replacement).tokensWa("/lb").tokensWb("linebreak");
        SegmentMatcher m4 = sM(aligned).tokensWa("/text");
        assertThat(segments, contains(m1, m2, m3, m4));
    }

    //    // This test only tests the nodes of the first phase of the alignment
//    @Test
//    public void testAlignTokens() throws Exception {
//        Tokenizer tokenizer = new Tokenizer();
//        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
//        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
//        TwoPhasedAligner firstAlignment = new TwoPhasedAligner();
//        Node rootNode = firstAlignment.alignTokensAndReturnRootNode(tokensWa, tokensWb);
//        NodeMatcher rootNodeMatcher = nM();
//        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")));
//        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
//        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI")));
//        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2, childrenMatcher3);
//        assertThat(rootNode, is(rootNodeMatcher));
//    }

//    // This test uses the same examples as the previous one, but tests the nodes of the first and second phase.
//    // In this case there is no difference between the result of the first phase and the second one.
//    @Test
//    public void testAlignTokens2() throws Exception {
//        Tokenizer tokenizer = new Tokenizer();
//        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
//        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
//        TwoPhasedAligner aligner = new TwoPhasedAligner();
//        Node rootNode = aligner.alignTokensAndReturnRootNode(tokensWa, tokensWb);
//        NodeMatcher rootNodeMatcher = nM();
//        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")));
//        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
//        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI")));
//        NodeMatcher childrenMatcher4 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("c")).tokensWb(t("a")));
//       System.out.println(rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2.childrenMatcher(childrenMatcher4), childrenMatcher3));
//        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2.childrenMatcher(childrenMatcher4), childrenMatcher3);
//        assertThat(rootNode, is(rootNodeMatcher));
//    }

//    @Test
//    public void testAlignTokensS21() throws Exception {
//        Tokenizer tokenizer = new Tokenizer();
//        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-A.xml"));
//        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-B.xml"));
//        TwoPhasedAligner aligner = new TwoPhasedAligner();
//        Node rootNode = aligner.alignTokensAndReturnRootNode(tokensWa, tokensWb);
//        NodeMatcher rootNodeMatcher = nM();
//        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("text"), t("body"), t("div"), t("s"), t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")).tokensWb(t("text"), t("body"), t("div"), t("s"),t("Hoe"), t("zoet"), t("moet"), t("nochtans"), t("zijn"), t("dit")));
//        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.omission).tokensWa(t("lb"), t("/lb")));
//        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")).tokensWb(t("del"), t("werven"), t("om"), t("/del"), t("add"), t("trachten"), t("naar"), t("/add"), t("een")));
//        NodeMatcher childrenMatcher4 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWb(t("lb"), t("/lb")));
//        NodeMatcher childrenMatcher5 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw")));
//        NodeMatcher childrenMatcher6 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
//        NodeMatcher childrenMatcher7 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t(",")).tokensWb(t("!")));
//        NodeMatcher childrenMatcher8 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWb(t("/s"), t("s")));
//        NodeMatcher childrenMatcher9 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
//        NodeMatcher childrenMatcher10 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("voor"), t("de")).tokensWb(t("voor"), t("de")));
//        NodeMatcher childrenMatcher11 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWb(t("lb"), t("/lb")));
//        NodeMatcher childrenMatcher12 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("liefelijke"), t("toestemming")).tokensWb(t("liefelijke"), t("toestemming")));
//        NodeMatcher childrenMatcher13 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("!")).tokensWb(t(".")));
//        NodeMatcher childrenMatcher14 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("!")).tokensWb(t(".")));
//        NodeMatcher childrenMatcher15 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/div"), t("/body"), t("/text")).tokensWb(t("/s"), t("/div"), t("/body"), t("/text")));
//        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2, childrenMatcher3, childrenMatcher4, childrenMatcher5, childrenMatcher6.childrenMatcher(childrenMatcher7, childrenMatcher8, childrenMatcher9), childrenMatcher10, childrenMatcher11, childrenMatcher12, childrenMatcher13.childrenMatcher(childrenMatcher14), childrenMatcher15);
//        assertThat(rootNode, is(rootNodeMatcher));
//    }

//    @Test
//    public void testAlignSelection21() throws Exception {
//        Tokenizer tokenizer = new Tokenizer();
//        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
//        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
//        TwoPhasedAligner aligner = new TwoPhasedAligner();
//        Node rootNode = aligner.alignTokensAndReturnRootNode(tokensWa, tokensWb);
//        NodeMatcher rootNodeMatcher = nM();
//        NodeMatcher childrenMatcher1 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("text"), t("s"), t("vrouw")));
//        NodeMatcher childrenMatcher2 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t(","), t("de"), t("ongewisheid"), t("?")).tokensWb(t("!"), t("/s"), t("s"), t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting"),t(".")));
//        NodeMatcher childrenMatcher3 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t(",")).tokensWb(t("!")));
//        NodeMatcher childrenMatcher4 = nM(SegmentMatcher.sM(Segment.Type.addition).tokensWb(t("/s"), t("s")));
//        NodeMatcher childrenMatcher5 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting")));
//        NodeMatcher childrenMatcher6 = nM(SegmentMatcher.sM(Segment.Type.replacement).tokensWa(t("?")).tokensWb(t(".")));
//        NodeMatcher childrenMatcher7 = nM(SegmentMatcher.sM(Segment.Type.aligned).tokensWa(t("/s"), t("/text")).tokensWb(t("/s"), t("/text")));
//        rootNodeMatcher.childrenMatcher(childrenMatcher1, childrenMatcher2.childrenMatcher(childrenMatcher3, childrenMatcher4, childrenMatcher5, childrenMatcher6), childrenMatcher7);
//        assertThat(rootNode, is(rootNodeMatcher));
//    }
//}




}
