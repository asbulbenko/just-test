package rest.pojos;

import lombok.Data;

@Data
public class PostResponse {
	private int id;
	private String title;
	private String body;
	private int userId;
}