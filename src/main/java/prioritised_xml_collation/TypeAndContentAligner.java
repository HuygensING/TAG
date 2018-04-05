package prioritised_xml_collation;

import java.util.List;

/*
 * TypeAndContentAligner
 * Created on 05/04/2018 by Ronald Haentjens Dekker
 * Rather than first aligning on content and then only in the case of replacements aligning on type
 * This aligner tries to do that the other way around
 * This code is an experiment to see whether we can do the high quality alignment in one single phase
 */
class TypeAndContentAligner {
    List<Segment> alignTokens(List<XMLToken> tokensWa, List<XMLToken> tokensWb) {
        AbstractScorer typeAndContentScorer = new TypeAndContentScorer();
        EditGraphAligner aligner = new EditGraphAligner(typeAndContentScorer);
        return aligner.alignAndSegment(tokensWa, tokensWb, new AlignedNonAlignedSegmenter());
    }
}
