package br.com.bb.f4353448.exception;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PagamentoException extends Exception implements PagamentoErrorMap {
    private final Map<String, String> vars;
    private final String code;
    private PagamentoError pagamentoError;

    public PagamentoException(String code, String message) {
        super((String) Objects.requireNonNull(message, "A mensagem não pode ser nula."));
        this.code = (String)Objects.requireNonNull(code, "O código não pode ser nulo.");
        this.vars = new HashMap();
    }

    public PagamentoException(PagamentoError pagamentoError) {
        this(pagamentoError.getCode(), pagamentoError.getMessage());
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(PagamentoError pagamentoError, Object... messageArgs) {
        this(pagamentoError.getCode(), pagamentoError.getMessage(messageArgs));
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(String code, String message, @NotNull Map<String, String> vars) {
        this(code, message);
        this.vars.putAll((Map)Objects.requireNonNull(vars, "Map das variáveis monitoradas informada esta nulo."));
    }

    public PagamentoException(PagamentoError pagamentoError, @NotNull Map<String, String> vars) {
        this(pagamentoError.getCode(), pagamentoError.getMessage(), vars);
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(PagamentoError pagamentoError, @NotNull Map<String, String> vars, Object... messageArgs) {
        this(pagamentoError.getCode(), pagamentoError.getMessage(messageArgs), vars);
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(String code, String message, Throwable cause) {
        this(code, message);
        this.initCause(cause);
    }

    public PagamentoException(PagamentoError pagamentoError, Throwable cause) {
        this(pagamentoError.getCode(), pagamentoError.getMessage(), cause);
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(PagamentoError pagamentoError, Throwable cause, Object... messageArgs) {
        this(pagamentoError.getCode(), pagamentoError.getMessage(messageArgs), cause);
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(String code, String message, Map<String, String> vars, Throwable cause) {
        this(code, message, vars);
        this.initCause(cause);
    }

    public PagamentoException(PagamentoError pagamentoError, Map<String, String> vars, Throwable cause) {
        this(pagamentoError, vars);
        this.initCause(cause);
        this.pagamentoError = pagamentoError;
    }

    public PagamentoException(PagamentoError pagamentoError, Map<String, String> vars, Throwable cause, Object... messageArgs) {
        this(pagamentoError, vars, messageArgs);
        this.initCause(cause);
        this.pagamentoError = pagamentoError;
    }

    public String getCode() {
        return this.code;
    }

    public Map<String, String> getVars() {
        return this.vars;
    }

    public int getStatusCode() {
        return (Integer) Optional.ofNullable(this.pagamentoError).map(PagamentoError::getStatusCode).orElse(PagamentoErrorMap.super.getStatusCode());
    }
}
