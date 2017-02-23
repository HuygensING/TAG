package prioritised_xml_collation;

/**
 * Created by Ronald Haentjens Dekker on 29/01/17.
 * Ported from code written by Elli Bleeker.
 */
public class ElementToken extends XMLToken {
    public ElementToken(String tag) {
        super(tag);
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
