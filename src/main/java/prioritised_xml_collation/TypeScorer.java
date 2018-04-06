package prioritised_xml_collation;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public class TypeScorer extends AbstractScorer {

    @Override
    public Cell.Match match(XMLToken tokenA, XMLToken tokenB) {
        boolean punctuationType = (tokenA.content.matches("\\W+") && tokenB.content.matches("\\W+"));
        boolean contentType = (tokenA.content.matches("\\w+") && tokenB.content.matches("\\w+"));
//        System.out.println(punctuationType + " " + contentType);
        if(tokenA instanceof ElementToken && tokenB instanceof ElementToken) {
            return Cell.Match.match;
        }
        if(tokenA instanceof TextToken && tokenB instanceof TextToken) {
            // double pipes means "OR"
            // so: if token type is punctuation or token type is content, boolean is true
           if(punctuationType || contentType) {
                return Cell.Match.match;
           }
        }
        return Cell.Match.not_matched;
    }
}
