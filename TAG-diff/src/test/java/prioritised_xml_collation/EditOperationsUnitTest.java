package prioritised_xml_collation;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;
import static prioritised_xml_collation.Segment.Type.aligned;
import static prioritised_xml_collation.Segment.Type.replacement;
import static prioritised_xml_collation.SegmentMatcher.sM;
import static prioritised_xml_collation.TAGTokenContentMatcher.t;

/*
 * Created by Ronald Haentjens Dekker
 * on 16/04/2018
 */
public class EditOperationsUnitTest {

    @Test
    public void testTextEditOperationsSimple() throws FileNotFoundException, XMLStreamException {
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witA-simple.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/witB-simple.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
//        List<Segment> segments = aligner.alignTokens(tokensWa, tokensWb);
//        assertThat(segments, contains(sM(aligned).tokensWa(t("TEI"), t("s")).tokensWb(t("TEI"), t("s")), sM(replacement).tokensWa(t("c")).tokensWb(t("a")), sM(aligned).tokensWa(t("/s"), t("/TEI")).tokensWb(t("/s"), t("/TEI"))));
        //TODO: Move content type information to EditOperations.
        //THAT WOULD MAKE life easier...
        // We assert the text only edit operation first
        // so c-> a

        EditGraphTable editGraphTable = aligner.alignAndReturnTable(tokensWa, tokensWb);
        Set<EditOperation> editOperations = EditOperations.calculate(editGraphTable);
        assertThat(editOperations.size(), is(1));
        EditOperation rep = editOperations.iterator().next();
        //TODO: add an extra assert based on an EditOperationMatcher.
    }
}
