package prioritised_xml_collation;

// The Scorer determines whether a cell contains a match
// and fills the EditTable with a global score
public class TypeAndContentScorer extends AbstractScorer {
    @Override
    public Cell.Match match(XMLToken tokenA, XMLToken tokenB) {
        Token.Type typeTokenA = EditGraphTable.determineTypeOfToken(tokenA);
        Token.Type typeTokenB = EditGraphTable.determineTypeOfToken(tokenB);
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
