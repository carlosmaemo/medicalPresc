package com.cme.bean;

import com.cme.dao.MedicacaoDAO;
import com.cme.dao.MedicamentoDAO;
import com.cme.model.Prescricao;
import com.cme.dao.PrescricaoDAO;
import com.cme.dao.RelatorioDAO;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
import com.cme.model.Paciente;
import com.cme.model.Relatorio;
import com.cme.model.Usuario;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Carlos Maemo
 */
@ManagedBean
@SessionScoped
public class RelatorioBean implements Serializable {

    private Relatorio relatorio = new Relatorio();
    private List<Relatorio> relatorios = new ArrayList<>();
    private List<Medicamento> medicamentos = new ArrayList<>();
    private List<String> pacs = new ArrayList<>();
    private Prescricao prescricao = new Prescricao();
    private static int idPresc;

    private final RelatorioDAO relatorioDao = new RelatorioDAO();
    private final PrescricaoDAO prescricaoDAO = new PrescricaoDAO();
    private final MedicamentoDAO medicamentoDao = new MedicamentoDAO();

    @PostConstruct
    public void init() {
        try {
            pesquisarPacs();
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RelatorioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Relatorio getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (relatorio != null) {

            if (!"".equals(relatorio.getPacFiltro()) || !"".equals(relatorio.getDataInicial()) || !"".equals(relatorio.getDataFinal())) {

                relatorio.setPacFiltro("");
                relatorio.setDataInicial("");
                relatorio.setDataFinal("");

                listar();

            } else {

                relatorio.setPacFiltro("");
                relatorio.setDataInicial("");
                relatorio.setDataFinal("");

                listar();
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {
        try {
            relatorios = relatorioDao.buscar();
            if (relatorios == null || relatorios.isEmpty()) {
                addMensagem("Nenhum registo encontrado!", "Não foi encontrado nehnum registo de prescrição.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listarMedicamentos(int idPrescricao) throws ClassNotFoundException, SQLException {
        try {
            medicamentos = medicamentoDao.buscar(idPrescricao);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Relatorio c) throws ClassNotFoundException, SQLException {
        try {
            relatorioDao.deletar(c.getIdPrescricao());
            listar();
            addMensagem("Removido!", "Prescrição removida com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void cancelarX() throws ClassNotFoundException, SQLException {

        prescricao.setNome("");
        prescricao.setApelido("");
        prescricao.setSexo("");
        prescricao.setContacto("");
        prescricao.setMorada("");
        relatorio.setPacConf("");
        idPresc = 0;

        listarMedicamentos(0);

    }

    public void printRelat(Relatorio re, Integer idUser) throws ClassNotFoundException, SQLException, ErroSistema, IOException {
        
        relatorioDao.printRelat(re, idUser);
    }

    public void print() throws IOException, ErroSistema, JRException, ClassNotFoundException, SQLException {

        if (idPresc != 0) {

            relatorioDao.print(idPresc);

            prescricao.setNome("");
            prescricao.setApelido("");
            prescricao.setSexo("");
            prescricao.setContacto("");
            prescricao.setMorada("");
            relatorio.setPacConf("");
            idPresc = 0;

            listarMedicamentos(0);
            listar();

        } else {

            prescricao.setNome("");
            prescricao.setApelido("");
            prescricao.setSexo("");
            prescricao.setContacto("");
            prescricao.setMorada("");
            relatorio.setPacConf("");
            idPresc = 0;

            listarMedicamentos(0);
            listar();
        }

    }

    public void pesquisar() throws ClassNotFoundException, SQLException {
        try {

            if (relatorio.getPacFiltro().isEmpty() == false
                    && relatorio.getDataInicial().isEmpty() == true
                    && relatorio.getDataFinal().isEmpty() == true) {

                relatorios = relatorioDao.buscarFiltro(relatorio, "pac");

            } else if (relatorio.getPacFiltro().isEmpty() == false
                    && relatorio.getDataInicial().isEmpty() == false
                    && relatorio.getDataFinal().isEmpty() == true) {

                relatorios = relatorioDao.buscarFiltro(relatorio, "pac");

            } else if (relatorio.getPacFiltro().isEmpty() == false
                    && relatorio.getDataInicial().isEmpty() == true
                    && relatorio.getDataFinal().isEmpty() == false) {

                relatorios = relatorioDao.buscarFiltro(relatorio, "pac");

            } else if (relatorio.getPacFiltro().isEmpty() == true
                    && relatorio.getDataInicial().isEmpty() == false
                    && relatorio.getDataFinal().isEmpty() == false) {

                relatorios = relatorioDao.buscarFiltro(relatorio, "data");

            } else if (relatorio.getPacFiltro().isEmpty() == false
                    && relatorio.getDataInicial().isEmpty() == false
                    && relatorio.getDataFinal().isEmpty() == false) {

                relatorios = relatorioDao.buscarFiltro(relatorio, "pac_data");

            } else if (relatorio.getPacFiltro().isEmpty() == true
                    && relatorio.getDataInicial().isEmpty() == true
                    && relatorio.getDataFinal().isEmpty() == true) {

                relatorios = relatorioDao.buscarFiltro(relatorio, "vazio");

            } else {

                relatorios = relatorioDao.buscarFiltro(relatorio, "vazio");
            }

            if (relatorios == null || relatorios.isEmpty()) {
                addMensagem("Nenhum registo encontrado!", "Não foi encontrado nehnum registo de prescrição.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public void pesquisarPacs() {
        try {
            pacs = relatorioDao.buscarPac();
        } catch (ErroSistema ex) {
            Logger.getLogger(PrescricaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void aviar(Medicamento medic) throws ClassNotFoundException, SQLException {
        try {
            relatorioDao.aviarMedicamento(medic);
            listarMedicamentos(prescricao.getIdPrescricao());
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void remover(Medicamento medic) throws ClassNotFoundException, SQLException {
        try {
            relatorioDao.removerMedicamento(medic);
            listarMedicamentos(prescricao.getIdPrescricao());
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void confirmar(String pac, int idUsuario) throws ClassNotFoundException, SQLException {
        try {
            prescricao = relatorioDao.confirmar(pac, idUsuario);
            idPresc = prescricao.getIdPrescricao();
            listarMedicamentos(prescricao.getIdPrescricao());
        } catch (ErroSistema ex) {
            Logger.getLogger(PrescricaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Relatorio> getRelatorios() throws SQLException, ClassNotFoundException {
        return relatorios;
    }

    public void setRelatorios(List<Relatorio> relatorios) {
        this.relatorios = relatorios;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<String> getPacs() {
        pesquisarPacs();
        return pacs;
    }

    public void setPacs(List<String> pacs) {
        this.pacs = pacs;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

}
