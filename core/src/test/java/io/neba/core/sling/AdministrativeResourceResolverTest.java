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

package io.neba.core.sling;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Olaf Otto
 */
@RunWith(MockitoJUnitRunner.class)
public class AdministrativeResourceResolverTest {
    @Mock
    private ResourceResolverFactory factory;
    @Mock
    private ResourceResolver resolver;
    @Mock
    private SlingHttpServletRequest request;

    private AdministrativeResourceResolver testee;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        this.testee = new AdministrativeResourceResolver();

        doReturn(this.resolver).when(this.factory).getAdministrativeResourceResolver((Map<String, Object>) any());
        doReturn(true).when(this.resolver).isLive();
    }

    @Test(expected = IllegalStateException.class)
    public void testHandlingOfUninitializedResourceResolverFactoryInResolve() throws Exception {
        resolve("/junit/test");
    }

    @Test(expected = IllegalStateException.class)
    public void testHandlingOfUninitializedResourceResolverFactoryInGet() throws Exception {
        get("/junit/test");
    }

    @Test(expected = IllegalStateException.class)
    public void testHandlingOfUninitializedResourceResolverFactoryInGetResolver() throws Exception {
        getResolver();
    }

    @Test
    public void testHandlingOfResourceResolverFactoryChange() throws Exception {
        bindFactory();
        verifyAdministrativeResourceResolverIsObtainedOnce();
        verifyResolverIsNotClosed();

        unbindFactory();
        verifyResolverIsClosed();
    }

    @Test
    public void testHandlingOfUnbindingWhenResolverIsClosed() throws Exception {
        withClosedResourceResolver();
        unbindFactory();
        verifyResolverIsNotClosed();
    }

    @Test
    public void testDelegationOfResolveWithPathOnly() throws Exception {
        bindFactory();

        resolve("/junit/test");

        verifyAdministrativeResolverResolves("/junit/test");
    }

    @Test
    public void testDelegationOfResolveWithPathAndRequest() throws Exception {
        bindFactory();

        resolveWithRequest("/junit/test");

        verifyAdministrativeResolverResolvesWithRequest("/junit/test");
    }

    @Test
    public void testDelegationOfGet() throws Exception {
        bindFactory();

        get("/junit/test");

        verifyAdministrativeResolverGets("/junit/test");
    }

    private void verifyAdministrativeResolverGets(String path) {
        verify(this.resolver).getResource(path);
    }

    private void verifyAdministrativeResolverResolvesWithRequest(String path) {
        verify(this.resolver).resolve(this.request, path);
    }

    private void resolveWithRequest(String path) {
        this.testee.resolve(this.request, path);
    }

    private void verifyAdministrativeResolverResolves(String path) {
        verify(this.resolver).resolve(path);
    }

    private void withClosedResourceResolver() {
        doReturn(false).when(this.resolver).isLive();
    }

    private void verifyResolverIsClosed() {
        verify(this.resolver).close();
    }

    private void verifyResolverIsNotClosed() {
        verify(this.resolver, never()).close();
    }

    @SuppressWarnings("unchecked")
    private void verifyAdministrativeResourceResolverIsObtainedOnce() throws LoginException {
        verify(this.factory).getAdministrativeResourceResolver((Map<String, Object>) any());
    }

    private void unbindFactory() {
        this.testee.unbind(this.factory);
    }

    private void bindFactory() throws LoginException {
        this.testee.bind(this.factory);
    }

    private void resolve(String path) {
        this.testee.resolve(path);
    }

    private void get(String path) {
        this.testee.get(path);
    }

    private void getResolver() {
        this.testee.getResolver();
    }
}
