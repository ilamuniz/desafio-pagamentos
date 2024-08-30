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
import java.util.Optional;

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

    public Optional<Cartao> listarCartaoPorID(int numeroPagamento) {
        return Optional.ofNullable(cartaoRepository.findById((long) numeroPagamento));
    }

    public Cartao inserirCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros, ErrosDeSistema.PreenchimentoIncorretoNumeroCartao {
        validarCartao(cartao);
        entityManager.persist(cartao);
        entityManager.flush();
        return cartao;
    }

    public void deletarCartao(int numeroPagamento) throws ErrosDeSistema.CartaoNaoEncontrado {
        Cartao cartao = entityManager.find(Cartao.class, numeroPagamento);
        if (cartao == null) {
            throw new ErrosDeSistema.CartaoNaoEncontrado(numeroPagamento);
        }
        cartaoRepository.deleteById((long) numeroPagamento);
    }

    private void validarCartao(Cartao cartao) throws ErrosDeSistema.CampoNaoInformado, ErrosDeSistema.PreenchimentoIncorretoCPF, ErrosDeSistema.PreencherSomenteNumeros, ErrosDeSistema.PreenchimentoIncorretoNumeroCartao {
        // Campo Tipo de pessoa só aceita 1 - Pessoa Física ou 2 - Pessoa Jurídica
        if (cartao.getTipoPessoa() != 1 && cartao.getTipoPessoa() != 2) {
            throw new ErrosDeSistema.CampoNaoInformado("tipo Pessoa");
        }
        // Campo CPF ou CNPJ não pode ser nulo e só aceita 11 ou 14 números (no caso, se for CNPJ)
        if(cartao.getCpfOuCnpj() == null || cartao.getCpfOuCnpj().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("CPF ou CNPJ");
        }
        if ((cartao.getTipoPessoa() == 1 && cartao.getCpfOuCnpj().length() != 11) || (cartao.getTipoPessoa() == 2 && cartao.getCpfOuCnpj().length() != 14)) {
            throw new ErrosDeSistema.PreenchimentoIncorretoCPF();
        }
        if (!cartao.getCpfOuCnpj().matches("\\d+")) {
            throw new ErrosDeSistema.PreencherSomenteNumeros();
        }
        // Campo pagamento não pode ser nulo
        if (cartao.getPagamento() == null) {
            throw new ErrosDeSistema.CampoNaoInformado("pagamento");
        }
        // Campo nome do titular não pode ser nulo
        if(cartao.getNomeTitular() == null || cartao.getNomeTitular().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("nome do titular");
        }
        // Campo número do cartão não pode ser nulo. Numeração do cartão deve ter no mínimo 13 dígitos e no máximo 19 dígitos. Aceita números com e sem "-" ou "."
        if(cartao.getNumeroDoCartao() == null) {
            throw new ErrosDeSistema.CampoNaoInformado("número do cartão");
        }
        if (cartao.getNumeroDoCartao().length() < 13 || cartao.getNumeroDoCartao().length() > 19) {
            throw new ErrosDeSistema.PreenchimentoIncorretoNumeroCartao();
        }
        if (!cartao.getNumeroDoCartao().matches("[\\d-.]+")) {
            throw new ErrosDeSistema.PreencherSomenteNumeros();
        }
        // Campos mês e ano de expiração não podem ser nulo
        if(cartao.getMesVencimento() > 12 || cartao.getMesVencimento() <= 0) {
            throw new ErrosDeSistema.CampoNaoInformado("mês de vencimento");
        }
        if (cartao.getAnoVencimento() < Year.now().getValue()) {
            throw new ErrosDeSistema.CampoNaoInformado("ano de vencimento");
        }
        // Campo código não aceita letras
        if (cartao.getCodigoDeSeguranca() == null || cartao.getCodigoDeSeguranca().isEmpty()) {
            throw new ErrosDeSistema.CampoNaoInformado("CVV");
        }
        if (cartao.getCodigoDeSeguranca().length() < 3 || cartao.getCodigoDeSeguranca().length() > 4) {
            throw new ErrosDeSistema.CampoNaoInformado("CVV");
        }
        if(!cartao.getCodigoDeSeguranca().matches("\\d+")) {
            throw new ErrosDeSistema.PreencherSomenteNumeros();
        }
    }

}
