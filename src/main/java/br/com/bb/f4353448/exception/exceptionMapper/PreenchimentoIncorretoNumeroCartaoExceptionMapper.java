package br.com.bb.f4353448.exception.exceptionMapper;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class PreenchimentoIncorretoNumeroCartaoExceptionMapper implements ExceptionMapper<ErrosDeSistema.PreenchimentoIncorretoNumeroCartao> {

    @Override
    public Response toResponse(ErrosDeSistema.PreenchimentoIncorretoNumeroCartao preenchimentoIncorretoNumeroCartao) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("codigo", preenchimentoIncorretoNumeroCartao.getCode());
        errorDetails.put("mensagem", preenchimentoIncorretoNumeroCartao.getMessage());

        return Response.status(422)
                .entity(errorDetails)
                .build();
    }
}
