package org.openmrs.web.dwr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Provider;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.messagesource.MessageSourceService;

public class DWRProviderService {
	
	private static final Log log = LogFactory.getLog(DWREncounterService.class);
	
	public Vector<Object> findProvider(String phrase, boolean includeVoided, Integer start, Integer length) {
		Vector<Object> providerListItem = new Vector<Object>();
		List<Provider> providerList = Context.getProviderService().getProviders(phrase, start, length);
		MessageSourceService mss = Context.getMessageSourceService();
		if (providerList.size() == 0) {
			providerListItem.add(mss.getMessage("Provider.noMatchesFound", new Object[] { phrase }, Context.getLocale()));
		} else {
			for (Provider p : providerList) {
				if (!p.isRetired() || includeVoided == true)
					providerListItem.add(new ProviderListItem(p));
			}
		}
		return providerListItem;
	}
	
	public Map<String, Object> findProviderCountAndProvider(String phrase, boolean includeVoided, Integer start,
	        Integer length) throws APIException {
		Map<String, Object> providerMap = new HashMap<String, Object>();
		Vector<Object> objectList = findProvider(phrase, includeVoided, start, length);
		try {
			providerMap.put("count", Context.getProviderService().getCountOfProviders(phrase));
			providerMap.put("objectList", objectList);
		}
		catch (Exception e) {
			log.error("Error while searching for encounters", e);
			objectList.clear();
			objectList.add(Context.getMessageSourceService().getMessage("Provider.search.error") + " - " + e.getMessage());
			providerMap.put("count", 0);
			providerMap.put("objectList", objectList);
		}
		return providerMap;
	}
	
}
