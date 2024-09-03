package br.com.bb.f4353448.exception.exceptionMapper;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class PreencherSomenteNumerosExceptionMapper implements ExceptionMapper<ErrosDeSistema.PreencherSomenteNumeros> {

    @Override
    public Response toResponse(ErrosDeSistema.PreencherSomenteNumeros preencherSomenteNumeros) {
        Map<String,String> errorDetails = new HashMap<>();
        errorDetails.put("codigo", preencherSomenteNumeros.getCode());
        errorDetails.put("mensagem", preencherSomenteNumeros.getMessage());

        return Response.status(422)
                .entity(errorDetails)
                .build();
    }
}
