package prioritised_xml_collation;

/**
 * Created by ellibleeker on 06/04/2017.
 */
public class Score {

        public prioritised_xml_collation.Score parent;
        public int globalScore = 0;
        int x;
        int y;
        int previousX;
        int previousY;
        Boolean match;

        public Score(Boolean match, int x, int y, prioritised_xml_collation.Score parent, int i) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent == null ? 0 : parent.x;
            this.previousY = parent == null ? 0 : parent.y;
            this.globalScore = i;
        }

        public Score(Boolean match, int x, int y, prioritised_xml_collation.Score parent) {
            this.match = match;
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.previousX = parent.x;
            this.previousY = parent.y;
            this.globalScore = parent.globalScore;
        }

        public int getGlobalScore() {
            return this.globalScore;
        }

        public void setGlobalScore(int globalScore) {
            this.globalScore = globalScore;
        }

        @Override
        public String toString() {
            return "[" + this.y + "," + this.x + "]:" + this.globalScore;
        }

}
