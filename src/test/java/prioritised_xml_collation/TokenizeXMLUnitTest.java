package prioritised_xml_collation;

import org.junit.Test;

import javax.xml.stream.XMLStreamException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static prioritised_xml_collation.XMLTokenContentMatcher.containsTokens;
/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Original made by Elli Bleeker.
 */
public class TokenizeXMLUnitTest {


    @Test
    public void testTokenizeXML() throws FileNotFoundException, XMLStreamException {
        File input = new File("input_xml/witA-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokens = tokenizer.convertXMLFileIntoTokens(input);
        assertThat(tokens, containsTokens("TEI", "s", "The", "add", "black", "/add", "cat", ".",
                "/s", "/TEI"));
    }

}

