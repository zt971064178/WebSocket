package cn.itcast.spring4.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Enable STOMP over WebSocket
 * 详细参考文档
 * http://docs.spring.io/spring/docs/4.3.0.RELEASE/spring-framework-reference/html/websocket.html
 * ClassName: WebSocketStompConfig  
 * (使用STOMP配置)
 * @author zhangtian  
 * @version
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer  {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/portfolio").withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");
		registry.enableSimpleBroker("/topic", "/queue");
	}
	
	/*
	 * 前端调用
	 *  var socket = new SockJS("/spring-websocket-portfolio/portfolio");
		var stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
		}	
		或者
		var socket = new WebSocket("/spring-websocket-portfolio/portfolio");
		var stompClient = Stomp.over(socket);
		stompClient.connect({}, function(frame) {
		}
	 */
}
