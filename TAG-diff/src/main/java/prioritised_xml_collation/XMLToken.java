package prioritised_xml_collation;

/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Ported from code written by Elli Bleeker.
 * This class was originally called Token, but that would conflict with the Token interface in CollateX.
 */
public class XMLToken {
    public String content;

    public XMLToken(String content) {
        this.content = content;
    }

    //TODO: this can be done more efficiently!
    Token.Type getType() {
        boolean punctuationType = (content.matches("\\W+"));
        boolean contentType = (content.matches("\\w+") && this instanceof TextToken);
        boolean markupType = (this instanceof ElementToken);
        if (punctuationType) {
            return Token.Type.punctuation;
        } else if (contentType) {
            return Token.Type.text;
        } else if (markupType) {
            return Token.Type.markup;
        }
        throw new RuntimeException("Unknown token type!");
    }

    //TODO: whitespace handling

    //TODO: add generics

    //TODO: handle milestones as single tokens
}
