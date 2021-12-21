package com.cme.dao;

import com.cme.exception.ErroSistema;
import com.cme.model.Prescricao;
import com.cme.util.Conecxao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.print.DocFlavor;
import org.codehaus.groovy.runtime.ArrayUtil;

/**
 *
 * @author Carlos Maemo
 */

public class PrescricaoDAO {

    public boolean act;

//    public void salvar(Prescricao prescricao) throws ErroSistema {
//
//        try {
//            Connection conexao = Conecxao.getConexao();
//            PreparedStatement ps;
//
//                ps = conexao.prepareStatement("INSERT INTO `usuario`(`nomeUsuario`, `username`, `password`, `acesso`, `sexo`, `funcao`) VALUES (?, ?, ?, ?, ?, ?)");
//
//                ps.setString(1, usuario.getNome());
//                ps.setString(2, usuario.getUsername());
//                ps.setString(3, usuario.getPassword());
//                ps.setString(4, usuario.getAcesso());
//                ps.setString(5, usuario.getSexo());
//                ps.setString(6, usuario.getFuncao());
//                ps.execute();
//                
//            Conecxao.fecharConexao();
//
//        } catch (SQLException ex) {
//            throw new ErroSistema("Erro ao cadastrar o usuário!", ex);
//        }
//
//    }

    public List<Prescricao> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from paciente");

            ResultSet rs = ps.executeQuery();

            List<Prescricao> pacientes = new ArrayList();

            while (rs.next()) {

                Prescricao paciente = new Prescricao();

                //paciente.setNrRegistro(rs.getInt("nrRegistro"));
                
                pacientes.add(paciente);

            }

            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
        }

    }

    public List<Prescricao> buscarFiltro(Prescricao pacientex) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from paciente where dataCadastro >= ? and dataCadastro <= ?");

//            if (pacientex.getDataInicial() == null || pacientex.getDataFinal() == null) {
//
//                List<Prescricao> pacientes = new ArrayList();
//
//                return pacientes;
//            }
//            
//            ps.setString(1, pacientex.getDataInicial());      
//            ps.setString(2, pacientex.getDataFinal());
//
//            
//            ps.setString(1, Long.toString(pacientex.getDataInicial().getTime()).substring(0, Long.toString(pacientex.getDataInicial().getTime()).length() - 8));
//            ps.setString(2, Long.toString(pacientex.getDataFinal().getTime()).substring(0, Long.toString(pacientex.getDataFinal().getTime()).length() - 8));

            ResultSet rs = ps.executeQuery();

            List<Prescricao> pacientes = new ArrayList();

            while (rs.next()) {

                Prescricao paciente = new Prescricao();

//                paciente.setNrRegistro(rs.getInt("nrRegistro"));
//                paciente.setNome(rs.getString("nome"));
//             
                pacientes.add(paciente);

            }

            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);

        }

    }

    public List<Integer> buscarCodigo() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT nrRegistro FROM paciente;");

            ResultSet rs = ps.executeQuery();

            List<Integer> pacientes = new ArrayList<>();

            while (rs.next()) {

                pacientes.add(rs.getInt("nrRegistro"));

            }

            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o código do paciente!", ex);

        }

    }

    public Prescricao confirmar(String c, int idUsuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from paciente where pac=?");
            PreparedStatement pst2 = conexao.prepareStatement("Select * from usuario where idUsuario=?");

            pst.setString(1, c);
            pst.execute();
            
            pst2.setInt(1, idUsuario);
            pst2.execute();
            
            ResultSet rs = pst.executeQuery();
            ResultSet rs2 = pst2.executeQuery();

            Prescricao ps = new Prescricao();
            
            while (rs2.next()) {

                ps.setClinico(rs2.getString("nomeUsuario"));
                ps.setClinicoMorada(rs2.getString("morada"));
                ps.setClinicoContacto(rs2.getString("contacto"));

            }
            
            while (rs.next()) {

                ps.setIdPaciente(rs.getInt("idPaciente"));
                ps.setPac(rs.getString("pac"));
                ps.setNome(rs.getString("nome"));
                ps.setApelido(rs.getString("apelido"));
                ps.setIdade(rs.getString("idade"));
                ps.setSexo(rs.getString("sexo"));
                ps.setGestante(rs.getString("gestante"));
                ps.setMorada(rs.getString("morada"));
                ps.setContacto(rs.getString("contacto"));
                ps.setPeso(rs.getString("peso"));
                ps.setTipoDoc(rs.getString("tipoDoc"));
                ps.setNrDoc(rs.getString("nrDoc"));

            }
            
            return ps;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
        }
    }

    public List<String> buscarPac() throws ErroSistema {
            
        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT pac FROM paciente;");

            ResultSet rs = ps.executeQuery();

            List<String> pacientes = new ArrayList<>();

            while (rs.next()) {

                pacientes.add(rs.getString("pac"));
            }
            
            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o código do paciente!", ex);

        }

    }
    
}
