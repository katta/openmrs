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
 * @author zabilcm
 */
public interface ProviderDAO {
	
	/**
	 * @return gets all Providers
	 */
	List<Provider> getAllProviders();
	
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
	
	public Provider getProviderUuid(String uuid);
	
	/**
	 * @param query
	 * @return Integer gets the count of Providers based on a query
	 */
	
	public Integer getCountOfProviders(String query);
	
	/**
	 * @param query ,start,length
	 * @return Integer gets the list of Providers based on a query and start position and length for
	 *         Pagination
	 */
	public List<Provider> getProviders(String query, Integer start, Integer length);
	
}
