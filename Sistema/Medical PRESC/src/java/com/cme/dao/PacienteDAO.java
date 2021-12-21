package com.cme.dao;

import com.cme.exception.ErroSistema;
import com.cme.model.Paciente;
import com.cme.model.Prescricao;
import com.cme.util.Conecxao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PacienteDAO implements Serializable {

    public boolean verificar(Paciente paciente) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from paciente where pac='" + paciente.getPac() + "'");
    
            ResultSet rs = ps.executeQuery();

            Paciente p = new Paciente();

            while (rs.next()) {
                p.setPac(rs.getString("pac"));
            }
            
            if(p.getPac() != null) {
            
            if(p.getPac().equals(paciente.getPac())) {
                return true;
            }
            else {
                return false;
            }
            }else {
                return false;
            }
            
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao verificar o paciente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        return false;
        
    }
    
    public void actualizar(Paciente paciente) throws ErroSistema {
        
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

                ps = conexao.prepareStatement("update paciente set nome=?, apelido=?, idade=?, sexo=?, gestante=?, morada=?, contacto=?, peso=?, tipoDoc=?, nrDoc=? where pac=?");

                ps.setString(1, paciente.getNome());
                ps.setString(2, paciente.getApelido());
                ps.setString(3, paciente.getIdade());
                ps.setString(4, paciente.getSexo());
                ps.setString(5, paciente.getGestante());
                ps.setString(6, paciente.getMorada());
                ps.setString(7, paciente.getContacto());
                ps.setString(8, paciente.getPeso());
                ps.setString(9, paciente.getTipoDoc());
                ps.setString(10, paciente.getNrDoc());
                ps.setString(11, paciente.getPac());
                
                ps.execute();
                
            } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar o paciente!", ex);
        }
        
    }
    
    public void salvar(Paciente paciente) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

                ps = conexao.prepareStatement("INSERT INTO `paciente`(`pac`, `nome`, `apelido`, `idade`, `sexo`, `gestante`, `morada`, `contacto`, `peso`, `tipoDoc`, `nrDoc`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                
                ps.setString(1, paciente.getPac());
                ps.setString(2, paciente.getNome());
                ps.setString(3, paciente.getApelido());
                ps.setString(4, paciente.getIdade());
                ps.setString(5, paciente.getSexo());
                ps.setString(6, paciente.getGestante());
                ps.setString(7, paciente.getMorada());
                ps.setString(8, paciente.getContacto());
                ps.setString(9, paciente.getPeso());
                ps.setString(10, paciente.getTipoDoc());
                ps.setString(11, paciente.getNrDoc());
                
                ps.execute();
                
            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar o usuário!", ex);
        }

    }

    public void deletar(int idPaciente) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from paciente where idPaciente=?");
            ps.setInt(1, idPaciente);
            ps.execute();
            
            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o paciente!", ex);
        }
    }

    public List<Paciente> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from paciente");

            ResultSet rs = pst.executeQuery();

            List<Paciente> pacientes = new ArrayList();

            while (rs.next()) {

                Paciente ps = new Paciente();
                
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

                pacientes.add(ps);

            }
            
            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
        }

    }

    public List<Integer> buscarCodigo() throws ErroSistema {
            
        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT idPaciente FROM paciente;");

            ResultSet rs = ps.executeQuery();

            List<Integer> pacientes = new ArrayList<>();

            while (rs.next()) {

                pacientes.add(rs.getInt("idPaciente"));
            }
            
            return pacientes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o código do paciente!", ex);

        }

    }
   
}