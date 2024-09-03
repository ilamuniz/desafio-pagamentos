package br.com.bb.f4353448.exception;

import java.util.Map;


public class ErrosDeSistema {

    public static final class CampoNaoInformado extends PagamentoException {
        public static final String MSG_ERRO = "O campo %s não foi informado corretamente.";
        public static final String COD_ERRO = "400"; // Codigo do erro

        public CampoNaoInformado(String nomeCampo) {
            super(COD_ERRO, String.format(MSG_ERRO, nomeCampo), Map.of("NOME_CAMPO", nomeCampo));

        }
    }

    public static final class PreenchimentoIncorretoCPF extends PagamentoException {
        public static final String MSG_ERRO = "Campo CPF ou CNPJ deve conter 11 dígitos para CPF ou 14 dígitos para CNPJ.";
        public static final String COD_ERRO = "422"; // Codigo do erro

        public PreenchimentoIncorretoCPF() {
            super(COD_ERRO, MSG_ERRO);
        }
    }

    public static final class PreenchimentoIncorretoNumeroCartao extends PagamentoException {
        public static final String MSG_ERRO = "Digite a numeração completa do cartão (mínimo de 13 dígitos e máximo de 19 dígitos) somente com números, hífen ('-') ou ponto ('.').";
        public static final String COD_ERRO = "422"; // Codigo do erro

        public PreenchimentoIncorretoNumeroCartao() {
            super(COD_ERRO, MSG_ERRO);
        }
    }

    public static final class PreencherSomenteNumeros extends PagamentoException {
        public static final String MSG_ERRO = "Por favor, preencher campo %s somente com números.";
        public static final String COD_ERRO = "422"; // Codigo do erro

        public PreencherSomenteNumeros(String nomeCampo) {
            super(COD_ERRO, String.format(MSG_ERRO, nomeCampo), Map.of("NOME_CAMPO", nomeCampo));
        }
    }

    public static final class CartaoNaoEncontrado extends PagamentoException {
        public static final String MSG_ERRO = "Cartão com ID %d não encontrado";
        public static final String COD_ERRO = "404";

        public CartaoNaoEncontrado(long numeroPagamento) {
            super(COD_ERRO, String.format(MSG_ERRO, numeroPagamento));
            this.put("NUMERO_PAGAMENTO", String.valueOf(numeroPagamento));
        }
    }

}
