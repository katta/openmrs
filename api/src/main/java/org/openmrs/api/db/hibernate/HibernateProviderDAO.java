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
package org.openmrs.api.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Provider;
import org.openmrs.api.db.ProviderDAO;

/**
 * Hibernate specific Provider related functions. This class should not be used directly. All calls
 * should go through the {@link org.openmrs.api.ProviderService} methods.
 * 
 */
public class HibernateProviderDAO implements ProviderDAO {
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#getAllProviders()
	 */
	@Override
	public List<Provider> getAllProviders() {
		Session session = sessionFactory.getCurrentSession();
		List<Provider> providers = session.createQuery("from Provider where retired=0").list();
		return providers;
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#saveProvider(org.openmrs.Provider)
	 */
	public Provider saveProvider(Provider provider) {
		sessionFactory.getCurrentSession().saveOrUpdate(provider);
		return provider;
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#deleteProvider(org.openmrs.Provider)
	 */
	@Override
	public void deleteProvider(Provider provider) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().delete(provider);
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#getProvider(java.lang.Integer)
	 */
	@Override
	public Provider getProvider(Integer id) {
		return (Provider) sessionFactory.getCurrentSession().load(Provider.class, id);
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#getProviderUuid(java.lang.String)
	 */
	@Override
	public Provider getProviderUuid(String uuid) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Provider.class);
		criteria.add(Restrictions.eq("uuid", uuid));
		return (Provider) criteria.uniqueResult();
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#getCountOfProviders(java.lang.String)
	 */
	
	@Override
	public Integer getCountOfProviders(String sqlQuery) {
		Query query = sessionFactory.getCurrentSession().createQuery(sqlQuery);
		List list = query.list();
		return list != null ? list.size() : 0;
	}
	
	/**
	 * @see org.openmrs.api.db.ProviderDAO#getProviders(java.lang.String, java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public List<Provider> getProviders(String query, Integer start, Integer length) {
		Query hibernateQuery = sessionFactory.getCurrentSession().createQuery(query);
		hibernateQuery.setFirstResult((start - 1) * length);
		hibernateQuery.setMaxResults(length);
		return hibernateQuery.list();
		
	}
}
