package cn.itcast.spring4.websocket;

import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurationSupport;

/**
 * ClassName: WebSocketConfig  
 * (WebSocket客户端SockJS，具体参考文档)
 * @author zhangtian  
 * @version
 */
public class WebSocketConfig extends WebSocketMessageBrokerConfigurationSupport {

	@Override
	protected void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/sockjs").withSockJS()
	        .setStreamBytesLimit(512 * 1024)
	        .setHttpMessageCacheSize(1000)
	        .setDisconnectDelay(30 * 1000);
	}
}
