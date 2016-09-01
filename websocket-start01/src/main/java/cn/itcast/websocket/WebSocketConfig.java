package cn.itcast.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import cn.itcast.websocket.handler.MyWebSocketHandler;
import cn.itcast.websocket.interceptor.WebSocketHandshakeInterceptor;

/**
 * ClassName: WebSocketConfig  
 * (配置类)
 * @author zhangtian  
 * @version
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 这句表示在topic和user这两个域上可以向客户端发消息
		config.enableSimpleBroker("/topic", "/user") ;
		// 这句表示客户端向服务端广播发送时的主题上面需要加”/msg”作为前缀。 
		config.setApplicationDestinationPrefixes("/msg") ;
		// 这句表示给指定用户发送（一对一）的主题前缀是“/user/”
		config.setUserDestinationPrefix("/user/") ;
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// 这个和客户端创建连接时的url有关，后面在客户端的代码中可以看到。
		// setAllowedOrigins(“*”)处理跨域访问问题。 
		/*registry.addEndpoint("/webServer")
			.withSockJS() ;
		
		registry.addEndpoint("/noWebServer")
			.setAllowedOrigins("*");*/
		
		registry.addEndpoint("/webServer")
		.setHandshakeHandler(new MyWebSocketHandler())
		.setAllowedOrigins("*")
		.addInterceptors(new WebSocketHandshakeInterceptor())
		.withSockJS() ;
	
		registry.addEndpoint("/noWebServer")
			.setHandshakeHandler(new DefaultHandshakeHandler())
			.addInterceptors(new WebSocketHandshakeInterceptor())
			.setAllowedOrigins("*");
	}
}
