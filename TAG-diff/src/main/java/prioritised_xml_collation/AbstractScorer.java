package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2020 HuC DI (KNAW)
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
public abstract class AbstractScorer {
    public abstract Match match(TAGToken tokenA, TAGToken tokenB);

    // the method gap returns a new object Cell
    // Cell has the following fields: match (boolean), x + y (coordinates of EditTable), parent, global score
    public Cell gap(int x, int y, Cell parent) {
        return new Cell(Boolean.FALSE, x, y, parent, parent.globalScore - 1);
    }

    public Cell score(int x, int y, Cell parent, Match typeOfMatch) {
        if (typeOfMatch == Match.match) {
            return new Cell(Boolean.TRUE, x, y, parent, parent.globalScore+1);
        }
        if (typeOfMatch == Match.semanticMatch) {
            return new Cell(Boolean.FALSE, x, y, parent, parent.globalScore);
        }
        // "replacement" means omission + addition
        return new Cell(Boolean.FALSE, x, y, parent, parent.globalScore - 2);
    }

    public enum Match {
        match, not_matched, semanticMatch
    }
}
