package br.com.bb.f4353448.exception;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface PagamentoErrorMap extends Map<String, String>, PagamentoError {
    String MAP_NULO = "Map das variáveis monitoradas informada esta nulo.";

    Map<String, String> getVars();

    default int size() {
        return this.getVars().size();
    }

    default boolean isEmpty() {
        return this.getVars().isEmpty();
    }

    default boolean containsKey(Object key) {
        return this.getVars().containsKey(key);
    }

    default boolean containsValue(Object value) {
        return this.getVars().containsValue(value);
    }

    default String get(Object key) {
        return (String)this.getVars().get(key);
    }

    default String put(String key, String value) {
        return (String)this.getVars().put(key, value);
    }

    default String remove(Object key) {
        return (String)this.getVars().remove(key);
    }

    default void putAll(Map<? extends String, ? extends String> m) {
        this.getVars().putAll(m);
    }

    default void clear() {
        this.getVars().clear();
    }

    default Set<String> keySet() {
        return this.getVars().keySet();
    }

    default Collection<String> values() {
        return this.getVars().values();
    }

    default Set<Map.Entry<String, String>> entrySet() {
        return this.getVars().entrySet();
    }
}
