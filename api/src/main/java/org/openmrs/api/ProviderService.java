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

import java.util.List;

import org.openmrs.Provider;
import org.openmrs.api.db.ProviderDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service contains methods relating to providers.
 * 
 * @since 1.9
 */
@Transactional
public interface ProviderService extends OpenmrsService {
	
	/**
	 * Gets all providers. includes retired Provider.This method delegates to the
	 * #getAllProviders(boolean) method
	 * 
	 * @return a list of provider objects.
	 * @should get all providers
	 */
	
	@Transactional(readOnly = true)
	public List<Provider> getAllProviders();
	
	/**
	 * Gets all Provider
	 * 
	 * @param includeRetired - whether or not to include retired Provider
	 * @should get all providers that are unretired
	 */
	@Transactional(readOnly = true)
	public List<Provider> getAllProviders(boolean includeRetired);
	
	/**
	 * Retires a given Provider
	 * 
	 * @param Provider provider to retire
	 * @param String reason why the provider is retired
	 * @should retire a provider
	 */
	public void retireProvider(Provider provider, String reason);
	
	/**
	 * Unretire a given Provider
	 * 
	 * @param Provider provider to unretire
	 * @should unretire a provider
	 */
	public Provider unretireProvider(Provider provider);
	
	/**
	 * Deletes a given Provider
	 * 
	 * @param Provider provider to be deleted
	 * @should delete a provider
	 */
	public void purgeProvider(Provider provider);
	
	/**
	 * Gets a provider by it's provider id
	 * 
	 * @param providerId the provider id
	 * @return the provider by it's id
	 * @should get provider given ID
	 */
	@Transactional(readOnly = true)
	public Provider getProvider(Integer providerId);
	
	/**
	 * @param provider
	 * @return the Provider object after saving it in the database
	 * @throws Exception
	 * @should save a Provider with provider name alone
	 * @should save a Provider with Person alone
	 * @should not save a Provider with both name and person
	 * @should not save a Provider with both name and person being null
	 */
	
	public Provider saveProvider(Provider provider) throws Exception;
	
	/**
	 * @param string
	 * @return the Provider object having the given uuid
	 * @should get provider given Uuid
	 */
	@Transactional(readOnly = true)
	public Provider getProviderbyUuid(String uuid);
	
	/**
	 * @param string
	 * @return list of Provider object matching the string
	 * @should should force search string to be greater than minsearchcharacters global property
	 */
	@Transactional(readOnly = true)
	public List<Provider> getProvider(String query);
	
	/**
	 * @param query
	 * @param start
	 * @param length
	 * @return the list of Providers given the query , current page and page length
	 * @should force search string to be greater than minsearchcharacters global property
	 * @should fetch provider with given identifier with case in sensitive
	 * @should fetch provider with given name with case in sensitive
	 * @should not fail when minimum search characters is null
	 * @should not fail when minimum search characters is invalid integer
	 * @should fetch provider by matching query string with any unVoided PersonName's Given Name
	 * @should fetch provider by matching query string with any unVoided PersonName's middleName
	 * @should fetch provider by matching query string with any unVoided Person's familyName
	 * @should not fetch provider if the query string matches with any voided Person name for that
	 *         Provider
	 */
	@Transactional(readOnly = true)
	public List<Provider> getProviders(String query, Integer start, Integer length);
	
	/**
	 * @param query
	 * @return Count-Integer
	 * @should fetch number of provider matching given query.
	 */
	@Transactional(readOnly = true)
	public Integer getCountOfProviders(String query);
	
}
