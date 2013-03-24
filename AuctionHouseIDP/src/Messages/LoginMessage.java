package Messages;

public class LoginMessage implements Message {

	private String user, password;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	private int role;
	
	public LoginMessage (String user, String password, int role) {
		this.user = user;
		this.password = password;
		this.role = role;
	}
	@Override
	public MessageType getType() {
		return MessageType.Login;
	}

}
