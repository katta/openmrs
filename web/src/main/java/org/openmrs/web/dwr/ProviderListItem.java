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
package org.openmrs.web.dwr;

import org.openmrs.Person;
import org.openmrs.Provider;

/**
 * A mini/simplified provider object. Used as the return object from DWR methods to allow javascript
 * and other consumers to easily use all methods. This class guarantees that all objects in this
 * class will be initialized (copied) off of the provider object.
 * 
 * @see Provider
 * @see DWRProviderService
 */
public class ProviderListItem {
	
	private String providerName;
	
	private String identifier;
	
	private String givenName;
	
	private String middleName;
	
	private String familyName;
	
	private String familyName2;
	
	private Integer providerId;
	
	public ProviderListItem(Provider provider) {
		Person person = provider.getPerson();
		
		if (person != null) {
			this.givenName = person.getPersonName().getGivenName();
			this.middleName = person.getPersonName().getMiddleName();
			this.familyName = person.getPersonName().getFamilyName();
			this.familyName2 = person.getPersonName().getFamilyName2();
		}
		this.providerId = provider.getId();
		this.providerName = provider.getName();
		this.identifier = provider.getIdentifier();
		
	}
	
	public String getFamilyName2() {
		return familyName2;
	}
	
	public void setFamilyName2(String familyName2) {
		this.familyName2 = familyName2;
	}
	
	public String getProviderName() {
		return providerName;
	}
	
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	public String getGivenName() {
		return givenName;
	}
	
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
}
