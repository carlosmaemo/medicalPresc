package com.cme.dao;

import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
import com.cme.model.Paciente;
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
public class MedicamentoDAO {

    public Integer salvar(Prescricao prescricao, Medicamento medicamento) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();
            
            int generatedkey = 0;
            
            PreparedStatement ps1 = conexao.prepareStatement("INSERT INTO `prescricao`(`idPaciente`, `data`, `clinico`, `clinicoMorada`, `clinicoContacto`, `pac`, `nome`, `apelido`, `idade`, `gestante`, `morada`, `contacto`, `peso`, `nrDoc`, `tipoDoc`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement ps2 = conexao.prepareStatement("INSERT INTO `medicamento`(`idPrescricao`, `medicamento`, `posologia`, `quantidade`) VALUES (?, ?, ?, ?)");

            ps1.setInt(1, prescricao.getIdPaciente());
            ps1.setString(2, prescricao.getData());
            ps1.setString(3, prescricao.getClinico());
            ps1.setString(4, prescricao.getClinicoMorada());
            ps1.setString(5, prescricao.getClinicoContacto());
            ps1.setString(6, prescricao.getPac());
            ps1.setString(7, prescricao.getNome());
            ps1.setString(8, prescricao.getApelido());
            ps1.setString(9, prescricao.getIdade());
            ps1.setString(10, prescricao.getGestante());
            ps1.setString(11, prescricao.getMorada());
            ps1.setString(12, prescricao.getContacto());
            ps1.setString(13, prescricao.getPeso());
            ps1.setString(14, prescricao.getNrDoc());
            ps1.setString(15, prescricao.getTipoDoc());
            ps1.execute();

            ResultSet rs = ps1.getGeneratedKeys();
            if (rs.next()) {
                generatedkey = rs.getInt(1);
            }

            ps2.setInt(1, generatedkey);
            ps2.setString(2, medicamento.getMedicament());
            ps2.setString(3, medicamento.getPosologia());
            ps2.setString(4, medicamento.getQuantidade());
            ps2.execute();
           
            Conecxao.fecharConexao();
            
            return generatedkey;
           
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao cadastrar prescricao!", ex);
        }

    }

    public List<Medicamento> buscar(int idPresc) throws ErroSistema {
        
        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from medicamento where idPrescricao=?");
            ps.setInt(1, idPresc);
            ps.execute();

            ResultSet rs = ps.executeQuery();

            List<Medicamento> medicamentos = new ArrayList();
            
            while (rs.next()) {

                Medicamento med = new Medicamento();

                med.setIdMedicamento(rs.getInt("idMedicamento"));
                med.setIdPrescricao(rs.getInt("idPrescricao"));
                med.setMedicament(rs.getString("medicamento"));
                med.setPosologia(rs.getString("posologia"));
                med.setQuantidade(rs.getString("quantidade"));
                
                medicamentos.add(med);

            }
            
            return medicamentos;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar o paciente!", ex);
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
}
