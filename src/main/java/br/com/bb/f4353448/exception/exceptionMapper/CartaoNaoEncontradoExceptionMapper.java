package br.com.bb.f4353448.exception.exceptionMapper;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class CartaoNaoEncontradoExceptionMapper implements ExceptionMapper<ErrosDeSistema.CartaoNaoEncontrado> {

    @Override
    public Response toResponse(ErrosDeSistema.CartaoNaoEncontrado cartaoNaoEncontrado) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("CODIGO", cartaoNaoEncontrado.getCode());
        errorDetails.put("MENSAGEM", cartaoNaoEncontrado.getMessage());
        errorDetails.put("MOTIVO-ERRO", cartaoNaoEncontrado.toString());

        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorDetails)
                .build();
    }
}
