package fudan.homework2.pojo;

public class User {

	private int user_id;
	private String user_name;
	private String user_priority;
	
	public User(int user_id, String user_name, String user_priority) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_priority = user_priority;
	}
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_priority() {
		return user_priority;
	}
	public void setUser_priority(String user_priority) {
		this.user_priority = user_priority;
	}
}
