package sumaru.config;

import javax.servlet.Filter;

import javax.servlet.ServletContext;

import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(2)
public class WebAppInitializer extends
		AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { PersistenceConfig.class, SecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/*" };
	}

	/*
	 * @Override protected Filter[] getServletFilters() {
	 * 
	 * CharacterEncodingFilter characterEncodingFilter = new
	 * CharacterEncodingFilter(); characterEncodingFilter.setEncoding("UTF-8");
	 * characterEncodingFilter.setForceEncoding(true); return new Filter[] {
	 * characterEncodingFilter}; }
	 */

	@Override
	protected void registerDispatcherServlet(ServletContext servletContext) {

		String servletName = super.getServletName();
		Assert.hasLength(servletName,
				"getServletName() may not return empty or null");

		WebApplicationContext servletAppContext = super
				.createServletApplicationContext();
		Assert.notNull(servletAppContext,
				"createServletApplicationContext() did not return an application "
						+ "context for servlet [" + servletName + "]");

		DispatcherServlet dispatcherServlet = new DispatcherServlet(
				servletAppContext);

		// >>> START: My custom code, rest is exqact copy of the super class
		// dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		// >>> END

		ServletRegistration.Dynamic registration = servletContext.addServlet(
				servletName, dispatcherServlet);
		Assert.notNull(
				registration,
				"Failed to register servlet with name '"
						+ servletName
						+ "'."
						+ "Check if there is another servlet registered under the same name.");

		registration.setLoadOnStartup(1);
		registration.addMapping(getServletMappings());
		registration.setAsyncSupported(isAsyncSupported());

		Filter[] filters = getServletFilters();
		if (!ObjectUtils.isEmpty(filters)) {
			for (Filter filter : filters) {
				super.registerServletFilter(servletContext, filter);
			}
		}

		super.customizeRegistration(registration);
	}

}
