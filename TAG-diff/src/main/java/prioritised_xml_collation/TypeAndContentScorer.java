package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2019 HuC DI (KNAW)
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
// The Scorer determines whether a cell contains a match
// and fills the EditTable with a global score
public class TypeAndContentScorer extends AbstractScorer {
    @Override
    public Match match(TAGToken tokenA, TAGToken tokenB) {
        // First we see whether the tokens have the same type
        Token.Type typeTokenA = tokenA.getType();
        Token.Type typeTokenB = tokenB.getType();
        if (typeTokenA != typeTokenB) {
            return AbstractScorer.Match.not_matched;
        }
        // Only then we see whether they match on content
        boolean contentMatch = tokenA.normalizedContent.equals(tokenB.normalizedContent);
        if (contentMatch) {
            return AbstractScorer.Match.match;
        }
        // If the tokens are of the same type but not the same content
        // They are defined as a semantic match
        return AbstractScorer.Match.semanticMatch;
    }

}
