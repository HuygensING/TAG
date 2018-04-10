package prioritised_xml_collation;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Ported from code written by Elli Bleeker.
 */
public class TextToken extends XMLToken {
    public TextToken(String content) {
        super(content);
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
