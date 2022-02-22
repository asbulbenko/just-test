package rest.utils.services;

import rest.pojos.CommentResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CommentService extends RestService {

    public List<CommentResponse> getPostComments(int postId) {
        return given().spec(REQ_SPEC).param("postId", postId).get()
                .then().statusCode(200).extract().jsonPath().getList("", CommentResponse.class);
    }

    public CommentResponse getCommentById(int commentId) {
        return given().spec(REQ_SPEC).get("/{commentId}", commentId).then().statusCode(200).extract().as(CommentResponse.class);
    }

    public List<CommentResponse> getCommentByEmail(String email) {
        return given().spec(REQ_SPEC).param("email", email).get().then().statusCode(200).extract().jsonPath().getList("", CommentResponse.class);
    }

    @Override
    protected String getBasePath() {
        return "/comments";
    }
}
