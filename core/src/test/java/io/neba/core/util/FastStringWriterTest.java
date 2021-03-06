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

import org.junit.Before;
import org.junit.Test;

/**
 * @author Olaf Otto
 */
public class FastStringWriterTest {
	private FastStringWriter testee;

	@Before
	public void prepareWriter() {
		this.testee = new FastStringWriter();
	}

	@Test
	public void testStringComposition() throws Exception {
		this.testee.write('T');
		this.testee.append('h')
		           .append("e ")
		           .append("quick ", 0, 6);
		this.testee.write("brown ".toCharArray());
		this.testee.write("fox ".toCharArray(), 0, 4);
		this.testee.write("jumps over the lazy dog", 0, 23);
		
		assertWriterCreatesString("The quick brown fox jumps over the lazy dog");
	}

	private void assertWriterCreatesString(String value) {
		assertThat(this.testee.toString()).isEqualTo(value);
	}
}
