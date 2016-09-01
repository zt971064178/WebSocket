package cn.itcast.websocket.server;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import cn.itcast.websocket.message.TopicMessage;
import cn.itcast.websocket.message.UserMessage;

/**
 * ClassName: WebSocketController  
 * (服务类)
 * @author zhangtian  
 * @version
 */
@Controller
public class WebSocketController {
	/*
	 * a、queue 为消息缓存队列，简单放到jvm中，实际项目请放到Redis等缓存中。 
	 * b、广播方式用的@SendTo注解。 
	 * c、正常的点对点应该采用@SendToUser,但实际上并没调通，表现就是仅仅js端发，js端就能触发回调，根本不走controller中的userMessage方法（stompClient.send(“/user/{userId}/message”)
	 * d、据spring官网意思，@MessageMapping、@RequestMapping是共存的，所以把它当做平常我们用的controller就行了
	 */
	private static int SIZE = 3 ;
	private Queue<Object> queue = new ArrayDeque<Object>() ;
	
	@Autowired
	public SimpMessagingTemplate template ;
	
	@SubscribeMapping("/init/{coordinationId}")
	public Map<String, Object> init(@DestinationVariable("coordinationId") String coordinationId) {
		System.out.println("------------新用户 " + coordinationId + " 进入，空间初始化---------");  
		Map<String, Object> document = new HashMap<String, Object>();  
        document.put("chat",queue);  
        return document ;
	}
	
	// 广播
	@MessageMapping("/live")
	// Spring4.3 @SendTo和@SendToUser现在可以在类级被指定为共享共同的目的地。
	@SendTo("/topic/live")
	public TopicMessage sentTopic(TopicMessage msg) {
		if (queue.size() >= SIZE) {
	        queue.poll();
	    }
	    queue.offer(msg);
	    return msg;
	}
	
	// 广播方式写点对点
	@MessageMapping("/point")
	public void sendPoint(UserMessage userMessage) {
		String dest = "/topic/point" + userMessage.getCoordinationId();  
	    this.template.convertAndSend(dest, userMessage);
	}
	
	// 点对点
	@MessageMapping("/message")
	// Spring4.3 @SendTo和@SendToUser现在可以在类级被指定为共享共同的目的地。
	@SendToUser("/message")
	public void userMessage(UserMessage userMessage) throws Exception {
	    this.template.convertAndSendToUser(userMessage.getCoordinationId(), "/message", userMessage);
	}
}
