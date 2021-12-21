package com.cme.dao;

import com.cme.exception.ErroSistema;
import com.cme.model.Usuario;
import com.cme.util.Conecxao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements Serializable {

    public void actualizar(Usuario usuario, Integer idUsuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update usuario set nomeUsuario=?, password=?, sexoUsuario=?, contactoUsuario=?, moradaUsuario=? where idUsuario=?");

            ps.setString(1, usuario.getConfNome());
            ps.setString(2, usuario.getConfPasswordUsuario());
            ps.setString(3, usuario.getConfSexo());
            ps.setString(4, usuario.getConfContacto());
            ps.setString(5, usuario.getConfMorada());
            ps.setInt(6, usuario.getConfIdUsuario());

            ps.close();
            ps.execute();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o usuário!", ex);
        }

    }

    public void salvar(Usuario usuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("INSERT INTO `usuario`(`nomeUsuario`, `username`, `password`, `acesso`, `sexoUsuario`, `contactoUsuario`, `moradaUsuario`) VALUES (?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getUsername());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getAcesso());
            ps.setString(5, usuario.getSexo());
            ps.setString(6, usuario.getContacto());
            ps.setString(7, usuario.getMorada());
            ps.execute();
            
            ps.close();
            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar o usuário!", ex);
        }

    }

    public void deletar(int idUsuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from usuario where idUsuario=?");
            ps.setInt(1, idUsuario);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o usuário!", ex);
        }
    }

    public List<Usuario> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario");

            ResultSet rs = ps.executeQuery();

            List<Usuario> usuarios = new ArrayList();

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNome(rs.getString("nomeUsuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setAcesso(rs.getString("acesso"));
                usuario.setSexo(rs.getString("sexoUsuario"));
                usuario.setContacto(rs.getString("contactoUsuario"));
                usuario.setMorada(rs.getString("moradaUsuario"));

                usuarios.add(usuario);

            }

            return usuarios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o usuário!", ex);
        }

    }

    public List<Integer> buscarCodigo() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT idUsuario FROM usuario;");

            ResultSet rs = ps.executeQuery();

            List<Integer> usuarios = new ArrayList<>();

            while (rs.next()) {

                usuarios.add(rs.getInt("idUsuario"));
            }

            return usuarios;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o código do usuário!", ex);

        }

    }

    public Boolean entrar(Usuario usuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Conecxao.fecharConexao();
                return true;

            } else {

                Conecxao.fecharConexao();

                return false;
            }

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }

    }

    public String verificarNivelAcesso(Usuario usuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("acesso");

                Conecxao.fecharConexao();
                return x;

            } else {

                Conecxao.fecharConexao();
                return null;

            }

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }

    }

    public Integer verificarIdUsuario(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int x = rs.getInt("idUsuario");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return 0;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

    public String verificarNomeUsuario(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("username");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

    public String verificarNomeCompleto(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("nomeUsuario");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

    public String verificarPasswordUsuario(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("password");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

    public String verificarSexo(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("sexoUsuario");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

    public String verificarContacto(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("contactoUsuario");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

    public String verificarMorada(Usuario usuario) throws ErroSistema {

        try {

            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from usuario where username=? and password=?");

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getPassword());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String x = rs.getString("moradaUsuario");

                Conecxao.fecharConexao();
                return x;

            }

            Conecxao.fecharConexao();
            return null;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao iniciar a sessão!", ex);
        }
    }

}
