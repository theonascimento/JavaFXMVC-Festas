package javafestas.model.domain;

import java.io.Serializable;

public class ItemDeOrcamento implements Serializable {

    private int cdItemDeOrcamento;
    private int quantidade;
    private double valor;
    private Produto produto;
    private Orcamento orcamento;

    public ItemDeOrcamento() {
    }

    public int getCdItemDeOrcamento() {
        return cdItemDeOrcamento;
    }

    public void setCdItemDeOrcamento(int cdItemDeOrcamento) {
        this.cdItemDeOrcamento = cdItemDeOrcamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }
    
}
