/**
 * Copyright 2013 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 the "License";
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
**/

package io.neba.core.util;

import static org.springframework.util.Assert.notNull;

import org.apache.commons.collections.Predicate;
import org.osgi.framework.Bundle;

/**
 * {@link #evaluate(Object)} returns <code>false</code> if the bundle matches to
 * signal
 * {@link org.apache.commons.collections.CollectionUtils#filter(java.util.Collection, Predicate)}
 * to remove the element it.
 * 
 * Works only on collections of {@link OsgiBeanSource}.
 * 
 * @author Olaf Otto
 */
public class MatchedBundlesPredicate implements Predicate {
    private final Bundle bundle;
    private int filteredElements = 0;

    public MatchedBundlesPredicate(Bundle bundle) {
        notNull(bundle, "Constructor parameter bundle must not be null.");
        this.bundle = bundle;
    }

    @Override
    public boolean evaluate(Object object) {
        boolean keep = ((OsgiBeanSource<?>) object).getBundleId() != bundle.getBundleId();
        if (!keep) {
            ++filteredElements;
        }
        return keep;
    }

    public int getFilteredElements() {
        return filteredElements;
    }
}
