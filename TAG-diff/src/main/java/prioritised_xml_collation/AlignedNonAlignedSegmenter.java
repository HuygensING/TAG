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

import java.util.ArrayList;
import java.util.List;

public class AlignedNonAlignedSegmenter implements SegmenterInterface {
    public List<Segment> calculateSegmentation(EditGraphTable editTable) {
        ArrayList<Segment> superwitness = new ArrayList<>();
        // We set last cell to the first iterable cell. (lower right corner)
        Cell lastCell = editTable.iterator().next();
        Cell currentCell = null;
        // CellIterator iterates cells:
      // As long as the pointer can move up in the editTable
      for (final Cell anEditTable : editTable) {
        // move one cell up
        currentCell = anEditTable;
        // stateChange if the type of the lastCell is not the same as the currentCell
        Boolean stateChange = lastCell.match != currentCell.match;
        if (stateChange) {
          // insert the segment to the superwitness list at the first position (position "0")
          superwitness.add(0, editTable.createSegmentOfCells(currentCell, lastCell));
          // change the pointer
          lastCell = currentCell;
        }
      }
        // process the final cell in de EditGraphTable (additions/omissions at the beginning of the witnesses)
        if (lastCell!=currentCell) {
            // insert the segment to the superwitness list at the first position (position "0")
            superwitness.add(0, editTable.createSegmentOfCells(currentCell, lastCell));
        }
        return superwitness;
    }
}
