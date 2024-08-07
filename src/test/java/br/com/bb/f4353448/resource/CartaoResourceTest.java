package br.com.bb.f4353448.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CartaoResourceTest {

    @Test
    public void statusCodeDaRequisicao200() {
        RestAssured.given()
                .get("pagamentos")
                .then()
                .statusCode(200);
    }

}
