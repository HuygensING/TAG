package prioritised_xml_collation;

public class TypeAndContentScorer extends AbstractScorer {
    @Override
    public Segment.Type match(XMLToken tokenA, XMLToken tokenB) {
        CellType typeTokenA = EditGraphTable.determineTypeOfToken(tokenA);
        CellType typeTokenB = EditGraphTable.determineTypeOfToken(tokenB);
        if (typeTokenA != typeTokenB) {
            return Segment.Type.not_aligned;
        }
        //NOTE: no normalisation applied!
        boolean contentAligned = tokenA.content.equals(tokenB.content);
        if (contentAligned) {
            return Segment.Type.aligned;
        }
        return Segment.Type.semanticVariation;
    }

}
