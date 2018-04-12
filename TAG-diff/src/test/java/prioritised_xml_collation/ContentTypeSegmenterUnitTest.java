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
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.XMLTokenContentMatcher.t;

/**
 * Created by ellibleeker on 21/04/2017.
 */
public class ContentTypeSegmenterUnitTest {
    @Test
    public void testSelectionS21() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<XMLToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb, new ContentTypeSegmenter());
        System.out.println(segments);

        SegmentMatcher m1 = sM(Segment.Type.aligned).tokensWa(t("text"), t("s")).tokensWb(t("text"), t("s"));
        SegmentMatcher m2 = sM(Segment.Type.aligned).tokensWa(t("vrouw")).tokensWb(t("vrouw"));
        // Segment 1 is separate, not because of the content, but because they have the same type (punctuation)
         SegmentMatcher m3 = sM(Segment.Type.replacement).tokensWa(t(",")).tokensWb(t("!"));
         SegmentMatcher m4 = sM(Segment.Type.addition).tokensWb(t("/s"), t("s"));
         SegmentMatcher m5 = sM(Segment.Type.replacement).tokensWa(t("de"), t("ongewisheid")).tokensWb(t("Die"), t("dagen"), t("van"), t("nerveuze"), t("verwachting"));
         SegmentMatcher m6 = sM(Segment.Type.replacement).tokensWa(t("?")).tokensWb(t("."));
         SegmentMatcher m7 = sM(Segment.Type.aligned).tokensWa(t("/s"), t("/text")).tokensWb(t("/s"), t("/text"));
         assertThat(segments, contains(m1, m2, m3, m4, m5, m6, m7));
    }

}
