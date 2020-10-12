package prioritised_xml_collation;

import java.util.regex.Pattern;

import static java.lang.String.format;

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
public class TAGToken {
  public final String content;
  public final String normalizedContent;

  TAGToken(String content) {
    this.content = content;
    this.normalizedContent = content.trim();
  }

  //TODO: this can be done more efficiently!
  Token.Type getType() {
    Pattern punctuationPattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);
    Pattern contentPattern = Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS);
    boolean punctuationType = punctuationPattern.matcher(content).matches();
    boolean contentType = (contentPattern.matcher(normalizedContent).matches() && this instanceof TextToken);
    boolean markupType = (this instanceof MarkupOpenToken || this instanceof MarkupCloseToken);
    if (punctuationType) {
      return Token.Type.punctuation;
    } else if (contentType) {
      return Token.Type.text;
    } else if (markupType) {
      return Token.Type.markup;
    }
    throw new RuntimeException(format("Type could not be determined for token with %s and content \"%s\"",
        this.getClass(), this.content));
  }

  //TODO: handle milestones as single tokens (in tokenizer and with special token class)

}
