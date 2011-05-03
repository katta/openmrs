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
package org.openmrs.api.impl;

import java.util.List;

import org.openmrs.Provider;
import org.openmrs.api.ProviderService;
import org.openmrs.api.db.ProviderDAO;

/**
 * Default implementation of the {@link ProviderService}. This class should not be used on its own.
 * The current OpenMRS implementation should be fetched from the Context.
 * 
 * @since 1.9
 */
public class ProviderServiceImpl extends BaseOpenmrsService implements ProviderService {
	
	private ProviderDAO dao;
	
	/**
	 * @see org.openmrs.api.ProviderService#setProviderDAO(org.openmrs.api.db.ProviderDAO )
	 */
	@Override
	public void setProviderDAO(ProviderDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#getAllProviders()
	 */
	@Override
	public List<Provider> getAllProviders() {
		return dao.getAllProviders();
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#retireProvider(org.openmrs.Provider, java.lang.String)
	 */
	public void retireProvider(Provider provider, String reason) {
		dao.saveProvider(provider);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#unretireProvider(org.openmrs.Provider)
	 */
	public Provider unretireProvider(Provider provider) {
		return dao.saveProvider(provider);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#purgeProvider(org.openmrs.Provider)
	 */
	public void purgeProvider(Provider provider) {
		dao.deleteProvider(provider);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#getProvider(java.lang.Integer)
	 */
	@Override
	public Provider getProvider(Integer providerId) {
		return dao.getProvider(providerId);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#saveProvider(org.openmrs.Provider)
	 */
	@Override
	public Provider saveProvider(Provider provider) {
		return dao.saveProvider(provider);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#getProviderbyUuid(java.lang.String)
	 */
	@Override
	public Provider getProviderbyUuid(String uuid) {
		return dao.getProviderUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#getCountOfProviders(java.lang.String)
	 */
	@Override
	public Integer getCountOfProviders(String query) {
		return dao.getCountOfProviders(query);
	}
	
	/**
	 * @see org.openmrs.api.ProviderService#getProviders(java.lang.String, java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public List<Provider> getProviders(String query, Integer start, Integer length) {
		
		return dao.getProviders(query, start, length);
	}
	
}
