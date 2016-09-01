package cn.itcast.websocket.message;

import java.io.Serializable;

public class UserMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; 
	private String msg ;
	private String coordinationId ;

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

	public String getCoordinationId() {
		return coordinationId;
	}

	public void setCoordinationId(String coordinationId) {
		this.coordinationId = coordinationId;
	} 
}
