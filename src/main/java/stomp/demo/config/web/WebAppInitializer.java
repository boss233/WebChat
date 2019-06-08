package stomp.demo.config.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * LabWebAppInitializer
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // SpringApplicationContext
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootConfig.class};
    }
    
    // SpringMVCApplicationContext
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

}