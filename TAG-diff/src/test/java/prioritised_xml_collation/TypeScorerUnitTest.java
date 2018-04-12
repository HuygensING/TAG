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
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by ellibleeker on 30/01/2017.
 */
public class  TypeScorerUnitTest {

    @Test
    public void testScoreXMLmatchPunctuation() {
        XMLToken tokenA = new TextToken(",");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.match));
    }

    @Test
    public void testScoreXMLnonMatchPunctuation() {
        XMLToken tokenA = new TextToken("black");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.not_matched));
    }

    @Test
    public void testScoreXMLmatchCharacters() {
        XMLToken tokenA = new TextToken("cat");
        XMLToken tokenB = new TextToken("white");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.match));
    }

    @Test
    public void testScoreXMLMatchElement() {
        XMLToken tokenA = new ElementToken("s");
        XMLToken tokenB = new ElementToken("p");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.match));
    }

    @Test
    public void testScoreXMLnonMatchElement() {
        XMLToken tokenA = new ElementToken("p");
        XMLToken tokenB = new TextToken("!");
        AbstractScorer scoreType = new TypeScorer();
        AbstractScorer.Match resultScorer = scoreType.match(tokenA, tokenB);
        assertThat(resultScorer, is(AbstractScorer.Match.not_matched));
    }

}
// TODO create four extra tests to assert each possible situation.
