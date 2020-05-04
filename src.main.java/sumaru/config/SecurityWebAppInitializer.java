package sumaru.config;


import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

@Order(1)
public class SecurityWebAppInitializer
    extends AbstractSecurityWebApplicationInitializer { 
	
	@Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
	    FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
	    characterEncodingFilter.setInitParameter("encoding", "UTF-8");
	    characterEncodingFilter.setInitParameter("forceEncoding", "true");
	    characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
	    }
	
}
