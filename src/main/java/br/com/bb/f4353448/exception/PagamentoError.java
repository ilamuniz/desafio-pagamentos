package br.com.bb.f4353448.exception;

public interface PagamentoError {
    String MSG_NULO = "A mensagem não pode ser nula.";
    String CODIGO_NULO = "O código não pode ser nulo.";

    String getCode();

    String getMessage();

    default String getMessage(Object... messageArgs) {
        return String.format(this.getMessage(), messageArgs);
    }

    default int getStatusCode() {
        return 422;
    }
}
