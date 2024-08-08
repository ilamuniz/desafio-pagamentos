package br.com.bb.f4353448.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;


public class ErrosDeSistema {

    public static final class CampoNaoInformado extends WebApplicationException {
        public CampoNaoInformado(String MSG_ERRO) {
            super(Response.status(Response.Status.BAD_REQUEST)
                    .entity(MSG_ERRO)
                    .type("application/json")
                    .build());
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

}
