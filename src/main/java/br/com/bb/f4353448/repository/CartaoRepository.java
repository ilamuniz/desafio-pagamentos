package br.com.bb.f4353448.repository;

import br.com.bb.f4353448.models.Cartao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartaoRepository implements PanacheRepository<Cartao> {

    public Cartao findById(Long id) {
        return find("id", id).firstResult();
    }

}
