package com.cme.dao;

import com.cme.essencial.Extenso;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
import com.cme.model.Paciente;
import com.cme.model.Prescricao;
import com.cme.model.Relatorio;
import com.cme.model.Usuario;
import com.cme.util.Conecxao;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Carlos Maemo
 */
public class RelatorioDAO implements Serializable {

    JasperPrint imprimirJ;
    private final HttpServletResponse response;
    private final FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream steam;

    public RelatorioDAO() {

        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }

    public List<Relatorio> buscar() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("Select * from prescricao where estado = 'verdade';");

            ResultSet rs = ps.executeQuery();

            List<Relatorio> prescricoes = new ArrayList();

            while (rs.next()) {

                Relatorio prescricao = new Relatorio();

                prescricao.setIdPrescricao(rs.getInt("idPrescricao"));
                prescricao.setIdPaciente(rs.getInt("idPaciente"));
                prescricao.setData(rs.getString("data"));
                prescricao.setClinico(rs.getString("clinico"));
                prescricao.setClinicoMorada(rs.getString("clinicoMorada"));
                prescricao.setClinicoContacto(rs.getString("clinicoContacto"));
                prescricao.setPac(rs.getString("pac"));
                prescricao.setNome(rs.getString("nome"));
                prescricao.setApelido(rs.getString("apelido"));
                prescricao.setSexo(rs.getString("sexo"));
                prescricao.setIdade(rs.getString("idade"));
                prescricao.setGestante(rs.getString("gestante"));
                prescricao.setMorada(rs.getString("morada"));
                prescricao.setContacto(rs.getString("contacto"));
                prescricao.setPeso(rs.getString("peso"));
                prescricao.setNrDoc(rs.getString("nrDoc"));
                prescricao.setTipoDoc(rs.getString("tipoDoc"));

                prescricoes.add(prescricao);

            }

