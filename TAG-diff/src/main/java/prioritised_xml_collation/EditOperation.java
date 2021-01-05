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
import java.util.List;

/*
 * Created by Ronald
 * on 16/04/2018
 */
//NOTE: question could this be an Enum?
//NOTE: Or does it have to be an Object because it has instance data?
//NOTE: I would think so.
//NOTE: Then it becomes very much like Segment
//TODO: Add content type?
public class EditOperation {
    private final List<TAGToken> editOperationTokensA;
    private final List<TAGToken> editOperationTokensB;
    private final Type type;

    EditOperation(List<TAGToken> editOperationTokensA, List<TAGToken> editOperationTokensB, Type type) {
        this.editOperationTokensA = editOperationTokensA;
        this.editOperationTokensB = editOperationTokensB;
        this.type = type;
    }


    public enum Type {
        aligned, replacement, addition, omission
    }

    @Override
    public String toString() {
        return type+": "+editOperationTokensA.toString() + ": "+editOperationTokensB.toString();
    }
}
