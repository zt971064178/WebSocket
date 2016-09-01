package cn.itcast.websocket.message;

import java.io.Serializable;

public class TopicMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name ;
	private String msg ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
