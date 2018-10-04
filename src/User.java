
public class User {
	
	private int id;
	private String nickname;
	
	public User(int id, String nickname) {
		super();
		this.id = id;
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
}
