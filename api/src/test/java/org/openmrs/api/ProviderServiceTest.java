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

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.GlobalProperty;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.ProviderAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;
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
	
	@Test
	@Verifies(value = "should get all providers", method = "getAllProviders()")
	public void getAllProviders_shouldGetAllProviders() {
		List<Provider> providers = service.getAllProviders();
		assertEquals(8, providers.size());
	}
	
	@Test
	@Verifies(value = "should get all providers that are unretired", method = "getAllProviders(boolean includeRetire)")
	public void getAllUnRetiredProviders_shouldGetAllUnRetiredProviders() {
		List<Provider> providers = service.getAllProviders(false);
		assertEquals(7, providers.size());
	}
	
	@Test
	@Verifies(value = "should save a  Provider with provider name alone", method = "saveProvider(Provider provider)")
	public void should_saveProviderWithNameAlone() throws Exception {
		Provider provider = new Provider();
		provider.setName("Provider9");
		service.saveProvider(provider);
		Assert.assertNotNull(provider.getId());
		Assert.assertNotNull(provider.getUuid());
		Assert.assertNotNull(provider.getCreator());
		Assert.assertNotNull(provider.getDateCreated());
		Assert.assertEquals("Provider9", provider.getName());
	}
	
	@Test
	@Verifies(value = "should save a  Provider with Person alone", method = "saveProvider(Provider provider)")
	public void should_saveProviderWithPersonAlone() throws Exception {
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
	
	@Test
	@Verifies(value = "should not save a  Provider with both name and person", method = "saveProvider(Provider provider)")
	@ExpectedException(Exception.class)
	public void should_notSaveProviderWithNameAndPerson() throws Exception {
		Provider provider = new Provider();
		provider.setName("Provider9");
		Person person = Context.getPersonService().getPerson(new Integer(999));
		provider.setPerson(person);
		service.saveProvider(provider);
	}
	
	@Test
	@Verifies(value = "should not save a  Provider with both name and person being  null", method = "saveProvider(Provider provider)")
	@ExpectedException(Exception.class)
	public void should_notSaveProviderWithNameAndPersonBothNull() throws Exception {
		Provider provider = new Provider();
		service.saveProvider(provider);
	}
	
	@Test
	@Verifies(value = "should retire a  Provider", method = "retireProvider(Provider provider, String reason)")
	public void should_retireProvider() {
		Provider provider = service.getProvider(1);
		assertFalse(provider.isRetired());
		assertNull(provider.getRetireReason());
		service.retireProvider(provider, "retire reason");
		assertTrue(provider.isRetired());
		assertEquals("retire reason", provider.getRetireReason());
		assertEquals(6, service.getAllProviders(false).size());
		
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
		assertEquals(7, Context.getProviderService().getAllProviders().size());
	}
	
	@Test
	@Verifies(value = "should get provider given ID", method = "getProvider(Integer id)")
	public void should_getProvider() {
		Provider provider = service.getProvider(1);
		assertEquals("RobertClive", provider.getName());
		assertEquals("a2c3868a-6b90-11e0-93c3-18a905e044dc", provider.getUuid());
	}
	
	@Test
	@Verifies(value = "should get provider given Uuid", method = "getProvider(String uuid)")
	public void should_getProviderByUuid() {
		Provider provider = service.getProviderbyUuid("a2c3868a-6b90-11e0-93c3-18a905e044dc");
		Assert.assertNotNull(provider);
		assertEquals("RobertClive", provider.getName());
	}
	
	@Test
	@Verifies(value = "should force search string to be greater than minsearchcharacters global property", method = "getProvider(String)")
	public void getPatients_shouldForceSearchStringToBeGreaterThanMinsearchcharactersGlobalProperty() throws Exception {
		// make sure we can get patients with the default of 3
		assertEquals(2, service.getProvider("Ron").size());
		
		Context.clearSession();
		Context.getAdministrationService().saveGlobalProperty(
		    new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, "4"));
		
		assertEquals(0, service.getProvider("Ron").size());
		assertEquals(2, service.getProvider("Rona").size());
	}
	
	@Test
	@Verifies(value = "should fetch provider with given identifier with case in sensitive", method = "getProvider(String)")
	public void getProvider_shouldFetchProviderWithGivenIdentifierWithCaseInSensitive() throws Exception {
		assertEquals(2, service.getProvider("8c7").size());
	}
	
	@Test
	@Verifies(value = "should fetch provider with given name with case in sensitive", method = "getProvider(String)")
	public void getProvider_shouldFetchProviderWithGivenNameWithCaseInSensitive() throws Exception {
		assertEquals(2, service.getProvider("RON").size());
	}
	
	@Test
	@Verifies(value = "should not fail when minimum search characters is null", method = "getProvider(String)")
	public void getProvider_shouldNotFailWhenMinimumSearchCharactersIsNull() throws Exception {
		Context.clearSession();
		Context.getAdministrationService().saveGlobalProperty(
		    new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, null));
		assertEquals(2, service.getProvider("RON").size());
	}
	
	@Test
	@Verifies(value = "not fail when minimum search characters is invalid integer", method = "getProvider(String)")
	public void getProvider_shouldNotFailWhenMinimumSearchCharactersIsInvalid() throws Exception {
		Context.clearSession();
		Context.getAdministrationService().saveGlobalProperty(
		    new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_MIN_SEARCH_CHARACTERS, "A"));
		assertEquals(1, service.getProvider("ROg").size());
	}
	
	@Test
	@Verifies(value = "should fetch provider by matching query string with any unVoided PersonName's Given Name ", method = "getProvider(String)")
	public void getProvider_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesGivenName() throws Exception {
		assertEquals(1, service.getProvider("COL").size());
	}
	
	@Test
	@Verifies(value = "should fetch provider by matching query string with any unVoided PersonName's middleName", method = "getProvider(String)")
	public void getProvider_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesMiddleName() throws Exception {
		assertEquals(4, service.getProvider("Tes").size());
	}
	
	@Test
	@Verifies(value = "should fetch provider by matching query string with any unVoided Person's familyName", method = "getProvider(String)")
	public void getProvider_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesFamilyName() throws Exception {
		assertEquals(2, service.getProvider("Che").size());
	}
	
	@Test
	@Ignore
	@Verifies(value = "should fetch provider by matching query string with any unVoided Person's familyName2", method = "getProvider(String)")
	public void getProvider_shouldFetchProviderByMatchingQueryStringWithAnyUnVoidedPersonNamesFamilyName2() throws Exception {
		assertEquals(1, service.getProvider("ric").size());
	}
	
	@Test
	@Verifies(value = "should not fetch provider if the query string matches with any voided Person name for that provider", method = "getProvider(String)")
	public void getProvider_shouldNotFetchProviderIfQueryStringMatchesWithAnyVoidedPersonName() throws Exception {
		assertEquals(0, service.getProvider("Hit").size());
		assertEquals(1, service.getProvider("coll").size());
	}
	
	@Test
	@Verifies(value = "should fetch number of provider matching given query with provider person Name", method = "getCountOfProvider(String query)")
	public void getCountOfProvider_shouldFetchNumberOfProviderMatchingGivenQueryWithProviderPersonName() {
		assertEquals(1, service.getCountOfProviders("Hippo").intValue());
		Person person = Context.getPersonService().getPerson(502);
		person.addName(new PersonName("Hippot", "A", "B"));
		Context.getPersonService().savePerson(person);
		assertEquals(1, service.getCountOfProviders("Hippo").intValue());
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
	 * @see ProviderService#unretireProviderAttributeType(ProviderAttributeType)
	 * @verifies unretire a provider attribute type
	 */
	@Test
	public void unretireProviderAttributeType_shouldUnretireAProviderAttributeType() throws Exception {
		ProviderAttributeType providerAttributeType = service.getProviderAttributeType(1);
		service.unretireProviderAttributeType(providerAttributeType);
		assertFalse(providerAttributeType.isRetired());
		assertNull(providerAttributeType.getRetireReason());
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
	 * @see ProviderService#getAllProviderAttributeTypes()
	 * @verifies get all provider attribute types including retired
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesIncludingRetired() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes();
		assertEquals(2, types.size());
	}
	
	/**
	 * @see ProviderService#getAllProviderAttributeTypes(Boolean)
	 * @verifies get all provider attribute types excluding retired
	 */
	@Test
	public void getAllProviderAttributeTypes_shouldGetAllProviderAttributeTypesExcludingRetired() throws Exception {
		List<ProviderAttributeType> types = service.getAllProviderAttributeTypes(false);
		assertEquals(1, types.size());
	}
	
}
