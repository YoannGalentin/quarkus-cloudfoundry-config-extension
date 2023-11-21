package io.quarkiverse.quarkus.cloudfoundry.config.extension.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CloudfoundryConfigExtensionResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/cloudfoundry-config-extension")
                .then()
                .statusCode(200)
                .body(is("Hello cloudfoundry-config-extension"));
    }
}
