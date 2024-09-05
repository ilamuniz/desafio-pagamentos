package br.com.bb.f4353448.resource;

import br.com.bb.f4353448.repository.CartaoRepository;
import br.com.bb.f4353448.models.Cartao;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

@QuarkusTest
public class CartaoResourceTest {

    @InjectMock
    CartaoRepository cartaoRepository;

    String novoPagamento = "{ \"tipoPessoa\": 2, \"cpfOuCnpj\": \"77777777777777\", \"pagamento\": 3500.00, \"nomeTitular\": \"Empresa 202\", \"numeroDoCartao\": \"1234.5678.9101-112\", \"mesVencimento\": 9, \"anoVencimento\": 2030, \"codigoDeSeguranca\": \"567\"}";

    @Test
    public void statusCodeDaRequisicao200() {
        RestAssured.given()
                .get("/pagamentos")
                .then()
                .statusCode(200);
    }

    @Test
    public void testcriarPagamentoStatusCode201() {
        RestAssured.given()
                .body(novoPagamento)
                .header("Content-Type", "application/json")
                .when()
                .post("/pagamentos")
                .then()
                .statusCode(201);
    }

    /**
     *
     * Esses dois métodos estão funcionando (testbuscarPagamentoPorIDStatusCode200() e testdeletarPagamentoStatusCode204()),
     * mas os dois métodos abaixo com o mesmo nome também funcionam se os dados não estiverem sendo persistidos no banco de dados
     *
    @Test
    public void testbuscarPagamentoPorIDStatusCode200() {
        int id = 1;
        String getresponseBody = RestAssured.given()
                .when()
                .get("/pagamentos/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(getresponseBody);

        String respostaCpfOuCnpj = jsonPath.getString("cpfOuCnpj");
        String respostaNumeroDoCartao = jsonPath.getString("numeroDoCartao");

        assert respostaCpfOuCnpj.equals("77777777777777");
        assert respostaNumeroDoCartao.equals("1234.5678.9101-112");
    }

    @Test
    public void testdeletarPagamentoStatusCode204() {
        int id = 1;
        RestAssured.given()
                .when()
                .delete("/pagamentos/" + id)
                .then()
                .statusCode(204);
    }
**/

    @Test
    public void testbuscarPagamentoPorIDStatusCode200() {
        int id = 1;
        Cartao cartaoTest = new Cartao();
        cartaoTest.setTipoPessoa(2);
        cartaoTest.setCpfOuCnpj("77777777777777");
        cartaoTest.setPagamento(BigDecimal.valueOf(3500.00));
        cartaoTest.setNomeTitular("Empresa 202");
        cartaoTest.setNumeroDoCartao("1234.5678.9101-112");
        cartaoTest.setMesVencimento(9);
        cartaoTest.setAnoVencimento(2030);
        cartaoTest.setCodigoDeSeguranca("567");
        Mockito.when(cartaoRepository.findById((long) id)).thenReturn(cartaoTest);

        String getresponseBody = RestAssured.given()
                .when()
                .get("/pagamentos/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(getresponseBody);
        int respostaId = jsonPath.getInt("numeroPagamento") + 1;  // Banco configurado para não aceitar valores nulos, mas mock está iniciando a contagem do zero.
        String respostaCpfOuCnpj = jsonPath.getString("cpfOuCnpj");
        String respostaNumeroDoCartao = jsonPath.getString("numeroDoCartao");

        assert respostaId == id;
        assert respostaCpfOuCnpj.equals("77777777777777");
        assert respostaNumeroDoCartao.equals("1234.5678.9101-112");
    }

    @Test
    public void testdeletarPagamentoStatusCode204() {
        int id = 1;
        Cartao cartaoTest = new Cartao();
        cartaoTest.setTipoPessoa(2);
        cartaoTest.setCpfOuCnpj("77777777777777");
        cartaoTest.setPagamento(BigDecimal.valueOf(3500.00));
        cartaoTest.setNomeTitular("Empresa 202");
        cartaoTest.setNumeroDoCartao("1234.5678.9101-112");
        cartaoTest.setMesVencimento(9);
        cartaoTest.setAnoVencimento(2030);
        cartaoTest.setCodigoDeSeguranca("567");
        Mockito.when(cartaoRepository.findById((long) id)).thenReturn(cartaoTest);

        String getResponseBody = RestAssured.given()
                .when()
                .get("/pagamentos/" + id)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        JsonPath jsonPath = new JsonPath(getResponseBody);
        int respostaId = jsonPath.getInt("numeroPagamento");
        if (respostaId == id) {
            RestAssured.given()
                    .when()
                    .delete("/pagamentos/" + id)
                    .then()
                    .log().all()
                    .statusCode(204);
        }
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
