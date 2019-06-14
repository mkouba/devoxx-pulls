package org.acme.pulls;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class PullsResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/pulls")
                .then()
                .statusCode(200)
                .body(containsString("Super cool PR"));
    }

}