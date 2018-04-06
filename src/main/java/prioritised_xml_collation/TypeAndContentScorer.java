package prioritised_xml_collation;

public class TypeAndContentScorer extends AbstractScorer {
    @Override
    public Cell.Match match(XMLToken tokenA, XMLToken tokenB) {
        CellType typeTokenA = EditGraphTable.determineTypeOfToken(tokenA);
        CellType typeTokenB = EditGraphTable.determineTypeOfToken(tokenB);
        if (typeTokenA != typeTokenB) {
            return Cell.Match.not_matched;
        }
        //NOTE: no normalisation applied!
        boolean contentAligned = tokenA.content.equals(tokenB.content);
        if (contentAligned) {
            return Cell.Match.match;
        }
        return Cell.Match.semanticMatch;
    }

}
