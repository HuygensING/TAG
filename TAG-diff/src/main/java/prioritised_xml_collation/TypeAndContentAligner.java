package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2018 HuC DI (KNAW)
 * =======
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
import java.util.List;

/*
 * TypeAndContentAligner
 * Created on 05/04/2018 by Ronald Haentjens Dekker
 * Rather than first aligning on content and then only in the case of replacements aligning on type
 * This aligner tries to do that the other way around
 * This code is an experiment to see whether we can do the high quality alignment in one single phase
 */
class TypeAndContentAligner {
    List<Segment> alignTokens(List<TAGToken> tokensWa, List<TAGToken> tokensWb) {
        EditGraphAligner aligner = createAligner();
        return aligner.alignAndSegment(tokensWa, tokensWb, new AlignmentAndTypeSegmenter());
    }

    List<Segment> alignTokens(List<TAGToken> tokensWa, List<TAGToken> tokensWb, SegmenterInterface segmenter) {
        EditGraphAligner aligner = createAligner();
        return aligner.alignAndSegment(tokensWa, tokensWb, segmenter);
    }

    EditGraphTable alignAndReturnTable(List<TAGToken> tokensWa, List<TAGToken> tokensWb) {
        EditGraphAligner editGraphAligner = createAligner();
      return editGraphAligner.align(tokensWa, tokensWb);
    }

    private EditGraphAligner createAligner() {
        AbstractScorer typeAndContentScorer = new TypeAndContentScorer();
        return new EditGraphAligner(typeAndContentScorer);
    }
}
