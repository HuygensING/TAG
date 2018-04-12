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
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Ported from code written by Elli Bleeker
 */
public class Tokenizer {
    public List<XMLToken> convertXMLFileIntoTokens(File input) throws FileNotFoundException, XMLStreamException {
        FileReader fileReader = new FileReader(input);
        return tokenizeXMLDocument(fileReader);
    }

    private List<XMLToken> tokenizeXMLDocument(Reader reader) throws XMLStreamException {
        List<XMLToken> tokens = new ArrayList<>();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(reader);
        while (xmlEventReader.hasNext()) {
            XMLEvent event = xmlEventReader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tokens.add(new ElementToken(startElement.getName().getLocalPart()));
            } else if (event.isCharacters()) {
                Characters characters = event.asCharacters();
                tokens.addAll(tokenizeText(characters.getData()));
            } else if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                tokens.add(new ElementToken("/" + endElement.getName().getLocalPart()));
            }
        }
        return tokens;
    }

    private List<TextToken> tokenizeText(String data) {
        //NOTE: this pattern can be constructed outside of this method!
        Pattern pattern = Pattern.compile("\\w+|[^\\w\\s]+");
        Matcher matcher = pattern.matcher(data);
        List<TextToken> textTokens = new ArrayList<>();
        while (matcher.find()) {
            textTokens.add(new TextToken(matcher.group()));
        }
        return textTokens;
    }
}
