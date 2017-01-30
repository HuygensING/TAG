package prioritised_xml_collation;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public class TypeScorer extends Scorer {

    @Override
    public boolean match(XMLToken tokenA, XMLToken tokenB) {
        if((tokenA.content.matches("\\W") && tokenB.content.matches("\\W")) || (tokenA instanceof ElementToken && tokenB instanceof ElementToken)) {
            return true;
        }
        return false;
    }
}

