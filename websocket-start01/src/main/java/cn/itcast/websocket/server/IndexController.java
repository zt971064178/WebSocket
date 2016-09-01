package cn.itcast.websocket.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping("demo")
	@ResponseBody
	public String demo() {
		return "Hello WebSocket" ;
	}
	
	@RequestMapping("index")
	public String index() {
		return "index" ;
	}
	
}
