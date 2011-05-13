package org.openmrs.web.dwr;

import org.openmrs.Person;
import org.openmrs.Provider;

public class ProviderListItem {
	
	private String providerName;
	
	private String identifier;
	
	private String givenName;
	
	private String middleName;
	
	private String familyName;
	
	private Integer providerId;
	
	public ProviderListItem(Provider provider) {
		Person person = provider.getPerson();
		
		if (person != null) {
			this.givenName = person.getGivenName();
			this.middleName = person.getMiddleName();
			this.familyName = person.getFamilyName();
		}
		this.providerId = provider.getId();
		this.providerName = provider.getName();
		this.identifier = provider.getIdentifier();
		
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
