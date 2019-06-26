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
import java.util.ArrayList;
import java.util.List;

/*
 * AlignmentAndTypeSegmenter
 * The goal is two firs separate on Aligned / non-aligned.
 * If content is non-aligned we also separate on type.
 *
 * Created by Ronald Haentjens Dekker
 * on 09/04/2018
 * Work In Progress
 *
 */
public class AlignmentAndTypeSegmenter implements SegmenterInterface {
    @Override
    public List<Segment> calculateSegmentation(EditGraphTable editTable) {
        List<Segment> result = new ArrayList<>();
        // We set last cell to the first iterable cell. (lower right corner)
        Cell lastCell = editTable.iterator().next();
        // For loop iterates cells of the editTable:
        // As long as the pointer can move up in the editTable
        // move one cell up
        for (Cell currentCell : editTable) {
            // stateChange if cell is root or the alignment of the lastCell is not the same as the currentCell
            Boolean stateChange = currentCell.isRoot() || lastCell.match != currentCell.match;
            if (!stateChange) {
                // stateChange if type is different and we are within a modification.
                stateChange = !lastCell.match && editTable.determineCellType(currentCell) != editTable.determineCellType(lastCell);
            }
            if (stateChange) {
                Segment newSegment = editTable.createSegmentOfCells(currentCell, lastCell);
                result.add(0, newSegment);
                lastCell = currentCell;
            }
        }
        return result;
    }
}
