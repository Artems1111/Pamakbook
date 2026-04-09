package PamakBook;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Post implements Serializable{
	private static final long serialVersionUID = -3629051814124279721L;
	
	private LocalDateTime timestamp;
	private String text;
	private User user;
	
	public Post(String text, User user) {
		this.timestamp = LocalDateTime.now(); //setting as timestamp the current timestamp
		this.text = text;
		this.user = user;
		this.user.setPost(this);
	}
	
	
	//getters
	public String getTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return timestamp.format(formatter); //returns formated timestamp
	}

	public String getText() {
		return text;
	}

	public String getUserName() {
		return user.getName();
	}
	
	public String getUserEmail() {
		return user.getEmail();
	}

	@Override
	public String toString()
	{
		return timestamp+ ", " + text + ", "+ user.getName();
	}
	
	
}

//Artemis Dara-2025
