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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.GlobalProperty;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.ProviderAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.test.annotation.ExpectedException;

/**
 * This test class (should) contain tests for all of the ProviderService
 * 
 * @see org.openmrs.api.ProviderService
 */
public class ProviderServiceTest extends BaseContextSensitiveTest {
	
	private static final String PROVIDERS_INITIAL_XML = "org/openmrs/api/include/ProviderServiceTest-initial.xml";
	
	private static final String PROVIDER_ATTRIBUTE_TYPES_XML = "org/openmrs/api/include/ProviderServiceTest-providerAttributes.xml";
	
	private ProviderService service;
	
	@Before
	public void before() throws Exception {
		service = Context.getProviderService();
		executeDataSet(PROVIDERS_INITIAL_XML);
		executeDataSet(PROVIDER_ATTRIBUTE_TYPES_XML);
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes(boolean)
	 * @verifies get all provider attribute types excluding retired
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesExcludingRetired() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes(false);
		assertEquals(1, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes(boolean)
	 * @verifies get all provider attribute types including retired
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesIncludingRetired() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes(true);
		assertEquals(2, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes()
	 * @verifies get all provider attribute types including retired by default
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesIncludingRetiredByDefault() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes();
		assertEquals(2, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviders()
	 * @verifies get all providers
	 */
	@Test
	public void getAllProviders_shouldGetAllProviders() throws Exception {
		List<Provider> providers = service.getAllProviders();
		assertEquals(8, providers.size());
	}
	
	/**
	 * @see ProviderService#getAllProviders(boolean)
	 * @verifies get all providers that are unretired
	 */
	@Test
	public void getAllProviders_shouldGetAllProvidersThatAreUnretired() throws Exception {
		List<Provider> providers = service.getAllProviders(false);
		assertEquals(7, providers.size());
	}
	
	/**
	 * @see ProviderService#getCountOfProviders(String)
	 * @verifies fetch number of provider matching given query.
	 */
	@Test
	public void getCountOfProviders_shouldFetchNumberOfProviderMatchingGivenQuery() throws Exception {
		assertEquals(1, service.getCountOfProviders("Hippo").intValue());
		Person person = Context.getPersonService().getPerson(502);
		Set names = person.getNames();
		for (Iterator iterator = names.iterator(); iterator.hasNext();) {
			PersonName name = (PersonName) iterator.next();
			name.setVoided(true);
			
		}
		PersonName personName = new PersonName("Hippot", "A", "B");
		personName.setPreferred(true);
		person.addName(personName);
		Context.getPersonService().savePerson(person);
		assertEquals(1, service.getCountOfProviders("Hippo").intValue());
	}
	
	/**
	 * @see ProviderService#getProvider(Integer)
	 * @verifies get provider given ID
	 */
	@Test
	public void getProvider_shouldGetProviderGivenID() throws Exception {
		Provider provider = service.getProvider(1);
		assertEquals("RobertClive", provider.getName());
		assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", provider.getUuid());
	}
	
	/**
	 * @see ProviderService#getProviderAttributeType(Integer)
	 * @verifies get provider attribute type for the given id
	 */
	@Test
	public void getProviderAttributeType_shouldGetProviderAttributeTypeForTheGivenId() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(1);
		assertEquals("Audit Date", providerAttributeType.getName());
		assertEquals("9516cc50-6f9f-11e0-8414-001e378eb67e", providerAttributeType.getUuid());
	}
	
	/**
	 * @see ProviderService#getProviderAttributeTypeByUuid(String)
	 * @verifies get the provider attribute type by it's uuid
	 */
	@Test
	public void getProviderAttributeTypeByUuid_shouldGetTheProviderAttributeTypeByItsUuid() throws Exception {
		ProviderAttributeType providerAttributeType = service
		        .getProviderAttributeTypeByUuid("9516cc50-6f9f-11e0-8414-001e378eb67e");
		assertEquals("Audit Date", providerAttributeType.getName());
	}
	
	/**
	 * @see ProviderService#getProviderbyUuid(String)
	 * @verifies get provider given Uuid
	 */
	@Test
	public void getProviderbyUuid_shouldGetProviderGivenUuid() throws Exception {
		Provider provider = service.getProviderbyUuid("a2c3868a-6b90-11e0-93c3-18a905e044dc");
		Assert.assertNotNull(provider);
		assertEquals("RobertClive", provider.getName());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies force search string to be greater than minsearchcharacters
	 *           global property
	 */
	@Test
	public void getProviders_shouldForceSearchStringToBeGreaterThanMinsearchcharactersGlobalProperty() throws Exception {
		assertEquals(2, service.getProviders("Ron", 0, null).size());
		Context.clearSession();
		Context.getAdministrationService().saveGlobalProperty(
		    new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, "4"));
		
		assertEquals(0, service.getProviders("Ron", 0, null).size());
		assertEquals(2, service.getProviders("Rona", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies fetch provider with given identifier with case in sensitive
	 */
	@Test
	public void getProviders_shouldFetchProviderWithGivenIdentifierWithCaseInSensitive() throws Exception {
		assertEquals(2, service.getProviders("8c7", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies fetch provider with given name with case in sensitive
	 */
	@Test
	public void getProviders_shouldFetchProviderWithGivenNameWithCaseInSensitive() throws Exception {
		Provider provider = new Provider();
		provider.setName("Catherin");
		service.saveProvider(provider);
		assertEquals(1, service.getProviders("Cath", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies not fail when minimum search characters is null
	 */
	@Test
	public void getProviders_shouldNotFailWhenMinimumSearchCharactersIsNull() throws Exception {
		Context.clearSession();
		Context.getAdministrationService().saveGlobalProperty(
		    new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, null));
		assertEquals(2, service.getProviders("RON", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies not fail when minimum search characters is invalid integer
	 */
	@Test
	public void getProviders_shouldNotFailWhenMinimumSearchCharactersIsInvalidInteger() throws Exception {
		Context.clearSession();
		Context.getAdministrationService().saveGlobalProperty(
		    new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, "A"));
		assertEquals(1, service.getProviders("ROg", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies fetch provider by matching query string with any unVoided
	 *           PersonName's Given Name
	 */
	@Test
	public void getProviders_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesGivenName() throws Exception {
		assertEquals(1, service.getProviders("COL", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies fetch provider by matching query string with any unVoided
	 *           PersonName's middleName
	 */
	@Test
	public void getProviders_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesMiddleName() throws Exception {
		assertEquals(4, service.getProviders("Tes", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies fetch provider by matching query string with any unVoided
	 *           Person's familyName
	 */
	@Test
	public void getProviders_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonsFamilyName() throws Exception {
		assertEquals(2, service.getProviders("Che", 0, null).size());
	}
	
	/**
	 * @see ProviderService#getProviders(String,Integer,Integer)
	 * @verifies not fetch provider if the query string matches with any voided
	 *           Person name for that
	 */
	@Test
	public void getProviders_shouldNotFetchProviderIfTheQueryStringMatchesWithAnyVoidedPersonNameForThat() throws Exception {
		assertEquals(0, service.getProviders("Hit", 0, null).size());
		assertEquals(1, service.getProviders("coll", 0, null).size());
	}
	
	/**
	 * @see ProviderService#purgeProvider(Provider)
	 * @verifies delete a provider
	 */
	@Test
	public void purgeProvider_shouldDeleteAProvider() throws Exception {
		Provider provider = service.getProvider(1);
		service.purgeProvider(provider);
		assertEquals(7, Context.getProviderService().getAllProviders().size());
	}
	
	/**
	 * @see ProviderService#purgeProviderAttributeType(ProviderAttributeType)
	 * @verifies delete a provider attribute type
	 */
	@Test
	public void purgeProviderAttributeType_shouldDeleteAProviderAttributeType() throws Exception {
		int size = service.getAllProviderAttributeTypes().size();
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(1);
		service.purgeProviderAttributeType(providerAttributeType);
		assertEquals(size - 1, service.getAllProviderAttributeTypes().size());
	}
	
	/**
	 * @see ProviderService#retireProvider(Provider,String)
	 * @verifies retire a provider
	 */
	@Test
	public void retireProvider_shouldRetireAProvider() throws Exception {
		Provider provider = service.getProvider(1);
		assertFalse(provider.isRetired());
		assertNull(provider.getRetireReason());
		service.retireProvider(provider, "retire reason");
		assertTrue(provider.isRetired());
		assertEquals("retire reason", provider.getRetireReason());
		assertEquals(6, service.getAllProviders(false).size());
	}
	
	/**
	 * @see ProviderService#retireProviderAttributeType(ProviderAttributeType,String)
	 * @verifies retire provider type attribute
	 */
	@Test
	public void retireProviderAttributeType_shouldRetireProviderTypeAttribute() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(1);
		assertFalse(providerAttributeType.isRetired());
		assertNull(providerAttributeType.getRetireReason());
		service.retireProviderAttributeType(providerAttributeType, "retire reason");
		assertTrue(providerAttributeType.isRetired());
		assertEquals("retire reason", providerAttributeType.getRetireReason());
		assertEquals(0, service.getAllProviderAttributeTypes(false).size());
	}
	
	/**
	 * @see ProviderService#saveProvider(Provider)
	 * @verifies save a Provider with provider name alone
	 */
	@Test
	public void saveProvider_shouldSaveAProviderWithProviderNameAlone() throws Exception {
		Provider provider = new Provider();
		provider.setName("Provider9");
		service.saveProvider(provider);
		Assert.assertNotNull(provider.getId());
		Assert.assertNotNull(provider.getUuid());
		Assert.assertNotNull(provider.getCreator());
		Assert.assertNotNull(provider.getDateCreated());
		Assert.assertEquals("Provider9", provider.getName());
	}
	
	/**
	 * @see ProviderService#saveProvider(Provider)
	 * @verifies save a Provider with Person alone
	 */
	@Test
	public void saveProvider_shouldSaveAProviderWithPersonAlone() throws Exception {
		Provider provider = new Provider();
		Person person = Context.getPersonService().getPerson(new Integer(999));
		provider.setPerson(person);
		service.saveProvider(provider);
		Assert.assertNotNull(provider.getId());
		Assert.assertNotNull(provider.getUuid());
		Assert.assertNotNull(provider.getCreator());
		Assert.assertNotNull(provider.getDateCreated());
		Assert.assertEquals(999, provider.getPerson().getId().intValue());
		
	}
	
	/**
	 * @see ProviderService#saveProviderAttributeType(ProviderAttributeType)
	 * @verifies save the provider attribute type
	 */
	@Test
	public void saveProviderAttributeType_shouldSaveTheProviderAttributeType() throws Exception {
		int size = service.getAllProviderAttributeTypes().size();
		ProviderAttributeType providerAttributeType = new ProviderAttributeType();
		providerAttributeType.setName("new");
		providerAttributeType.setDatatype("string");
		providerAttributeType = service.saveProviderAttributeType(providerAttributeType);
		assertEquals(size + 1, service.getAllProviderAttributeTypes().size());
		assertNotNull(providerAttributeType.getId());
	}
	
	/**
	 * @see ProviderService#unretireProvider(Provider)
	 * @verifies unretire a provider
	 */
	@Test
	public void unretireProvider_shouldUnretireAProvider() throws Exception {
		Provider provider = service.getProvider(1);
		service.unretireProvider(provider);
		assertFalse(provider.isRetired());
		assertNull(provider.getRetireReason());
	}
	
	/**
	 * @see ProviderService#unretireProviderAttributeType(ProviderAttributeType)
	 * @verifies unretire a provider attribute type
	 */
	@Test
	public void unretireProviderAttributeType_shouldUnretireAProviderAttributeType() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(2);
		assertTrue(providerAttributeType.isRetired());
		service.unretireProviderAttributeType(providerAttributeType);
		assertFalse(providerAttributeType.isRetired());
		assertNull(providerAttributeType.getRetireReason());
	}
	
}