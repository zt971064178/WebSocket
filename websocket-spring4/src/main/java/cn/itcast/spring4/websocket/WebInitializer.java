package cn.itcast.spring4.websocket;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
//WebApplicationInitializer为Spring提供的用来配置Servlet3.0+的接口,替代web.xml文件
//实现此接口将会自动被SpringServletContainerInitialize(用来启动servlet3.0的容器)r获取
public class WebInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		try {
			AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext() ;
			// rootContext.register(AppConfig.class);
			// 新建WebApplicationContext，注册配置类，并将其和servletContext关联
			rootContext.setServletContext(servletContext);
			servletContext.addListener(new ContextLoaderListener(rootContext));
			
			// 统一编码  字符控制
			CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter() ;
			characterEncodingFilter.setEncoding("UTF-8");  
			characterEncodingFilter.setForceEncoding(true);
			javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", characterEncodingFilter) ;
			filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/*");

			AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext() ;
			rootContext.register(WebConfig.class);
			// 注册SpringMVC的DispatcherServlet
			Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext)) ;
			System.out.println("**************************");
			// 开启Servlet3.0异步方法的支持
			servlet.setAsyncSupported(true);
			servlet.addMapping("/") ;
			servlet.setLoadOnStartup(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
