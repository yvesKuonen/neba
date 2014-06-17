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

package io.neba.core.mvc;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Supports {@link ResourceResolver} arguments of a {@link org.springframework.web.bind.annotation.RequestMapping}.
 * <br />
 *
 * Example:<br />
 * <p>
 * <pre>
 *  &#64;{@link org.springframework.web.bind.annotation.RequestMapping}(...)
 *  public void myHandlerMethod({@link ResourceResolver} resolver, ...) {
 *      ...
 *  }
 * </pre>
 * </p>
 *
 * @author Olaf Otto
 */
public class ResourceResolverArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return ResourceResolver.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                 NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object request = webRequest.getNativeRequest();
        if (request instanceof SlingHttpServletRequest) {
            return ((SlingHttpServletRequest) request).getResourceResolver();
        }
        return null;
    }
}
