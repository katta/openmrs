package org.openmrs.web.controller.provider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Provider;
import org.openmrs.api.ProviderService;
import org.openmrs.api.context.Context;
import org.openmrs.propertyeditor.PersonEditor;
import org.openmrs.web.WebConstants;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

public class ProviderFormController extends SimpleFormController {
	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * This is called prior to displaying a form for the first time. It tells Spring the
	 * form/command object to load into the request
	 * 
	 * @see org.springframework.web.servlet.mvc.AbstractFormController#formBackingObject(javax.servlet.http.HttpServletRequest)
	 */
	
	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		
		binder.registerCustomEditor(org.openmrs.Person.class, new PersonEditor());
		
	}
	
	/**
	 * The onSubmit function receives the form/command object that was modified by the input form
	 * and saves it to the db
	 * 
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.validation.BindException)
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object obj,
	                                BindException errors) throws Exception {
		
		String view = getFormView();
		HttpSession httpSession = request.getSession();
		try {
			
			if (Context.isAuthenticated()) {
				Provider provider = (Provider) obj;
				ProviderService service = Context.getProviderService();
				
				String message = "Provider.saved";
				if (request.getParameter("saveProviderButton") != null) {
					service.saveProvider(provider);
				} else if (request.getParameter("retireProviderButton") != null) {
					service.retireProvider(provider, provider.getRetireReason());
					message = "Provider.retired";
				} else if (request.getParameter("unretireProviderButton") != null) {
					service.unretireProvider(provider);
					message = "Provider.unretired";
				}
				
				// post action
				view = getSuccessView();
				view = view + "?providerId=" + provider.getProviderId();
				httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, message);
				
			}
		}
		catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ModelAndView(new RedirectView(view));
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws ServletException {
		Provider provider = new Provider();
		
		if (Context.isAuthenticated()) {
			ProviderService ps = Context.getProviderService();
			String providerId = request.getParameter("providerId");
			return providerId != null ? ps.getProvider(Integer.valueOf(providerId)) : provider;
		}
		return provider;
	}
	
}
