package br.com.bb.f4353448.resource;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import br.com.bb.f4353448.models.Cartao;
import br.com.bb.f4353448.service.CartaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import java.util.List;
import java.util.Optional;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartaoResource {

    @Inject
    CartaoService cartaoService;

    public CartaoResource() {}

    @GET
    @Counted(name = "listar_request_count", description = "Contador de requisicoes para listar todos os dados")
    @Timed(name = "listar_requests_time", description = "Tempo de requisicoes para listar todos os dados")
    public List<Cartao> listar() {
        return cartaoService.listar();
    }

    @GET
    @Path("/{id}")
    @Counted(name = "listarCartaoPorID_requests_count", description = "Contador de requisicoes para listar dados por ID")
    @Timed(name = "listarCartaoPorID_requests_time", description = "Tempo de requisicoes para listar dados por ID")
    public Response listarCartaoPorID(@PathParam("id") int numeroPagamento) {
        Optional<Cartao> cartao = cartaoService.listarCartaoPorID(numeroPagamento);
        if (cartao.isPresent()) {
            return Response.ok(cartao.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Counted(name = "inserirCartao_requests_count", description = "Contador de requisicoes para cadastrar dados")
    @Timed(name = "inserirCartao_requests_time", description = "Tempo de requisicoes para cadastrar dados")
    public Response inserirCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros {
        Cartao novoCartao = cartaoService.inserirCartao(cartao);
        return Response.status(Response.Status.CREATED)
                .entity(novoCartao)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "deletarCartao_requests_count", description = "Contador de requisicoes para deletar dados")
    @Timed(name = "deletarCartao_requests_time", description = "Tempo de requisicoes para deletar dados")
    public Response deletarCartao(@PathParam("id") int numeroPagamento) {
        try {
            cartaoService.deletarCartao(numeroPagamento);
        } catch (ErrosDeSistema.CartaoNaoEncontrado e) {
            throw new ErrosDeSistema.CartaoNaoEncontrado("Cartão com ID " + numeroPagamento + " não encontrado");
        }
        return Response.noContent().build();
    }

    @Liveness
    public HealthCheck checkLiveness() {
        return () -> HealthCheckResponse.up("Aplicação está em execução.");
    }

    @Readiness
    public HealthCheck checkReadiness() {
        return () -> HealthCheckResponse.up("Aplicação está pronta.");
    }
}
