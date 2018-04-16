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

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CellTypeUnitTest {

    @Test
    public void TestScoreType() throws Exception{
        Tokenizer tokenizer = new Tokenizer();
        List<TAGToken> tokensWa = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-A.xml"));
        List<TAGToken> tokensWb = tokenizer.convertXMLFileIntoTokens(new File("input_xml/s21-focus-B.xml"));
        TypeAndContentAligner aligner = new TypeAndContentAligner();
        EditGraphTable table = aligner.alignAndReturnTable(tokensWa, tokensWb);
        Cell[][] cells = table.matrix;
        assertEquals(CellType.text, table.determineCellType(cells[10][5]));
        assertEquals(CellType.punctuation, table.determineCellType(cells[4][4]));
        assertEquals(CellType.mix, table.determineCellType(cells[6][5]));
    }

}

