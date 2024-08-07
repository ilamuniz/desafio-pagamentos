package br.com.bb.f4353448.models;

import br.com.bb.f4353448.exception.ErrosDeSistema;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Cartao")
public class Cartao extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cpf;
    private BigDecimal pagamento;

    @Column(name = "nome_do_titular")
    private String nomeTitular;

    @Column(name = "numero_do_cartao")
    private String numeroDoCartao;

    @Column(name = "data_de_expiracao")
    private Date dataDeExpiracao;

    @Column(name = "codigo_de_seguranca")
    private int codigoDeSeguranca;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setPagamento(BigDecimal pagamento) {
        this.pagamento = pagamento;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public void setNumeroDoCartao(String numeroDoCartao) {
        this.numeroDoCartao = numeroDoCartao;
    }

    public void setDataDeExpiracao(Date dataDeExpiracao) {
        this.dataDeExpiracao = dataDeExpiracao;
    }

    public void setCodigoDeSeguranca(int codigoDeSeguranca) {
        this.codigoDeSeguranca = codigoDeSeguranca;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public BigDecimal getPagamento() {
        return pagamento;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public Date getDataDeExpiracao() {
        return dataDeExpiracao;
    }

    public int getCodigoDeSeguranca() {
        return codigoDeSeguranca;
    }

    @Override
    public String toString() {
        return "Dados do cartão {" +
                "id = " + this.id +
                ", Cliente = " + this.nome + '\'' +
                ", CPF = " + this.cpf +
                ", Valor da compra = " + String.format("%.2f", this.pagamento) +
                ", Nome do titular do cartão = " + this.nomeTitular +
                ", Número do cartão = " + this.numeroDoCartao +
                ", Data de expiração = " + this.dataDeExpiracao +
                ", Código de segurança = " + this.codigoDeSeguranca +
                '}';
    }

    @ElementCollection
    public List<String> adicionarCliente() {
        List<String> lista = new ArrayList<>();

        lista.add(this.toString());

        return lista;
    }
}
