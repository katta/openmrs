/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;

/**
 * This test class (should) contain tests for all of the ProviderService
 * 
 * @see org.openmrs.api.ProviderService
 */
public class ProviderServiceTest extends BaseContextSensitiveTest {
	
	private static final String PROVIDERS_INITIAL_XML = "org/openmrs/api/include/ProviderServiceTest-initial.xml";
	
	private ProviderService service;
	
	@Before
	public void before() throws Exception {
		service = Context.getProviderService();
		executeDataSet(PROVIDERS_INITIAL_XML);
	}
	
	@Test
	@Verifies(value = "should get all providers", method = "getAllProviders()")
	public void getAllProviders_shouldGetAllProviders() {
		List<Provider> providers = service.getAllProviders();
		assertEquals(7, providers.size());
	}
	
	@Test
	@Verifies(value = "should save a  Provider", method = "saveProvider(Provider provider)")
	public void should_saveProvider() {
		Provider provider = new Provider();
		provider.setCreator(Context.getAuthenticatedUser());
		provider.setDateCreated(new Date());
		provider = service.saveProvider(provider);
		Assert.assertNotNull(provider.getId());
		Assert.assertNotNull(provider.getUuid());
		Assert.assertNotNull(provider.getCreator());
		Assert.assertNotNull(provider.getDateCreated());
		
	}
	
	@Test
	@Verifies(value = "should retire a  Provider", method = "retireProvider(Provider provider, String reason)")
	public void should_retireProvider() {
		Provider provider = service.getProvider(1);
		assertFalse(provider.isRetired());
		assertEquals(null, provider.getRetireReason());
		service.retireProvider(provider, "retire reason");
		assertTrue(provider.isRetired());
		assertEquals("retire reason", provider.getRetireReason());
		assertEquals(6, service.getAllProviders().size());
		
	}
	
	@Test
	@Verifies(value = "should unretire a  Provider", method = "unretireProvider(Provider provider)")
	public void should_unretireProvider() {
		Provider provider = service.getProvider(1);
		service.unretireProvider(provider);
		assertFalse(provider.isRetired());
		assertNull(provider.getRetireReason());
	}
	
	@Test
	@Verifies(value = "should delete a  provider", method = "purgeProvider(Provider provider)")
	public void should_purgeProvider() {
		Provider provider = service.getProvider(1);
		service.purgeProvider(provider);
		assertEquals(6, Context.getProviderService().getAllProviders().size());
	}
	
	@Test
	@Verifies(value = "should get provider given ID", method = "getProvider(Integer id)")
	public void should_getProvider() {
		Provider provider = service.getProvider(1);
		assertEquals("provider1", provider.getName());
		assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", provider.getUuid());
	}
	
	@Test
	@Verifies(value = "should get provider given Uuid", method = "getProvider(String uuid)")
	public void should_getProviderByUuid() {
		Provider provider = service.getProviderbyUuid("a2c3868a-6b90-11e0-93c3-18a905e044dc");
		Assert.assertNotNull(provider);
		assertEquals("provider1", provider.getName());
	}
	
	@Test
	@Verifies(value = "should getcount of provider given a query", method = "getCountOfProviders(String uuid)")
	public void should_getCountOfProviders() {
		assertEquals(8, service.getCountOfProviders("from Provider").intValue());
	}
	
	@Test
	@Verifies(value = "should get provider given a query and page no and size", method = "getProviders(String query,int start,int length)")
	public void should_getProviders() {
		List providers = service.getProviders("from Provider", 3, 2);
		assertEquals(2, providers.size());
		assertEquals(5, ((Provider) providers.get(0)).getId().intValue());
		assertEquals(6, ((Provider) providers.get(1)).getId().intValue());
		
	}
	
}
