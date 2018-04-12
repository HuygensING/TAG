package prioritised_xml_collation;

/**
 * Created by ellibleeker on 08/02/2017.
 */
class ContentScorer extends AbstractScorer {

    @Override
    public Match match(XMLToken a, XMLToken b) {
        //note: performance: whitespace normalization and matching happens over and over again.
        //note: in the production version of CollateX both these things happen before alignment.
        boolean c = a.content.trim().equals(b.content.trim());
        if (c) {
            return AbstractScorer.Match.match;
        }
        return AbstractScorer.Match.not_matched;
    }
}
