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

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Olaf Otto
 */
public class ConcurrentMultiValueMapTest {
    private ConcurrentDistinctMultiValueMap<String, String> testee;

    @Before
    public void prepareMap() {
        this.testee = new ConcurrentDistinctMultiValueMap<String, String>();
    }
    
    @Test
    public void testShallowCopyContents() throws Exception {
        withEntry("a", "v1");
        withEntry("a", "v2");
        withEntry("b", "v1");
        withEntry("b", "v2");
        
        Map<String, Collection<String>> copy = this.testee.getContents();
        
        assertThat(copy)
                  .isNotNull()
                  .hasSize(2)
                  .includes(entry("a", v("v1", "v2")))
                  .includes(entry("b", v("v1", "v2")));
    }
    
    @Test
    public void testShallowCopyModificationsAreIndependent() throws Exception {
        withEntry("a", "v1");
        withEntry("a", "v2");
        withEntry("b", "v1");
        withEntry("b", "v2");

        Map<String, Collection<String>> copy1 = this.testee.getContents();

        copy1.remove("a");

        assertThat(copy1)
        .isNotNull()
        .hasSize(1)
        .includes(entry("b", v("v1", "v2")));

        Map<String, Collection<String>> copy2 = this.testee.getContents();

        assertThat(copy2)
        .isNotNull()
        .hasSize(2)
        .includes(entry("a", v("v1", "v2")))
        .includes(entry("b", v("v1", "v2")));
        
        copy1.get("b").add("v3");
        
        assertThat(copy1).includes(entry("b", v("v1", "v2", "v3")));
        assertThat(copy2).excludes(entry("b", v("v1", "v2", "v3")));

    }

    private void withEntry(String key, String value) {
        this.testee.put(key, value);
    }

    private <K> Collection<K> v(K... ks) {
        return Arrays.asList(ks);
    }
}
