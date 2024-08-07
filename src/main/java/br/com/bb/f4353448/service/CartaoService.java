package br.com.bb.f4353448.service;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import br.com.bb.f4353448.models.Cartao;
import br.com.bb.f4353448.repository.CartaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
@Transactional
public class CartaoService {

    @Inject
    CartaoRepository cartaoRepository;
    @Inject
    EntityManager entityManager;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public List<Cartao> listar() {
        return cartaoRepository.listAll();
    }

    public Cartao listarCartaoPorID(int id) {
        return cartaoRepository.findById((long) id);
    }

    public Cartao inserirCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros {
        validarCartao(cartao);
        entityManager.persist(cartao);
        entityManager.flush();
        return cartao;
    }

    public void deletarCartao(int id) {
        cartaoRepository.deleteById((long) id);
    }

    private void validarCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros {
        // Campo nome não pode ser nulo
        if(cartao.getNome() == null || cartao.getNome().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, informe o nome do comprador.");
        }
        // Campo CPF não pode ser nulo e só aceita 11 ou 14 números (no caso, se for CNPJ)
        if(cartao.getCpf() == null || cartao.getCpf().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, informe o CPF do comprador.");
        }
        if (!(cartao.getCpf().length() == 11 || cartao.getCpf().length() == 14)) {
            throw new ErrosDeSistema.PreenchimentoIncorretoCPF("Campo CPF deve conter 11 dígitos para CPF ou 14 dígitos para CNPJ.");
        }
        if (!cartao.getCpf().matches("\\d+")) {
            throw new ErrosDeSistema.PreencherSomenteNumeros("Por favor, preencher somente com números.");
        }
        // Campo pagamento não pode ser nulo
        if (cartao.getPagamento() == null) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, digite o valor da compra.");
        }
        // Campo nome do titular não pode ser nulo
        if(cartao.getNomeTitular() == null || cartao.getNomeTitular().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("Favor, informar o titular do cartão.");
        }
        // Campo número do cartão não pode ser nulo e só aceita números. Numeração do cartão deve ter entre 13 e 16 dígitos
        if(cartao.getNumeroDoCartao() == null) {
            throw new ErrosDeSistema.CampoNaoInformado("Favor, informar o número do cartão.");
        }
        if (cartao.getNumeroDoCartao().length() < 13) {
            throw new ErrosDeSistema.PreenchimentoIncorretoNumeroCartao("Não foi possível identificar seu cartão. Digite a numeração completa do cartão.");
        }
        if (!cartao.getNumeroDoCartao().matches("\\d+")) {
            throw new ErrosDeSistema.PreencherSomenteNumeros("Por favor, preencher somente com números.");
        }
        // Campo data de expiração não pode ser nulo
        if(cartao.getDataDeExpiracao() == null) {
            throw new ErrosDeSistema.CampoNaoInformado("Favor, informar uma data válida de expiração do cartão.");
        }
        // Campo código não aceita número com menos de três caracteres
        if(cartao.getCodigoDeSeguranca() < 100) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, digite o código de segurança do cartão.");
        }
    }

}
