package prioritised_xml_collation;

import org.junit.Test;
import static org.hamcrest.Matchers.contains;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static prioritised_xml_collation.XMLTokenContentMatcher.tokenContent;

/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Original made by Elli Bleeker.
 */
public class TokenizeXMLUnitTest {


//    import unittest
//
//    from prioritised_xml_collation.tokenizer import Tokenizer
//
//
//    class TokenizeXML(unittest.TestCase):
//    def test_tokenize_xml(self):
//    input = open("../input_xml/witA-simple.xml")
//    tokenizer = Tokenizer()
//    tokens = str(tokenizer.convert_xml_file_into_tokens(input))
//            # tokens are returned as a list
//        # and list is stringified in order to test
//    expected = "[TEI, s, The, add, black, /add, cat, ., /s, /TEI]"
//            self.assertEqual(expected, tokens)

    @Test
    public void testTokenizeXML() {
        File input = new File("../input_xml/witA-simple.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<XMLToken> tokens = tokenizer.convertXMLFileIntoTokens(input);
        assertThat(tokens, contains(tokenContent("TEI", "s", "The", "add", "black", "/add", "cat", ".",
                "/s", "/TEI")));
    }
}
