package prioritised_xml_collation;

public class TypeAndContentScorer extends AbstractScorer {
    @Override
    public boolean match(XMLToken tokenA, XMLToken tokenB) {
        CellType typeTokenA = EditGraphTable.determineTypeOfToken(tokenA);
        CellType typeTokenB = EditGraphTable.determineTypeOfToken(tokenB);
        if (typeTokenA != typeTokenB) {
            return false;
        }
        //NOTE: no normalisation applied!
        return tokenA.content.equals(tokenB.content);
    }
}
