package com.cme.model;

import com.cme.essencial.Extenso;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos Maemo
 */
public class Medicamento implements Serializable {

    private int ordem;

    private Integer idMedicamento, idPrescricao;
    private String medicament, posologia, quantidade, presc, quantAviado;
    private boolean canEdit;

    public Medicamento(String medicament, String posologia, String quantidade, String presc) {
        this.medicament = medicament;
        this.posologia = posologia;
        this.quantidade = quantidade;
        this.presc = presc;
        canEdit = false;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.ordem;
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
        final Medicamento other = (Medicamento) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

    public Integer getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Integer idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public String getMedicament() {
        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getQuantidade() {

        return quantidade + " (" + new Extenso(Integer.parseInt(quantidade)).toString() + ")";

    }

    public String getQuantidadeX() {

        return quantidade;

    }

    public String getQuantAviado() {
        return quantAviado;
    }

    public void setQuantAviado(String quantAviado) {
        this.quantAviado = quantAviado;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getPresc() {

        if ("falso".equals(presc)) {
            return "Indisponível";
        } else if ("verdade".equals(presc)) {
            return "Disponível  ";
        }

        return presc;
    }

    public void setPresc(String presc) {
        this.presc = presc;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

}
