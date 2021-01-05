package prioritised_xml_collation;

/*-
 * #%L
 * TAG-diff
 * =======
 * Copyright (C) 2016 - 2021 HuC DI (KNAW)
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * ContentType Segmenter
 * Created by Ronald Haentjens Dekker
 * 22/03/2018
 *
 */
public class ContentTypeSegmenter implements SegmenterInterface {
    public List<Segment> calculateSegmentation(EditGraphTable editTable) {
        ArrayList<Segment> superwitness = new ArrayList<>();
        // CellIterator iterates cells:
        Iterator<Cell> iterateTable = editTable.iterator();
        // pointer is set in lower right corner at "lastCell"
        Cell lastCell = editTable.iterator().next();
        // As long as the pointer can move up in the editTable
        while (iterateTable.hasNext()) {
            // move one cell up
            Cell currentCell = iterateTable.next();
            // we change state based on type, in case of mixed type we look at the movement through the table
            if (editTable.determineCellType(currentCell) != editTable.determineCellType(lastCell)) {
                Segment newSegment = editTable.createSegmentOfCells(currentCell, lastCell);
                superwitness.add(0, newSegment);
                lastCell = currentCell;
            }
        }
        return superwitness;
    }
}
