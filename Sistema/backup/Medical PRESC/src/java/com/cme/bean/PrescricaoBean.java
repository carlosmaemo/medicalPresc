package com.cme.bean;

import com.cme.dao.MedicamentoDAO;
import com.cme.model.Prescricao;
import com.cme.dao.PrescricaoDAO;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
import com.cme.model.Usuario;
import com.cme.report.Relatorio;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import net.sf.jasperreports.engine.JRException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos Maemo
 */
@ManagedBean
@SessionScoped
public class PrescricaoBean {

    private final PrescricaoDAO prescricaoDAO = new PrescricaoDAO();
    private Prescricao prescricao = new Prescricao();
    private List<String> pacs = new ArrayList<>();
    private List<Prescricao> prescricoes = new ArrayList<>();
    private Medicamento medicamento = new Medicamento();
    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    private List<Medicamento> medicamentos = new ArrayList<>();

    @PostConstruct
    public void init() {

        pesquisarPacs();

    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public String logOff() {
        FacesContext fc = FacesContext.getCurrentInstance();

        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.invalidate();
        return "index.jsf?faces-redirect=true";
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }

    public void confirmar(String pac, int idUsuario) {
        try {
            prescricao = prescricaoDAO.confirmar(pac, idUsuario);

        } catch (ErroSistema ex) {
            Logger.getLogger(PrescricaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }

    public void setPrescricoes(List<Prescricao> prescricoes) {
        this.prescricoes = prescricoes;
    }

    public void pesquisarPacs() {
        try {
            pacs = prescricaoDAO.buscarPac();
        } catch (ErroSistema ex) {
            Logger.getLogger(PrescricaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionar(Prescricao presc1, Medicamento medic) throws ClassNotFoundException, SQLException {
        
        if (medic.getMedicament().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Prescrição' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medic.getPosologia().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Posologia' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medic.getQuantidade().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            try {

                int prescId = medicamentoDAO.salvar(presc1, medic);
                
                medic.setIdMedicamento(null);
                medic.setMedicament("");
                medic.setPosologia("");
                medic.setQuantidade("");

                listar(prescId);

            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void listar(int idA) throws ClassNotFoundException, SQLException {

        try {
            medicamentos = medicamentoDAO.buscar(idA);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
        }
    }

    public void novoRegistoX() {
        prescricao = new Prescricao();
        prescricao.setNovoRegisto(true);
        prescricao.setActualizarReg(false);
    }

    public void cancelarRegistoX() {
        prescricao = new Prescricao();
        prescricao.setNovoRegisto(false);
        prescricao.setActualizarReg(false);
    }

    public List<String> getPacs() {
        pesquisarPacs();

        return pacs;
    }

    public void setPacs(List<String> pacs) {
        this.pacs = pacs;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

}
