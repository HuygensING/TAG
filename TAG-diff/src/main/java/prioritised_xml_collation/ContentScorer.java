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
