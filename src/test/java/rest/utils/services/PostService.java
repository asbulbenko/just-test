package rest.utils.services;

import io.restassured.response.Response;
import rest.pojos.CommentResponse;
import rest.pojos.CreatePostRequest;
import rest.pojos.PostResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostService extends RestService {

    public PostResponse createPost(CreatePostRequest rq) {
        return given().spec(REQ_SPEC).body(rq).post()
                .then().statusCode(201).extract().as(PostResponse.class);
    }

    public Response deletePost(int postId) {
        return given().spec(REQ_SPEC).delete("/{id}", postId);
    }

    public PostResponse getPostById(int postId) {
        return given().spec(REQ_SPEC).get("/{id}", postId)
                .then().statusCode(200).extract().as(PostResponse.class);
    }

    public List<PostResponse> getPosts() {
        return given().spec(REQ_SPEC).get()
                .then().statusCode(200).extract().jsonPath().getList("", PostResponse.class);
    }

    public PostResponse patchPost(int postId, String value) {
        return given().spec(REQ_SPEC).body(value).patch("/{id}", postId)
                .then().statusCode(200).extract().as(PostResponse.class);
    }

    public PostResponse updatePost(int postId, CreatePostRequest update) {
        return given().spec(REQ_SPEC).body(update).put("/{id}", postId)
                .then().statusCode(200).extract().as(PostResponse.class);
    }

    public List<PostResponse> getUserPosts(int userId) {
        return given().spec(REQ_SPEC).param("userId", userId).get()
                .then().statusCode(200).extract().jsonPath().getList("", PostResponse.class);
    }

    public List<CommentResponse> getPostComments(int postId) {
        return given().spec(REQ_SPEC).get("/{id}/comments", postId)
                .then().statusCode(200).extract().jsonPath().getList("", CommentResponse.class);
    }

    @Override
    protected String getBasePath() {
        return "/posts";
    }
}
