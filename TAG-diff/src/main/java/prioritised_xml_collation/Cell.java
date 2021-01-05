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
/**
 * Created by ellibleeker on 06/04/2017.
 */
public class Cell {
    public int globalScore = 0;
    int x;
    int y;
    int previousX;
    int previousY;
    Boolean match;

    Cell(Boolean match, int x, int y, Cell parent, int globalScore) {
        this.match = match;
        this.x = x;
        this.y = y;
        this.previousX = parent == null ? -1 : parent.x;
        this.previousY = parent == null ? -1 : parent.y;
        this.globalScore = globalScore;
    }

    @Override
    public String toString() {
        return "[" + this.y + "," + this.x + "]:" + this.globalScore;
    }

    public boolean movedHorizontal() {
        int differenceHorizontal = x - previousX;
        int differenceVertical = y - previousY;
        return differenceHorizontal != 0 && differenceVertical == 0;
    }

    public boolean movedVertical() {
        int differenceHorizontal = x - previousX;
        int differenceVertical = y - previousY;
        return differenceHorizontal == 0 && differenceVertical != 0;
    }

    public Boolean isRoot() {
        return x == 0 && y == 0;
    }

}
