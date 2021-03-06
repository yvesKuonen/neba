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

package io.neba.api.tags;

import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;

import javax.servlet.jsp.tagext.TagSupport;

import static org.apache.sling.api.scripting.SlingBindings.SLING;

/**
 * @author Olaf Otto
 */
abstract class TagWithBindings extends TagSupport {
    protected SlingBindings getBindings() {
        return (SlingBindings) this.pageContext.getRequest().getAttribute(SlingBindings.class.getName());
    }

    protected SlingScriptHelper getScriptHelper() {
        SlingBindings bindings = getBindings();

        if (bindings == null) {
            throw new IllegalStateException("No " + SlingBindings.class.getName() +
                    " was found in the request, got null.");
        }

        SlingScriptHelper scriptHelper = (SlingScriptHelper) bindings.get(SLING);

        if (scriptHelper == null) {
            throw new IllegalStateException("No " + SlingScriptHelper.class.getName() +
                    " was found in the sling bindings, got null.");
        }
        return scriptHelper;
    }
}
