package com.cme.util;

import com.cme.exception.ErroSistema;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conecxao {
    
    private static Connection conexao;
    private static final String URL_CONEXAO = "jdbc:sqlite:/Users/cmaemo/Documents/Sistemas (LTS)/incluir n reposit/Medical PRESC/Base de Dados/db_medical_presc.db";
    private static final String USUARIO = "root";
    private static final String SENHA = "";
    
    public static Connection getConexao() throws ErroSistema {
        if(conexao == null) {
            
            try {
                Class.forName("org.sqlite.JDBC");
                conexao = DriverManager.getConnection(URL_CONEXAO);
            } catch (ClassNotFoundException ex) {
                throw new ErroSistema("O drive de banco de dados não foi encontrado!", ex);
            } catch (SQLException ex) {
                throw new ErroSistema("Não foi possível conectar ao base de dados!", ex);
            }
        }
        return conexao;
    }
    
    public static void fecharConexao() throws ErroSistema {
        if(conexao != null) {
            try {
                conexao.close();
                conexao = null;
            } catch (SQLException ex) {
                throw new ErroSistema("Erro ao fechar conexão com a base de dados!", ex);
            }
        }
    }
    
}
