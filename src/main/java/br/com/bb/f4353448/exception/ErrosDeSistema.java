package br.com.bb.f4353448.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.Map;


public class ErrosDeSistema {

    public static final class CampoNaoInformado extends PagamentoException {
        public static final String MSG_ERRO = "O campo %s não foi informado corretamente.";
        public static final String COD_ERRO = "400"; // Codigo do erro

        public CampoNaoInformado(String nomeCampo) {
            super(COD_ERRO, String.format(MSG_ERRO, nomeCampo), Map.of("NOME_CAMPO", nomeCampo));

        }
    }

    public static final class PreenchimentoIncorretoCPF extends WebApplicationException {
        public PreenchimentoIncorretoCPF(String MSG_ERRO) {
            super(Response.status(422)
                    .entity(MSG_ERRO)
                    .type("application/json")
                    .build());
        }
    }

    public static final class PreenchimentoIncorretoNumeroCartao extends WebApplicationException {
        public PreenchimentoIncorretoNumeroCartao(String MSG_ERRO) {
            super(Response.status(422)
                    .entity(MSG_ERRO)
                    .type("application/json")
                    .build());
        }
    }

    public static final class PreencherSomenteNumeros extends WebApplicationException {
        public PreencherSomenteNumeros(String MSG_ERRO) {
            super(Response.status(422)
                    .entity(MSG_ERRO)
                    .type("application/json")
                    .build());
        }
    }

    public static final class CartaoNaoEncontrado extends WebApplicationException {
        public CartaoNaoEncontrado(String MSG_ERRO) {
            super(Response.status(Response.Status.NOT_FOUND)
                    .entity(MSG_ERRO)
                    .type("application/json")
                    .build());
        }
    }

}
