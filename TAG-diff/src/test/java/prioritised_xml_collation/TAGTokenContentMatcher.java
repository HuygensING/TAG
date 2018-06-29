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
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.collection.IsIterableContainingInOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronald Haentjens Dekker on 27/01/17.
 * Original created by Elli Bleeker.
 */
public class TAGTokenContentMatcher extends BaseMatcher<TAGToken> {
    final String expectedContent;

    private TAGTokenContentMatcher(String expectedContent) {
        this.expectedContent = expectedContent;
    }

    @Override
    public boolean matches(Object o) {
        if (!(o instanceof TAGToken)) {
            return false;
        }
        TAGToken other = (TAGToken) o;
        return this.expectedContent.equals(other.content);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.expectedContent);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("Expected is: "+this.expectedContent+" but actual item is class: "+item.getClass().getName());
        if (item instanceof TAGToken) {
            description.appendText(" and content is: "+((TAGToken)item).content);
        }
    }

    static TAGTokenContentMatcher t(String expectedContent) {
        return new TAGTokenContentMatcher(expectedContent);
    }

    static Matcher<Iterable> containsTokens(String... content) {
        List<Matcher<TAGToken>> tokenMatchers = new ArrayList<>();
        for (String t : content) {
            TAGTokenContentMatcher matcher = new TAGTokenContentMatcher(t);
            tokenMatchers.add(matcher);
        }
        return new IsIterableContainingInOrder(tokenMatchers);
    }
}
