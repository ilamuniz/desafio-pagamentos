package br.com.bb.f4353448.service;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import br.com.bb.f4353448.models.Cartao;
import br.com.bb.f4353448.repository.CartaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.Year;
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

    public Cartao listarCartaoPorID(int numeroPagamento) {
        return cartaoRepository.findById((long) numeroPagamento);
    }

    public Cartao inserirCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros {
        validarCartao(cartao);
        entityManager.persist(cartao);
        entityManager.flush();
        return cartao;
    }

    public void deletarCartao(int numeroPagamento) {
        cartaoRepository.deleteById((long) numeroPagamento);
    }

    private void validarCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros {
        // Campo Tipo de pessoa só aceita 1 - Pessoa Física ou 2 - Pessoa Jurídica
        if (cartao.getTipoPessoa() != 1 && cartao.getTipoPessoa() != 2) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, informe o tipo correto - 1 (para Pessoa Física) ou 2 (para Pessoa Jurídica)");
        }
        // Campo CPF ou CNPJ não pode ser nulo e só aceita 11 ou 14 números (no caso, se for CNPJ)
        if(cartao.getCpfOuCnpj() == null || cartao.getCpfOuCnpj().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, informe o CPF ou o CNPJ do comprador.");
        }
        if (!(cartao.getCpfOuCnpj().length() == 11 || cartao.getCpfOuCnpj().length() == 14)) {
            throw new ErrosDeSistema.PreenchimentoIncorretoCPF("Campo CPF deve conter 11 dígitos para CPF ou 14 dígitos para CNPJ.");
        }
        if (!cartao.getCpfOuCnpj().matches("\\d+")) {
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
            throw new ErrosDeSistema.PreencherSomenteNumeros("Por favor, preencha o número do cartão somente com números.");
        }
        // Campos mês e ano de expiração não podem ser nulo
        if(cartao.getMesVencimento() > 12 || cartao.getMesVencimento() < 0) {
            throw new ErrosDeSistema.CampoNaoInformado("Favor, informar um mês válido para expiração do cartão.");
        }
        if (cartao.getAnoVencimento() < Year.now().getValue()) {
            throw new ErrosDeSistema.CampoNaoInformado("Favor, informar um ano válido para expiração do cartão");
        }
        // Campo código não aceita letras
        if(!cartao.getCodigoDeSeguranca().matches("\\d+")) {
            throw new ErrosDeSistema.CampoNaoInformado("Por favor, preencha o CVV somente com números.");
        }
    }

}
