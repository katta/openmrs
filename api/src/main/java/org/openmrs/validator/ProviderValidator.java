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
package org.openmrs.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Provider;
import org.openmrs.annotation.Handler;
import org.openmrs.api.APIException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator for {@link Encounter} class
 * 
 * @since 1.9
 */
@Handler(supports = { Provider.class }, order = 50)
public class ProviderValidator implements Validator {
	
	private static final Log log = LogFactory.getLog(ProviderValidator.class);
	
	/**
	 * Returns whether or not this validator supports validating a given class.
	 * 
	 * @param c
	 *            The class to check for support.
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class c) {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + ".supports: " + c.getName());
		return Provider.class.isAssignableFrom(c);
	}
	
	/**
	 * Validates the given Provider. checks to see if a provider is valid
	 * (Either of Person or Provider name should be set and not both) Checks to
	 * see if there is a retired Reason in case a provider is retired
	 * 
	 * @param obj
	 *            The encounter to validate.
	 * @param errors
	 *            Errors
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 * @should fail if provider is retired and the retired reason is not mentioned
	 * @should fail if patient is not set
	 */
	public void validate(Object obj, Errors errors) throws APIException {
		if (log.isDebugEnabled())
			log.debug(this.getClass().getName() + ".validate...");
		
		if (obj == null || !(obj instanceof Provider))
			throw new IllegalArgumentException("The parameter obj should not be null and must be of type " + Provider.class);
		
		Provider provider = (Provider) obj;
		if (provider != null) {
			if (!(StringUtils.isEmpty(provider.getName()) ? provider.getPerson() != null : provider.getPerson() == null)) {
				errors.rejectValue("person", "Provider.error.person.required");
			}
			if (provider.isRetired() && StringUtils.isEmpty(provider.getRetireReason())) {
				errors.rejectValue("retireReason", "Provider.error.retireReason.required");
			}
		}
	}
}
