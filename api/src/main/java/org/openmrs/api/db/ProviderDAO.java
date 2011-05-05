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
package org.openmrs.api.db;

import java.util.List;

import org.openmrs.Provider;

/**
 * Data  Access function for  Provider
 * @since 1.9
 */
public interface ProviderDAO {
	
	/**
	 * Gets all Providers
	 * @param includeRetired - whether or not to include retired Provider
	 */
	List<Provider> getAllProviders(boolean includeRetired);
	
	/**
	 * Saves/Updates a given Provider
	 */
	public Provider saveProvider(Provider provider);
	
	/**
	 * deletes an exisiting Provider
	 */
	public void deleteProvider(Provider provider);
	
	/**
	 * @param id
	 * @return Provider gets the Provider based on id
	 */
	public Provider getProvider(Integer id);
	
	/**
	 * @param uuid
	 * @return Provider gets the Provider based on uuid
	 */
	
	public Provider getProviderByUuid(String uuid);
	
	/**
	 * 
	 * @param identifier
	 * @param name
	 * @param start
	 * @param length
	 * @return List of  Providers
	 */
	public List<Provider> getProviders(String name, String identifier, Integer start, Integer length);
	
	/**
	 * 
	 * 
	 * @param name
	 * @param identifier
	 * @return Count of providers satisfying the given query
	 */
	public Integer getCountOfProviders(String name, String identifier);
	
}
