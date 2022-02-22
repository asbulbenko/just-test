package rest;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rest.pojos.CommentResponse;
import rest.pojos.CreatePostRequest;
import rest.pojos.PostResponse;
import rest.utils.PostGenerator;
import rest.utils.services.CommentService;
import rest.utils.services.PostService;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Base rest api tests")
public class RestTests {

    private static PostService postService;
    private static CommentService commentService;

    @BeforeAll
    public static void setup() {
        postService = new PostService();
        commentService = new CommentService();
    }

    @Test
    @DisplayName("List all posts")
    @Description("Verify that list with 100 posts is returned")
    public void getPosts() {
        List<PostResponse> postList = postService.getPosts();
        assertThat(postList.size()).isEqualTo(100);
    }

    @Test
    @DisplayName("Create post")
    @Description("Verify request to create post was send and post was created in the system")
    public void createPost() {
        CreatePostRequest rq = PostGenerator.createSimplePost();
        PostResponse rs = postService.createPost(rq);
        assertThat(rs).extracting(PostResponse::getUserId, PostResponse::getBody, PostResponse::getTitle)
                .contains(rq.getUserId(), rq.getBody(), rq.getTitle());
    }

    @Test
    @DisplayName("Get post by id")
    @Description("Able to get post by id")
    public void getPostById() {
        PostResponse rs = postService.getPostById(100);
        assertThat(rs).extracting(PostResponse::getId, PostResponse::getUserId, PostResponse::getTitle)
                .contains(100, 10, "at nam consequatur ea labore ea harum");
    }

    @Test
    @DisplayName("Delete post")
    @Description("Verify post is created and deleted by id")
    public void deletePost() {
        CreatePostRequest rq = PostGenerator.createSimplePost();
        PostResponse rs = postService.createPost(rq);

        Response delResponse = postService.deletePost(rs.getId());
        assertThat(delResponse.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("Patch post")
    @Description("Verify patching a post returns response with updated argument")
    public void patchPost() {
        // Get post id, where title will be updated
        PostResponse rs = postService.getPostById(100);
        assertThat(rs).extracting(PostResponse::getId, PostResponse::getUserId, PostResponse::getTitle)
                .contains(100, 10, "at nam consequatur ea labore ea harum");

        String titleToUpdate = "{ \"title\" : \"foo\"}";
        PostResponse patchedPost = postService.patchPost(rs.getId(), titleToUpdate);
        assertThat(patchedPost).extracting(PostResponse::getId, PostResponse::getUserId, PostResponse::getTitle)
                .contains(100, 10, "foo");
    }

    @Test
    @DisplayName("Update post")
    @Description("Verify update a post returns response with updated arguments")
    public void updatePost() {
        // Get post id, where all fields will be updated except postId
        PostResponse rs = postService.getPostById(100);
        assertThat(rs).extracting(PostResponse::getId,
                        PostResponse::getUserId, PostResponse::getTitle, PostResponse::getBody)
                .contains(100, 10, "at nam consequatur ea labore ea harum",
                        "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut");

        // Create arguments that will be used to update corresponding post
        CreatePostRequest rq = CreatePostRequest.builder()
                .userId(5)
                .title("latin")
                .body("in vino veritas")
                .build();

        // Verify that data in response same as was sent in update request
        PostResponse updatedPost = postService.updatePost(rs.getId(), rq);
        assertThat(updatedPost).extracting(PostResponse::getId,
                        PostResponse::getUserId, PostResponse::getTitle, PostResponse::getBody)
                .contains(100, rq.getUserId(), rq.getTitle(), rq.getBody());
    }

    @Test
    @DisplayName("Filter posts by userId")
    @Description("Verify request to get posts filtering by random userId returns list of 10 posts")
    public void testFilterPostsByUserId() {
        // Get random userId from 1 to 10 & verify 10 posts returned for corresponding userId
        int userId = new Random().nextInt(10) + 1;
        List<PostResponse> rs = postService.getUserPosts(userId);
        assertThat(rs.size()).isEqualTo(10);
        assertThat(rs).extracting(PostResponse::getUserId).contains(userId);
    }

    @Test
    @DisplayName("Get Comments on a post")
    @Description("Verify get all comments from a post returned in response")
    public void testGetCommentsUnderPost() {
        List<CommentResponse> rs = postService.getPostComments(100);

        // Verify post has 5 comments & check 1st comment arguments
        assertThat(rs.size()).isEqualTo(5);
        assertThat(rs.get(0)).extracting(CommentResponse::getPostId, CommentResponse::getEmail,
                CommentResponse::getName, CommentResponse::getBody)
                .contains(100, "Zola@lizzie.com", "et occaecati asperiores quas voluptas ipsam nostrum",
                        "neque unde voluptatem iure\nodio excepturi ipsam ad id\nipsa sed expedita error quam\nvoluptatem tempora necessitatibus suscipit culpa veniam porro iste vel");
        // Verify all emails that commented a post
        assertThat(rs).extracting(CommentResponse::getEmail)
                .contains("Zola@lizzie.com", "Dolly@mandy.co.uk", "Davion@eldora.net", "Wilburn_Labadie@araceli.name","Emma@joanny.ca");
    }

    @Test
    @DisplayName("Get comment by id")
    @Description("Able to get comment by id")
    public void testGetComment() {
        CommentResponse rs = commentService.getCommentById(1);
        assertThat(rs).extracting(CommentResponse::getId, CommentResponse::getEmail, CommentResponse::getName, CommentResponse::getBody)
                .contains(1, "Eliseo@gardner.biz", "id labore ex et quam laborum",
                        "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium");
    }

    @Test
    @DisplayName("Get comments by postService and commentService and compare")
    @Description("Verify that responses returned from postService and commentService are equal")
    public void testGetCommentsByPost() {
        // Get random postId from 1 to 100
        int postId = new Random().nextInt(100) + 1;
        List<CommentResponse> postServiceList = postService.getPostComments(postId);
        List<CommentResponse> commentServiceList = commentService.getPostComments(postId);

        assertThat(postServiceList).containsExactlyInAnyOrderElementsOf(commentServiceList);
    }

    @Test
    @DisplayName("Get comments by email")
    @Description("Able to get comment by email")
    public void testGetCommentsByEmail() {
        String email = "Emma@joanny.ca";
        List<CommentResponse> rs = commentService.getCommentByEmail(email);
        assertThat(rs.get(0)).extracting(CommentResponse::getId, CommentResponse::getPostId,
                CommentResponse::getEmail, CommentResponse::getName, CommentResponse::getBody)
                .contains(500, 100, email, "ex eaque eum natus",
                        "perspiciatis quis doloremque\nveniam nisi eos velit sed\nid totam inventore voluptatem laborum et eveniet\naut aut aut maxime quia temporibus ut omnis");
    }

}
