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
	 * Sets the data access object for Concepts. The dao is used for saving and getting concepts
	 * to/from the database
	 * 
	 * @param dao The data access object to use
	 */
	public void setProviderDAO(ProviderDAO dao);
	
	/**
	 * Gets all providers.
	 * 
	 * @return a list of provider objects.
	 * @should get all providers
	 */
	
	@Transactional(readOnly = true)
	public List<Provider> getAllProviders();
	
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
	public Provider getProvider(Integer providerId);
	
	/**
	 * @param provider
	 * @return the Provider object after saving it in the database
	 * @should save a Provider
	 */
	
	public Provider saveProvider(Provider provider);
	
	/**
	 * @param string
	 * @return the Provider object having the given uuid
	 * @should get provider given Uuid
	 */
	public Provider getProviderbyUuid(String uuid);
	
	/**
	 * @param query
	 * @param start
	 * @param length
	 * @return the list of Providers given the query , current page and page length
	 * @should get provider given a query and page no and size
	 */
	public List<Provider> getProviders(String query, Integer start, Integer length);
	
	/**
	 * @param query
	 * @return Count-Integer
	 * @should getcount of provider given a query Return the Count of Providers for a given query.
	 */
	public Integer getCountOfProviders(String query);
	
}
