package cn.itcast.spring4.websocket;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.websocket.api.WebSocketBehavior;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.server.WebSocketServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.jetty.JettyRequestUpgradeStrategy;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import cn.itcast.spring4.websocket.handle.MySocketJSHandle;
import cn.itcast.spring4.websocket.interceptor.MyHandshakeInterceptor;

/**
 * ClassName: WebConfig  
 * (配置类总入口)
 * @author zhangtian  
 * @version
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
@ComponentScan
@EnableAspectJAutoProxy// 开启Spring对Aspectj支持
@EnableTransactionManagement
public class WebConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();  
	}
	
	/*
	 * 设置WebSocket引擎
	 * For Tomcat, WildFly, and GlassFish
	 */
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean() ;
		container.setMaxTextMessageBufferSize(8192);
		container.setMaxBinaryMessageBufferSize(8192);
		container.setMaxSessionIdleTimeout(8000);
		container.setAsyncSendTimeout(5000);
		return container ;
	}
	
	/*
	 * 设置WebSocket引擎
	 * For Jetty
	 */
	/*@Bean
    public DefaultHandshakeHandler handshakeHandler() {
		
        WebSocketPolicy policy = new WebSocketPolicy(WebSocketBehavior.SERVER);
        policy.setInputBufferSize(8192);
        policy.setIdleTimeout(600000);

        return new DefaultHandshakeHandler(
                new JettyRequestUpgradeStrategy(new WebSocketServerFactory(policy)));
    }*/
	
	/**
	 * 注册WebSocket的总入口
	 * @param arg0
	 * zhangtian
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(myStandarHandle(), "/standar") ;
		// 注册两种：ws 和http 实现websocket
		// registry.addHandler(myStandarHandle(), "/standar").addInterceptors(myHttpInterceptor()) ;
		// 支持SockJS
		registry.addHandler(mySocketJSHandle(), "/mySocketJSHandle").withSockJS() ;
		// 注册两种：ws 和http 实现websocket
		// registry.addHandler(mySocketJSHandle(), "/mySocketJSHandle").addInterceptors(myHttpInterceptor()) ;
	}
	
	/*
	 * 自定义的标准的的WebSocket
	 */
	@Bean
	public WebSocketHandler myStandarHandle() {
		return new MySocketJSHandle() ;
	}
	
	/*
	 * 自定义的支持SockJS的WebSocket
	 */
	@Bean
	public WebSocketHandler mySocketJSHandle() {
		return new MySocketJSHandle() ;
	}
	
	/*
	 * 自定义Interceptor
	 */
	@Bean
	public MyHandshakeInterceptor myHttpInterceptor() {
		return new MyHandshakeInterceptor() ;
	}
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
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/") ;
	}
}
