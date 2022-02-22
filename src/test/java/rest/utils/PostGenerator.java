package rest.utils;

import rest.pojos.CreatePostRequest;

public class PostGenerator {

    public static CreatePostRequest createSimplePost() {
        return CreatePostRequest.builder()
                .userId(1)
                .title("foo")
                .body("bar")
                .build();
    }
}
