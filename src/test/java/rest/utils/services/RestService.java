package rest.utils.services;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class RestService {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    protected RequestSpecification REQ_SPEC;

    protected abstract String getBasePath();

    public RestService() {
        REQ_SPEC = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath(getBasePath())
                .setContentType(ContentType.JSON)
                .build();
    }
}
