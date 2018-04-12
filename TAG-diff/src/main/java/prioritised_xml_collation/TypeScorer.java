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
 * Created by ellibleeker on 30/01/2017.
 */
public class TypeScorer extends AbstractScorer {

    @Override
    public Match match(XMLToken tokenA, XMLToken tokenB) {
        boolean punctuationType = (tokenA.content.matches("\\W+") && tokenB.content.matches("\\W+"));
        boolean contentType = (tokenA.content.matches("\\w+") && tokenB.content.matches("\\w+"));
//        System.out.println(punctuationType + " " + contentType);
        if(tokenA instanceof ElementToken && tokenB instanceof ElementToken) {
            return AbstractScorer.Match.match;
        }
        if(tokenA instanceof TextToken && tokenB instanceof TextToken) {
            // double pipes means "OR"
            // so: if token type is punctuation or token type is content, boolean is true
           if(punctuationType || contentType) {
                return AbstractScorer.Match.match;
           }
        }
        return AbstractScorer.Match.not_matched;
    }
}
