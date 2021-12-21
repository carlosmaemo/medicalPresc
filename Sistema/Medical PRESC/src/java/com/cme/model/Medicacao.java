/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cme.model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos Maemo
 */
@ManagedBean
@SessionScoped
public class Medicacao implements Serializable {
    
    private Integer idMedicacao, idPosologia;
    private String medicacao, codigoPresc;
    private String posologia, codigoPos;

    public Integer getIdMedicacao() {
        return idMedicacao;
    }

    public void setIdMedicacao(Integer idMedicacao) {
        this.idMedicacao = idMedicacao;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getCodigoPresc() {
        return codigoPresc;
    }

    public void setCodigoPresc(String codigoPresc) {
        this.codigoPresc = codigoPresc;
    }

    public String getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(String codigoPos) {
        this.codigoPos = codigoPos;
    }

    public Integer getIdPosologia() {
        return idPosologia;
    }

    public void setIdPosologia(Integer idPosologia) {
        this.idPosologia = idPosologia;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idMedicacao;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Medicacao other = (Medicacao) obj;
        if (this.idMedicacao != other.idMedicacao) {
            return false;
        }
        return true;
    }
    
    
}
