package rest.pojos;

import lombok.Data;

@Data
public class CommentResponse {
	private String name;
	private int postId;
	private int id;
	private String body;
	private String email;
}