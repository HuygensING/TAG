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
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Ported from code written by Elli Bleeker.
 * This class was originally called Token, but that would conflict with the Token interface in CollateX.
 */
public class XMLToken {
    public String content;

    public XMLToken(String content) {
        this.content = content;
    }

    //TODO: this can be done more efficiently!
    Token.Type getType() {
        boolean punctuationType = (content.matches("\\W+"));
        boolean contentType = (content.matches("\\w+") && this instanceof TextToken);
        boolean markupType = (this instanceof ElementToken);
        if (punctuationType) {
            return Token.Type.punctuation;
        } else if (contentType) {
            return Token.Type.text;
        } else if (markupType) {
            return Token.Type.markup;
        }
        throw new RuntimeException("Unknown token type!");
    }

    //TODO: whitespace handling

    //TODO: add generics

    //TODO: handle milestones as single tokens
}
