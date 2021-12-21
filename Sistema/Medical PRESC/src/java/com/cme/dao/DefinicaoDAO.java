/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cme.dao;

import com.cme.exception.ErroSistema;
import com.cme.model.Usuario;
import com.cme.util.Conecxao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author carlos.francisco
 */
public class DefinicaoDAO implements Serializable {
    
    public void salvar(Usuario usuario) throws ErroSistema, SQLException {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

                ps = conexao.prepareStatement("update definicoes set emailReceive=?, emailSender=?, emailSenderPassword=? where idDefinicoes=?");

                ps.setString(1, usuario.getConfEmailReceptor());
                ps.setString(2, usuario.getConfEmailEmissor());
                ps.setString(3, usuario.getConfPasswordEmailEmissor());
                ps.setString(4, "1");
                ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao salvar as definições!", ex);
        }

    }
    
    public String verificarEmissor() throws ErroSistema {
        
        try {
            
            Connection conexao = Conecxao.getConexao();
            
            PreparedStatement ps = conexao.prepareStatement("select * from definicoes where idDefinicoes=?");
            
            ps.setString(1, "1");
                
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

            String x = rs.getString("emailSender");
                
            Conecxao.fecharConexao();
            return x;

            }
                
            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao carregar as definições!", ex);
        }
        }

    public String verificarEmissorPassword() throws ErroSistema {
        
        try {
            
            Connection conexao = Conecxao.getConexao();
            
            PreparedStatement ps = conexao.prepareStatement("select * from definicoes where idDefinicoes=?");
            
            ps.setString(1, "1");
                
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

            String x = rs.getString("emailSenderPassword");
                
            Conecxao.fecharConexao();
            return x;

            }
                
            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao carregar as definições!", ex);
        }
        }
    
    public String verificarReceptor() throws ErroSistema {
        
        try {
            
            Connection conexao = Conecxao.getConexao();
            
            PreparedStatement ps = conexao.prepareStatement("select * from definicoes where idDefinicoes=?");
            
            ps.setString(1, "1");
                
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

            String x = rs.getString("emailReceive");
                
            Conecxao.fecharConexao();
            return x;

            }
                
            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao carregar as definições!", ex);
        }
        }
}
