package serveur;

public class Message {

	private int size;
	private int code;
	private String msg;
	private String type;
	
	public Message(String msg, String type, int code) {
		this.msg = msg;
		this.size = msg.length();
		this.type = type;
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return this.msg;
	}

	public String getType() {
		return this.type;
	}
	
	public int getCode() {
		return code;
	}
	
}
