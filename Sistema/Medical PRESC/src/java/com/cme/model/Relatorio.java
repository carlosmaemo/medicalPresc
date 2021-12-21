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
public class Relatorio implements Serializable {
    
    private Integer idPrescricao, idPaciente;
    private String data, clinico, clinicoMorada, clinicoContacto, pac, nome, apelido, sexo, idade, gestante, morada, contacto, peso, nrDoc, tipoDoc;
    private String pacFiltro, dataInicial, dataFinal;
    private String pacConf;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getPacConf() {
        
        return pacConf;
    }

    public void setPacConf(String pacConf) {
        this.pacConf = pacConf;
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

    public String getNrDoc() {
        return nrDoc;
    }

    public void setNrDoc(String nrDoc) {
        this.nrDoc = nrDoc;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getPacFiltro() {
        return pacFiltro;
    }

    public void setPacFiltro(String pacFiltro) {
        this.pacFiltro = pacFiltro;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idPrescricao;
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
        final Relatorio other = (Relatorio) obj;
        if (this.idPrescricao != other.idPrescricao) {
            return false;
        }
        return true;
    }
    
    
}
