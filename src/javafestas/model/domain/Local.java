package javafestas.model.domain;

import java.io.Serializable;

public class Local implements Serializable {

    private int cdLocal;
    private String nome;
    private String uf;
    private String cidade;
    private String cep;

    public Local(){
    }
    
    public Local(int cdLocal, String nome, String uf, String cidade, String cep) {
        this.cdLocal = cdLocal;
        this.nome = nome;
        this.uf = uf;
        this.cidade = cidade;
        this.cep = cep;
    }

    public int getCdLocal() {
        return cdLocal;
    }

    public void setCdLocal(int cdLocal) {
        this.cdLocal = cdLocal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
    
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}

