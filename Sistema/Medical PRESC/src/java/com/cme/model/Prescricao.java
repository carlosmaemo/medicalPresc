package com.cme.model;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Carlos Maemo
 */
@ManagedBean
@SessionScoped
public class Prescricao implements Serializable {

    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private boolean novoRegisto = false;

    private int ordem;

    private String data, dataInt, clinico, clinicoMorada, clinicoContacto;

    private Integer idPrescricao;
    private Integer idPaciente;
    private String pac;
    private String nome;
    private String apelido;
    private String idade;
    private String sexo;
    private String gestante;
    private String morada;
    private String contacto;
    private String peso;
    private String tipoDoc;
    private String nrDoc;
    private String estado;

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
        final Prescricao other = (Prescricao) obj;
        if (this.ordem != other.ordem) {
            return false;
        }
        return true;
    }

    public Boolean getNovoRegisto() {
        return novoRegisto;
    }

    public void setNovoRegisto(boolean novoRegisto) {
        this.novoRegisto = novoRegisto;
    }

    public String getData() {
        this.data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        return data;
    }

    public void setData(String data) {
        this.data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public String getDataInt() {
        this.dataInt = new SimpleDateFormat("ddMMyyyy").format(new Date());
        return dataInt;
    }

    public void setDataInt(String dataInt) {
         this.dataInt = new SimpleDateFormat("ddMMyyyy").format(new Date());
    }

    public String getClinico() {
        return clinico;
    }

    public void setClinico(String clinico) {
        this.clinico = clinico;
    }

    public String getClinicoMorada() {
        return clinicoMorada;
    }

    public void setClinicoMorada(String clinicoMorada) {
        this.clinicoMorada = clinicoMorada;
    }

    public String getClinicoContacto() {
        return clinicoContacto;
    }

    public void setClinicoContacto(String clinicoContacto) {
        this.clinicoContacto = clinicoContacto;
    }

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getPac() {
        return pac;
    }

    public void setPac(String pac) {
        this.pac = pac;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getGestante() {
        return gestante;
    }

    public void setGestante(String gestante) {
        this.gestante = gestante;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNrDoc() {
        return nrDoc;
    }

    public void setNrDoc(String nrDoc) {
        this.nrDoc = nrDoc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
