package prioritised_xml_collation;

/*
 * Created by Ronald Haentjens Dekker
 * on 06/04/2018
 */
public class Token {

    public enum Type {
        text, markup, punctuation
    }

    //TODO: move to XMLToken class and Tokeniser
    static Token.Type determineTypeOfToken(XMLToken token) {
        boolean punctuationType = (token.content.matches("\\W+"));
        boolean contentType = (token.content.matches("\\w+") && token instanceof TextToken);
        boolean markupType = (token instanceof ElementToken);
        if (punctuationType) {
            return Token.Type.punctuation;
        } else if (contentType) {
            return Token.Type.text;
        } else if (markupType) {
            return Token.Type.markup;
        }
        throw new RuntimeException("Unknown token type!");
    }

}
