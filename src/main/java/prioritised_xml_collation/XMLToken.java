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
}
