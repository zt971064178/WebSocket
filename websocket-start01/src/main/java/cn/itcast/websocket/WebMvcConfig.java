package cn.itcast.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
@ComponentScan
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	// ============================== SpringMVC配置 =============================
		@Bean
		public InternalResourceViewResolver viewResouler() {
			InternalResourceViewResolver viewResolver = new InternalResourceViewResolver() ;
			viewResolver.setPrefix("/WEB-INF/views/");
			viewResolver.setSuffix(".jsp");
			viewResolver.setViewClass(JstlView.class);
			return viewResolver ;
		}
		
		@Bean
		public MappingJackson2HttpMessageConverter createHttpMessageConverters() {
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter() ;
			List<MediaType> supportedMediaTypes = new ArrayList<MediaType>() ;
			supportedMediaTypes.add(MediaType.TEXT_PLAIN) ;
			
			converter.setSupportedMediaTypes(supportedMediaTypes);
			return converter ;
		}
		
		@Bean
		public RequestMappingHandlerAdapter createRequestMappingHandlerAdapter(MappingJackson2HttpMessageConverter converter) {
			RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter() ;
			List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>() ;
			messageConverters.add(converter) ;
			
			adapter.setMessageConverters(messageConverters);
			return adapter ;
		}
		
		// 配置映射页面
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			
		}
		
		// 重写addResourceHandlers方法，addResourceLocations为文件放置目录，addResourceHandler对外暴露的访问路径
		// <!-- 请注意：js/css静态资源文件可以直接访问的必须要在MyMvcConfig配置中配置直接访问，否则文件无法访问  -->
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/") ;
		}
}