            return prescricoes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar relatório!", ex);
        }

    }

    public List<Relatorio> buscarFiltro(Relatorio relat, String tipo) throws ErroSistema {
        try {

            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps = null;

            if ("pac".equals(tipo)) {

                ps = conexao.prepareStatement("Select * from prescricao where pac = ?");
                ps.setString(1, relat.getPacFiltro());

            } else if ("data".equals(tipo)) {

                ps = conexao.prepareStatement("Select * from prescricao where dataInt >= ? and dataInt <= ?");
                ps.setString(1, relat.getDataInicial());
                ps.setString(2, relat.getDataFinal());

            } else if ("pac_data".equals(tipo)) {

                ps = conexao.prepareStatement("Select * from prescricao where (dataInt >= ? and dataInt <= ?) and pac = ?");
                ps.setString(1, relat.getDataInicial());
                ps.setString(2, relat.getDataFinal());
                ps.setString(3, relat.getPacFiltro());

            } else if ("vazio".equals(tipo)) {

                List<Relatorio> relatorios = new ArrayList();
                return relatorios;

            }

            ResultSet rs = ps.executeQuery();
            List<Relatorio> prescricoes = new ArrayList();

            while (rs.next()) {
                Relatorio prescricao = new Relatorio();

                prescricao.setIdPrescricao(rs.getInt("idPrescricao"));
                prescricao.setIdPaciente(rs.getInt("idPaciente"));
                prescricao.setData(rs.getString("data"));
                prescricao.setClinico(rs.getString("clinico"));
                prescricao.setClinicoMorada(rs.getString("clinicoMorada"));
                prescricao.setClinicoContacto(rs.getString("clinicoContacto"));
                prescricao.setPac(rs.getString("pac"));
                prescricao.setNome(rs.getString("nome"));
                prescricao.setApelido(rs.getString("apelido"));
                prescricao.setSexo(rs.getString("sexo"));
                prescricao.setIdade(rs.getString("idade"));
                prescricao.setGestante(rs.getString("gestante"));
                prescricao.setMorada(rs.getString("morada"));
                prescricao.setContacto(rs.getString("contacto"));
                prescricao.setPeso(rs.getString("peso"));
                prescricao.setNrDoc(rs.getString("nrDoc"));
                prescricao.setTipoDoc(rs.getString("tipoDoc"));

                prescricoes.add(prescricao);
            }
            return prescricoes;

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao pesquisar relatório!", ex);
        }
    }

    public void deletar(int idPrescricao) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("delete from prescricao where idPrescricao=?");
            ps.setInt(1, idPrescricao);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover a prescrição!", ex);
        }
    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public void aviarMedicamento(Medicamento medic) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update medicamento set presc=?, aviado=? where idMedicamento=?");
            ps.setString(1, "verdade");

            String[] partes = medic.getQuantidadeX().split(" \\(");
            String quantidade = partes[0];

            if (Integer.parseInt(medic.getQuantAviado()) < Integer.parseInt(quantidade)) {
                int quantAviado = Integer.parseInt(quantidade) - Integer.parseInt(medic.getQuantAviado());
                ps.setString(2, medic.getQuantAviado() + " (" + new Extenso(Integer.parseInt(medic.getQuantAviado())).toString() + "), Falta " + quantAviado + " (" + new Extenso(quantAviado).toString() + ")");
            } else if (Integer.parseInt(medic.getQuantAviado()) >= Integer.parseInt(quantidade)) {
                ps.setString(2, medic.getQuantAviado() + " (" + new Extenso(Integer.parseInt(medic.getQuantAviado())).toString() + ")");
            }

            ps.setInt(3, medic.getIdMedicamento());
            ps.execute();

            medic.setPresc("verdade");

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao aviar medicamento!", ex);
        }
    }

    public void removerMedicamento(Medicamento medic) throws ErroSistema {
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update medicamento set presc=?, aviado=? where idMedicamento=?");
            ps.setString(1, "falso");
            ps.setString(2, "");
            ps.setInt(3, medic.getIdMedicamento());
            ps.execute();

            medic.setPresc("falso");

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao remover medicamento!", ex);
        }
    }

    public void print(int idPrescricao) throws ErroSistema, IOException {

        String data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        
        try {
            Connection conexao = Conecxao.getConexao();
            PreparedStatement ps;

            ps = conexao.prepareStatement("update prescricao set estado=?, dataPresc=? where idPrescricao=?");
            ps.setString(1, "verdade");
            ps.setString(2, data);
            ps.setInt(3, idPrescricao);
            ps.execute();

            Conecxao.fecharConexao();

        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao gravar prescrição!", ex);
        }
    }

    public void printRelat(Relatorio relat, Integer idUsuario) throws ErroSistema, IOException {

        print(relat.getIdPrescricao());
        
        Map<String, Object> params = new HashMap<>();
        params.put("idPrescricao", relat.getIdPrescricao());
        params.put("idUsuario", idUsuario);

        try {

            Connection conexao = Conecxao.getConexao();

            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            ServletOutputStream responseStream = response.getOutputStream();

            InputStream caminho = getClass().getResourceAsStream("../report/prescricao.jrxml");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + relat.getNome() + " " + relat.getApelido() + " - " + relat.getData() + ".pdf\"");

            JasperReport pathReport = JasperCompileManager.compileReport(caminho);
            JasperPrint preencher = JasperFillManager.fillReport(pathReport, params, conexao);
            JasperExportManager.exportReportToPdfStream(preencher, responseStream);

            responseStream.flush();
            responseStream.close();
            context.renderResponse();
            context.responseComplete();
            
            Conecxao.fecharConexao();
            
        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<String> buscarPac() throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement ps = conexao.prepareStatement("SELECT pac FROM prescricao where estado = 'falso';");

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

    public Prescricao confirmar(String c, int idUsuario) throws ErroSistema {

        try {
            Connection conexao = Conecxao.getConexao();

            PreparedStatement pst = conexao.prepareStatement("Select * from paciente where pac=?");
            PreparedStatement pst2 = conexao.prepareStatement("Select * from usuario where idUsuario=?");
            PreparedStatement pst3 = conexao.prepareStatement("Select * from prescricao where pac=?");

            pst.setString(1, c);
            pst.execute();

            pst2.setInt(1, idUsuario);
            pst2.execute();

            pst3.setString(1, c);
            pst3.execute();

            ResultSet rs = pst.executeQuery();
            ResultSet rs2 = pst2.executeQuery();
            ResultSet rs3 = pst3.executeQuery();

            Prescricao ps = new Prescricao();

            while (rs2.next()) {

                ps.setClinico(rs2.getString("nomeUsuario"));
                ps.setClinicoMorada(rs2.getString("moradaUsuario"));
                ps.setClinicoContacto(rs2.getString("contactoUsuario"));

            }

            while (rs3.next()) {

                ps.setIdPrescricao(rs3.getInt("idPrescricao"));

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

}
