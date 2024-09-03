package br.com.bb.f4353448.exception.exceptionMapper;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class PreenchimentoIncorretoCPFExceptionMapper implements ExceptionMapper<ErrosDeSistema.PreenchimentoIncorretoCPF> {

    @Override
    public Response toResponse(ErrosDeSistema.PreenchimentoIncorretoCPF preenchimentoIncorretoCPF) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("codigo", preenchimentoIncorretoCPF.getCode());
        errorDetails.put("mensagem", preenchimentoIncorretoCPF.getMessage());

        return Response.status(422)
                .entity(errorDetails)
                .build();
    }
}