package br.com.bb.f4353448.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CartaoResourceTest {

    String novoPagamento = "{ \"tipoPessoa\": 2, \"cpfOuCnpj\": \"77777777777777\", \"pagamento\": 3500.00, \"nomeTitular\": \"Empresa 202\", \"numeroDoCartao\": \"1234.5678.9101-112\", \"mesVencimento\": 9, \"anoVencimento\": 2030, \"codigoDeSeguranca\": \"567\"}";

    @Test
    public void statusCodeDaRequisicao200() {
        RestAssured.given()
                .get("/pagamentos")
                .then()
                .statusCode(200);
    }

//    @Test
//    public void testcriarPagamentoStatusCode201() {
//        RestAssured.given()
//                .body(novoPagamento)
//                .header("Content-Type", "application/json")
//                .when()
//                .post("/pagamentos")
//                .then()
//                .statusCode(201);
//    }
//
//    @Test
//    public void testbuscarPagamentoPorIDStatusCode200() {
//        int id = 1;
//        String getresponseBody = RestAssured.given()
//                .when()
//                .get("/pagamentos/" + id)
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract()
//                .body()
//                .asString();
//
//        JsonPath jsonPath = new JsonPath(getresponseBody);
//
//        String respostaCpfOuCnpj = jsonPath.getString("cpfOuCnpj");
//        String respostaNumeroDoCartao = jsonPath.getString("numeroDoCartao");
//
//        assert respostaCpfOuCnpj.equals("77777777777777");
//        assert respostaNumeroDoCartao.equals("1234.5678.9101-112");
//    }
//
//    @Test
//    public void testdeletarPagamentoStatusCode204() {
//        int id = 1;
//        RestAssured.given()
//                .when()
//                .delete("/pagamentos/" + id)
//                .then()
//                .statusCode(204);
//    }

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
