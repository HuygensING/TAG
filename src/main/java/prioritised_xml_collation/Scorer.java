package prioritised_xml_collation;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public abstract class Scorer {
    public abstract boolean match(XMLToken tokenA, XMLToken tokenB);
}
