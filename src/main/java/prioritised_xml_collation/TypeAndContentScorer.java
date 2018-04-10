package prioritised_xml_collation;

// The Scorer determines whether a cell contains a match
// and fills the EditTable with a global score
public class TypeAndContentScorer extends AbstractScorer {
    @Override
    public Match match(XMLToken tokenA, XMLToken tokenB) {
        // First we see whether the tokens have the same type
        Token.Type typeTokenA = EditGraphTable.determineTypeOfToken(tokenA);
        Token.Type typeTokenB = EditGraphTable.determineTypeOfToken(tokenB);
        if (typeTokenA != typeTokenB) {
            return AbstractScorer.Match.not_matched;
        }
        // Only then we see whether they match on content
        //NOTE: no normalisation applied!
        boolean contentMatch = tokenA.content.equals(tokenB.content);
        if (contentMatch) {
            return AbstractScorer.Match.match;
        }
        // If the tokens are of the same type but not the same content
        // They are defined as a semantic match
        return AbstractScorer.Match.semanticMatch;
    }

}
