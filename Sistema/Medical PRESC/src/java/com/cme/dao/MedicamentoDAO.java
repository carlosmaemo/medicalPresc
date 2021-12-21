package com.cme.dao;

import com.cme.essencial.Extenso;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
import com.cme.model.Paciente;
import com.cme.model.Prescricao;
import com.cme.model.Relatorio;
import com.cme.util.Conecxao;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos Maemo
 */
public class MedicamentoDAO implements Serializable {

    public void salvar(Prescricao prescricao, ArrayList<Medicamento> medicamentos) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();

            String comma = "";
            StringBuilder todosMedicamentos = new StringBuilder();
            for (Medicamento m : medicamentos) {
                todosMedicamentos.append(comma);
                todosMedicamentos.append(m);
                comma = ", ";
            }

            int generatedkey = 0;

            PreparedStatement ps1 = conexao.prepareStatement("INSERT INTO `prescricao`(`idPaciente`, `data`, `dataInt`, `dataPresc`, `clinico`, `clinicoMorada`, `clinicoContacto`, `pac`, `nome`, `apelido`, `sexo`, `idade`, `gestante`, `morada`, `contacto`, `peso`, `nrDoc`, `tipoDoc`, `estado`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps2 = conexao.prepareStatement("INSERT INTO `medicamento`(`idPrescricao`, `medicamento`, `posologia`, `quantidade`, `presc`) VALUES (?, ?, ?, ?, ?)");

            ps1.setInt(1, prescricao.getIdPaciente());
            ps1.setString(2, prescricao.getData());
            ps1.setString(3, prescricao.getDataInt());
            ps1.setString(4, prescricao.getData());
            ps1.setString(5, prescricao.getClinico());
            ps1.setString(6, prescricao.getClinicoMorada());
            ps1.setString(7, prescricao.getClinicoContacto());
            ps1.setString(8, prescricao.getPac());
            ps1.setString(9, prescricao.getNome());
            ps1.setString(10, prescricao.getApelido());
            ps1.setString(11, prescricao.getSexo());
            ps1.setString(12, prescricao.getIdade());
            ps1.setString(13, prescricao.getGestante());
            ps1.setString(14, prescricao.getMorada());
            ps1.setString(15, prescricao.getContacto());
            ps1.setString(16, prescricao.getPeso());
            ps1.setString(17, prescricao.getNrDoc());
            ps1.setString(18, prescricao.getTipoDoc());
            ps1.setString(19, "falso");
            ps1.execute();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                generatedkey = rs.getInt(1);
            }

            for (Medicamento m : medicamentos) {

                ps2.setInt(1, generatedkey);
                ps2.setString(2, m.getMedicament());
                ps2.setString(3, m.getPosologia());
                ps2.setString(4, m.getQuantidadeX() + " (" + new Extenso(Integer.parseInt(m.getQuantidadeX())).toString() + ")");
                ps2.setString(5, m.getPresc());

                ps2.execute();
            }

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar prescricao!", ex);
        }
    }

    public void deletar(int idMedicamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from medicamento where idMedicamento=?");
            ps.setInt(1, idMedicamento);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover o medicamento!", ex);
        }
    }

    public List<Medicamento> buscar(int idPrescricao) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from medicamento where idPrescricao = '"+ idPrescricao +"'");
            
            ResultSet rs = ps.executeQuery();

            List<Medicamento> medicamentos = new ArrayList();

            while (rs.next()) {

                Medicamento medicamento = new Medicamento(null, null, null, null);

                medicamento.setIdMedicamento(rs.getInt("idMedicamento"));
                medicamento.setIdPrescricao(rs.getInt("idPrescricao"));
                medicamento.setMedicament(rs.getString("medicamento"));
                medicamento.setPosologia(rs.getString("posologia"));
                medicamento.setQuantidade(rs.getString("quantidade"));
                medicamento.setQuantAviado(rs.getString("aviado"));
                medicamento.setPresc(rs.getString("presc"));

                medicamentos.add(medicamento);

            }

            return medicamentos;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar relatório!", ex);
        }

    }
}
