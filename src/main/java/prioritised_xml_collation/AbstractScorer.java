package prioritised_xml_collation;

import prioritised_xml_collation.EditGraphAligner.Score;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public abstract class AbstractScorer {
    public abstract boolean match(XMLToken tokenA, XMLToken tokenB);

    // the method gap returns a new object Score (that is created in the EditGraphAligner)
    // and has the fields type, x, y, parent, i
    public Score gap(int x, int y, Score parent) {
        return new Score(Boolean.FALSE, x, y, parent, parent.globalScore - 1);
    }

    public Score score(int x, int y, Score parent, boolean match) {
        if (match) {
            return new Score(Boolean.TRUE, x, y, parent, parent.globalScore);
        }
        // "replacement" means replacement (omission + addition)
        return new Score(Boolean.FALSE, x, y, parent, parent.globalScore - 2);
    }
//
//    private Score.Type determineType(int x, int y, Score parent){
//        if (x == parent.x) {
//            return Score.Type.addition;
//        }
//        if (y == parent.y) {
//            return Score.Type.omission;
//        }
//        return Score.Type.empty;
//    }
}
