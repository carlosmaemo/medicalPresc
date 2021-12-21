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
public class Usuario implements Serializable {
    
    private Integer idUsuario;
    private String nome;
    private String username;
    private String password;
    private String acesso;
    private String sexo;
    private String morada;
    private String contacto;
    private String confContacto;
    private String confMorada;
    private String confNomeUsuario;
    private String confNomeUsuarioWenIsDelete;
    public String confNome;
    private String confNomeWenIsDelete;
    private String confPasswordUsuario;
    private String confPasswordUsuarioWenIsDelete;
    private String confSexo;
    private String confAcesso;
    private Integer confIdUsuario;
    private String confEmailReceptor;
    private String confEmailEmissor;
    private String confPasswordEmailEmissor;
    private String confEmailReceptorWenIsDelete;
    private String confEmailEmissorWenIsDelete;
    private String confPasswordEmailEmissorWenIsDelete;


    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getConfPasswordUsuario() {
        return confPasswordUsuario;
    }

    public void setConfPasswordUsuario(String confPasswordUsuario) {
        this.confPasswordUsuario = confPasswordUsuario;
    }

    public String getConfSexo() {
        return confSexo;
    }

    public void setConfSexo(String confSexo) {
        this.confSexo = confSexo;
    }

    public String getConfAcesso() {
        return confAcesso;
    }

    public void setConfAcesso(String confAcesso) {
        this.confAcesso = confAcesso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAcesso() {
        return acesso;
    }
    

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public String getConfNomeUsuario() {
        return confNomeUsuario;
    }

    public void setConfNomeUsuario(String confNomeUsuario) {
        this.confNomeUsuario = confNomeUsuario;
    }
    
        public String getConfNome() {
        return confNome;
    }

    public void setConfNome(String confNome) {
        this.confNome = confNome;
    }
    
    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Integer getConfIdUsuario() {
        return confIdUsuario;
    }

    public void setConfIdUsuario(Integer confIdUsuario) {
        this.confIdUsuario = confIdUsuario;
    }

    public String getConfContacto() {
        return confContacto;
    }

    public void setConfContacto(String confContacto) {
        this.confContacto = confContacto;
    }

    public String getConfMorada() {
        return confMorada;
    }

    public void setConfMorada(String confMorada) {
        this.confMorada = confMorada;
    }
    
    public String getConfNomeUsuarioWenIsDelete() {
        return confNomeUsuarioWenIsDelete;
    }

    public void setConfNomeUsuarioWenIsDelete(String confNomeUsuarioWenIsDelete) {
        this.confNomeUsuarioWenIsDelete = confNomeUsuarioWenIsDelete;
    }

    public String getConfNomeWenIsDelete() {
        return confNomeWenIsDelete;
    }

    public void setConfNomeWenIsDelete(String confNomeWenIsDelete) {
        this.confNomeWenIsDelete = confNomeWenIsDelete;
    }

    public String getConfPasswordUsuarioWenIsDelete() {
        return confPasswordUsuarioWenIsDelete;
    }

    public void setConfPasswordUsuarioWenIsDelete(String confPasswordUsuarioWenIsDelete) {
        this.confPasswordUsuarioWenIsDelete = confPasswordUsuarioWenIsDelete;
    }

    public String getConfEmailReceptor() {
        return confEmailReceptor;
    }

    public void setConfEmailReceptor(String confEmailReceptor) {
        this.confEmailReceptor = confEmailReceptor;
    }

    public String getConfEmailEmissor() {
        return confEmailEmissor;
    }

    public void setConfEmailEmissor(String confEmailEmissor) {
        this.confEmailEmissor = confEmailEmissor;
    }

    public String getConfPasswordEmailEmissor() {
        return confPasswordEmailEmissor;
    }

    public void setConfPasswordEmailEmissor(String confPasswordEmailEmissor) {
        this.confPasswordEmailEmissor = confPasswordEmailEmissor;
    }

    public String getConfEmailReceptorWenIsDelete() {
        return confEmailReceptorWenIsDelete;
    }

    public void setConfEmailReceptorWenIsDelete(String confEmailReceptorWenIsDelete) {
        this.confEmailReceptorWenIsDelete = confEmailReceptorWenIsDelete;
    }

    public String getConfEmailEmissorWenIsDelete() {
        return confEmailEmissorWenIsDelete;
    }

    public void setConfEmailEmissorWenIsDelete(String confEmailEmissorWenIsDelete) {
        this.confEmailEmissorWenIsDelete = confEmailEmissorWenIsDelete;
    }

    public String getConfPasswordEmailEmissorWenIsDelete() {
        return confPasswordEmailEmissorWenIsDelete;
    }

    public void setConfPasswordEmailEmissorWenIsDelete(String confPasswordEmailEmissorWenIsDelete) {
        this.confPasswordEmailEmissorWenIsDelete = confPasswordEmailEmissorWenIsDelete;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.idUsuario;
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
        final Usuario other = (Usuario) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }
    
    
}
