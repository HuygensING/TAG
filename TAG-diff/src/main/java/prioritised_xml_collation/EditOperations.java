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
import java.util.HashSet;
import java.util.Set;

/*
 * Created on 16/04/2018 by Ronald Haentjens Dekker
 * This code takes inspiration from the AlignmentAndTypeSegmenter.
 * Aligned stuff is ignored.
 */
public class EditOperations {
    public static Set<EditOperation> calculate(EditGraphTable editGraphTable) {
        Set<EditOperation> editOperations = new HashSet<>();
        // We set last cell to the first iterable cell. (lower right corner)
        Cell lastCell = editGraphTable.iterator().next();
        // For loop iterates cells of the editTable:
        // As long as the pointer can move up in the editTable
        // move one cell up
        for (Cell currentCell : editGraphTable) {
            // stateChange if cell is root or the alignment of the lastCell is not the same as the currentCell
            Boolean stateChange = currentCell.isRoot() || lastCell.match != currentCell.match;
            if (!stateChange) {
                // stateChange if type is different and we are within a modification.
                stateChange = !lastCell.match && editGraphTable.determineCellType(currentCell) != editGraphTable.determineCellType(lastCell);
            }
            if (stateChange) {
                // NOTE: we skip aligned segments.
                if (lastCell.match) {
                    lastCell = currentCell;
                    continue;
                }
                // create an EditOperation going from the lastCell to the currentCell
                EditOperation newEditOperation = editGraphTable.createEditOperationOfCells(lastCell, currentCell);
                System.out.println(newEditOperation);
                editOperations.add(newEditOperation);
                lastCell = currentCell;
            }
        }

        return editOperations;
    }
}
