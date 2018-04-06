package prioritised_xml_collation;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public abstract class AbstractScorer {
    public abstract Cell.Match match(XMLToken tokenA, XMLToken tokenB);

    // the method gap returns a new object CellIterator (that is created in the EditGraphAligner)
    // and has the fields type, x, y, parent, i
    public Cell gap(int x, int y, Cell parent) {
        return new Cell(Boolean.FALSE, x, y, parent, parent.globalScore - 1);
    }

    public Cell score(int x, int y, Cell parent, Cell.Match typeOfMatch) {
        if (typeOfMatch == Cell.Match.match) {
            return new Cell(Boolean.TRUE, x, y, parent, parent.globalScore+1);
        }
        if (typeOfMatch == Cell.Match.semanticMatch) {
            return new Cell(Boolean.FALSE, x, y, parent, parent.globalScore);
        }
        // "replacement" means replacement (omission + addition)
        return new Cell(Boolean.FALSE, x, y, parent, parent.globalScore - 2);
    }
//
//    private CellIterator.Type determineType(int x, int y, CellIterator parent){
//        if (x == parent.x) {
//            return CellIterator.Type.addition;
//        }
//        if (y == parent.y) {
//            return CellIterator.Type.omission;
//        }
//        return CellIterator.Type.empty;
//    }
}
