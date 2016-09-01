package cn.itcast.spring4.websocket.handle;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * ClassName: StandarHandle  
 * (我的标准的Handle)
 * @author zhangtian  
 * @version
 */
public class StandarHandle extends TextWebSocketHandler {
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("message is :"+message);
	}
}
