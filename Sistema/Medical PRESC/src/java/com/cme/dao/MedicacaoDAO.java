package com.cme.dao;

import com.cme.exception.ErroSistema;
import com.cme.model.Medicacao;
import com.cme.util.Conecxao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class MedicacaoDAO implements Serializable {

    public boolean verificarPresc(Medicacao medic_presc) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from medic_prescricao where codigo='" + medic_presc.getCodigoPresc() + "'");

            ResultSet rs = ps.executeQuery();

            Medicacao p = new Medicacao();

            while (rs.next()) {
                p.setCodigoPresc(rs.getString("codigo"));
            }

            if (p.getCodigoPresc() != null) {

                if (p.getCodigoPresc().equals(medic_presc.getCodigoPresc())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public boolean verificarPos(Medicacao medic_presc) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("Select * from medic_posologia where codigo='" + medic_presc.getCodigoPos() + "'");

            ResultSet rs = ps.executeQuery();

            Medicacao p = new Medicacao();

            while (rs.next()) {
                p.setCodigoPos(rs.getString("codigo"));
            }

            if (p.getCodigoPos() != null) {

                if (p.getCodigoPos().equals(medic_presc.getCodigoPos())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return false;

    }

    public void actualizarPresc(Medicacao medic_presc) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("update medic_prescricao set prescricao=? where codigo=?");
            ps.setString(1, medic_presc.getMedicacao());
            ps.setString(2, medic_presc.getCodigoPresc());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar os dados!", ex);
        }
    }

    public void actualizarPos(Medicacao medic_presc) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("update medic_posologia set posologia=? where codigo=?");
            ps.setString(1, medic_presc.getPosologia());
            ps.setString(2, medic_presc.getCodigoPos());
            ps.execute();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao actualizar os dados!", ex);
        }
    }

    public void salvarPresc(Medicacao medic_presc) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("INSERT INTO `medic_prescricao`(`prescricao`, `codigo`) VALUES (?, ?)");
            ps.setString(1, medic_presc.getMedicacao());
            ps.setString(2, medic_presc.getCodigoPresc());
            ps.execute();
            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar a prescrição!", ex);
        }
    }

    public void salvarPos(Medicacao medic_presc) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("INSERT INTO `medic_posologia`(`posologia`, `codigo`) VALUES (?, ?)");
            ps.setString(1, medic_presc.getPosologia());
            ps.setString(2, medic_presc.getCodigoPos());
            ps.execute();
            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar a posologia!", ex);
        }
    }

    public void deletarPresc(int medic_id) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("delete from medic_prescricao where idMedicPresc=?");
            ps.setInt(1, medic_id);
            ps.execute();
            Conecxao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover os dados!", ex);
        }
    }

    public void deletarPos(int medic_id) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;
            ps = conexao.prepareStatement("delete from medic_posologia where idMedicPos=?");
            ps.setInt(1, medic_id);
            ps.execute();
            Conecxao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover os dados!", ex);
        }
    }

    public List<Medicacao> buscarPresc() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement pst = conexao.prepareStatement("Select * from medic_prescricao");
            ResultSet rs = pst.executeQuery();
            List<Medicacao> medicacoes = new ArrayList();

            while (rs.next()) {
                Medicacao ps = new Medicacao();
                ps.setIdMedicacao(rs.getInt("idMedicPresc"));
                ps.setMedicacao(rs.getString("prescricao"));
                ps.setCodigoPresc(rs.getString("codigo"));

                medicacoes.add(ps);
            }
            return medicacoes;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os dados da prescrição!", ex);
        }
    }

    public List<Medicacao> buscarPos() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement pst = conexao.prepareStatement("Select * from medic_posologia");
            ResultSet rs = pst.executeQuery();
            List<Medicacao> medicacoes = new ArrayList();

            while (rs.next()) {
                Medicacao ps = new Medicacao();
                ps.setIdPosologia(rs.getInt("idMedicPos"));
                ps.setPosologia(rs.getString("posologia"));
                ps.setCodigoPos(rs.getString("codigo"));

                medicacoes.add(ps);
            }
            return medicacoes;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os dados da posologia!", ex);
        }
    }

    public List<String> buscarPrescNome() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT prescricao FROM medic_prescricao");
            ResultSet rs = ps.executeQuery();
            List<String> prescricao = new ArrayList<>();

            while (rs.next()) {
                prescricao.add(rs.getString("prescricao"));
            }

            return prescricao;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os dados de prescrições!", ex);
        }
    }

    public List<String> buscarPosNome() throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("SELECT posologia FROM medic_posologia");
            ResultSet rs = ps.executeQuery();
            List<String> prescricao = new ArrayList<>();

            while (rs.next()) {
                prescricao.add(rs.getString("posologia"));
            }

            return prescricao;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar os dados da posologia!", ex);
        }
    }

}
