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

import static org.hamcrest.MatcherAssert.assertThat;
import static prioritised_xml_collation.TAGTokenContentMatcher.containsTokens;
/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Original made by Elli Bleeker.
 */
public class TokenizeXMLUnitTest {


    @Test
    public void testTokenizeXML() throws FileNotFoundException, XMLStreamException {
        File input = new File("input_xml/black_cat_example.xml");
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokens = tokenizer.convertXMLFileIntoTokens(input);
        assertThat(tokens, containsTokens("TEI", "s", "The", "add", "black", "/add", "cat", ".",
                "/s", "/TEI"));
    }

}

