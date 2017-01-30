package prioritised_xml_collation;

import javax.xml.soap.Text;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public class TypeScorer extends Scorer {

    @Override
    public boolean match(XMLToken tokenA, XMLToken tokenB) {
        boolean punctuationType = (tokenA.content.matches("\\W+") && tokenB.content.matches("\\W+"));
        boolean contentType = (tokenA.content.matches("\\w+") && tokenB.content.matches("\\w+"));
//        System.out.println(punctuationType + " " + contentType);
        if(tokenA instanceof ElementToken && tokenB instanceof ElementToken) {
            return true;
        }
        if(tokenA instanceof TextToken && tokenB instanceof TextToken) {
           if(punctuationType || contentType) {
                return true;
           }
        }
        return false;
    }
}
