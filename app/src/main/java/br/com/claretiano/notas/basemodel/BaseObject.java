package br.com.claretiano.notas.basemodel;

import java.io.Serializable;
import java.util.Date;

public abstract class BaseObject implements Serializable {

    protected Date datahora_criacao;
    protected Date datahora_alteracao;

    public Date getDatahora_alteracao() {
        return datahora_alteracao;
    }

    public void setDatahora_alteracao(Date datahora_alteracao) {
        this.datahora_alteracao = datahora_alteracao;
    }

    public Date getDatahora_criacao() {
        return datahora_criacao;
    }

    public void setDatahora_criacao(Date datahora_criacao) {
        this.datahora_criacao = datahora_criacao;
    }
}                                                                  
