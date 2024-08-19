package br.com.bb.f4353448.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CartaoResourceTest {

    @Test
    public void statusCodeDaRequisicao200() {
        RestAssured.given()
                .get("/pagamentos")
                .then()
                .statusCode(200);
    }

    @Test
    public void dadosNulosStatusCode400() {
        String dadosNulos = "{ \"tipoPessoa\": 0, \"cpfOuCnpj\": \"\", \"pagamento\": 0.00, \"nomeTitular\": \"\", \"numeroDoCartao\": \"\", \"mesVencimento\": 0, \"anoVencimento\": 0, \"codigoDeSeguranca\": \"\" }";
        RestAssured.given()
                .contentType("application/json")
                .body(dadosNulos)
                .when()
                .post("/pagamentos")
                .then()
                .statusCode(400);
    }

    @Test
    public  void preenchimentoIncorretoCPFStatusCode422() {
        String cpfIncorreto = "{ \"tipoPessoa\": 1, \"cpfOuCnpj\": \"3333\", \"pagamento\": 300.00, \"nomeTitular\": \"Luiz\", \"numeroDoCartao\": \"1234567891011\", \"mesVencimento\": 4, \"anoVencimento\": 2036, \"codigoDeSeguranca\": \"123\" }";
        RestAssured.given()
                .contentType("application/json")
                .body(cpfIncorreto)
                .when()
                .post("/pagamentos")
                .then()
                .statusCode(422);
    }

    @Test
    public void preenchimentoIncorretoNumeroCartaoStatusCode422() {
        String numeroCartaoIncorreto = "{ \"tipoPessoa\": 1, \"cpfOuCnpj\": \"33333333333\", \"pagamento\": 300.00, \"nomeTitular\": \"Luiz\", \"numeroDoCartao\": \"1234\", \"mesVencimento\": 4, \"anoVencimento\": 2036, \"codigoDeSeguranca\": \"123\" }";
        RestAssured.given()
                .contentType("application/json")
                .body(numeroCartaoIncorreto)
                .when()
                .post("/pagamentos")
                .then()
                .statusCode(422);
    }

    @Test
    public void preencherSomenteNumerosStatusCode422() {
        String somenteNumeros = "{ \"tipoPessoa\": 1, \"cpfOuCnpj\": \"33333333333\", \"pagamento\": 300.00, \"nomeTitular\": \"Luiz\", \"numeroDoCartao\": \"123456789abcd\", \"mesVencimento\": 4, \"anoVencimento\": 2036, \"codigoDeSeguranca\": \"123\" }";
        RestAssured.given()
                .contentType("application/json")
                .body(somenteNumeros)
                .when()
                .post("/pagamentos")
                .then()
                .statusCode(422);
    }
}
