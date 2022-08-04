package javafestas.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Orcamento implements Serializable {

    private int cdOrcamento;
    private LocalDate data;
    private double valor;
    private boolean pago;
    private List<ItemDeOrcamento> itensDeOrcamento;
    private Cliente cliente;
    private Local local;

    public Orcamento() {
    }

    public Orcamento(int cdorcamento, LocalDate data, double valor, boolean pago) {
        this.cdOrcamento = cdorcamento;
        this.data = data;
        this.valor = valor;
        this.pago = pago;
    }

    public int getCdOrcamento() {
        return cdOrcamento;
    }

    public void setCdOrcamento(int cdOrcamento) {
        this.cdOrcamento = cdOrcamento;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public List<ItemDeOrcamento> getItensDeOrcamento() {
        return itensDeOrcamento;
    }

    public void setItensDeOrcamento(List<ItemDeOrcamento> itensDeOrcamento) {
        this.itensDeOrcamento = itensDeOrcamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
    
}