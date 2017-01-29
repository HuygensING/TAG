package prioritised_xml_collation;

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
