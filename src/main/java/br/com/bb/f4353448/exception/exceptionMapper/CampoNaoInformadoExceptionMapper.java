package br.com.bb.f4353448.exception.exceptionMapper;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class CampoNaoInformadoExceptionMapper implements ExceptionMapper<ErrosDeSistema.CampoNaoInformado> {

    @Override
    public Response toResponse(ErrosDeSistema.CampoNaoInformado campoNaoInformado) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("CODIGO", campoNaoInformado.getCode());
        errorDetails.put("MENSAGEM", campoNaoInformado.getMessage());
        errorDetails.put("MOTIVO-ERRO", campoNaoInformado.toString());

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorDetails)
                .build();
    }
}
