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

package io.neba.core.blueprint;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Olaf Otto
 */
public class FelixTaskExecutorTest {
    private boolean executed = false;

    private FelixTaskExecutor testee = new FelixTaskExecutor();

    @Test
    public void testSynchronousExecution() throws Exception {
        execute(new Runnable() {
            @Override
            public void run() {
                executed = true;
            }
        });
        assertThat(this.executed).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHandlingOfNullTask() throws Exception {
        execute(null);
    }

    private void execute(Runnable task) {
        this.testee.execute(task);
    }
}
