package br.com.bb.f4353448.resource;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import br.com.bb.f4353448.models.Cartao;
import br.com.bb.f4353448.service.CartaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaoResource {

    @Inject
    CartaoService cartaoService;

    public CartaoResource() {}

    @GET
    public List<Cartao> listar() {
        return cartaoService.listar();
    }

    @GET
    @Path("/{id}")
    public Cartao listarCartaoPorID(@PathParam("id") int id) {
        return cartaoService.listarCartaoPorID(id);
    }

    @POST
    public Response inserirCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros {
        Cartao novoCartao = cartaoService.inserirCartao(cartao);
        return Response.status(Response.Status.CREATED)
                .entity(novoCartao)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletarCartao(@PathParam("id") int id) {
        cartaoService.deletarCartao(id);
        return Response.noContent().build();
    }
}
