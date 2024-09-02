package br.com.bb.f4353448.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class PagamentoExceptionMapper implements ExceptionMapper<ErrosDeSistema.CampoNaoInformado> {

    @Override
    public Response toResponse(ErrosDeSistema.CampoNaoInformado campoNaoInformado) {
        Map<String, String> errordetails = new HashMap<>();
        errordetails.put("codigo", campoNaoInformado.getCode());
        errordetails.put("mensagem", campoNaoInformado.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errordetails)
                .build();
    }
}
