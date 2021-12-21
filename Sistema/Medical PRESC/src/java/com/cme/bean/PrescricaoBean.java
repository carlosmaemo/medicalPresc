package com.cme.bean;

import com.cme.dao.MedicacaoDAO;
import com.cme.dao.MedicamentoDAO;
import com.cme.model.Prescricao;
import com.cme.dao.PrescricaoDAO;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
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
import net.sf.jasperreports.engine.JRException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Carlos Maemo
 */
@ManagedBean
@SessionScoped
public class PrescricaoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idPrescricao;
    private String medicament, posologia, quantidade;

    private static final ArrayList<Medicamento> medicamentos
            = new ArrayList<Medicamento>();

    private final PrescricaoDAO prescricaoDAO = new PrescricaoDAO();
    private final MedicacaoDAO medicacaoDAO = new MedicacaoDAO();
    private Prescricao prescricao = new Prescricao();
    private List<String> pacs = new ArrayList<>();
    private List<String> presc = new ArrayList<>();
    private List<String> pos = new ArrayList<>();
    private List<Prescricao> prescricoes = new ArrayList<>();
    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();

    @PostConstruct
    public void init() {
        pesquisarPacs();
        pesquisarPresc();
        pesquisarPos();
    }

    public ArrayList<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public String adicionarMedicamento() {
        if (medicament.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Prescrição' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (posologia.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Posologia' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (quantidade.isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Quantidade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {

            Medicamento medicamento = new Medicamento(medicament, posologia, quantidade, "falso");
            medicamentos.add(medicamento);

            quantidade = "";
        }

        return null;
    }

    public void salvar(Prescricao prescricao) throws ErroSistema {

        if (prescricao.getNome() == null || prescricao == null || prescricao.getPac() == null) {
            addMensagem("Falha ao gravar!", "Paciente não identificado.", FacesMessage.SEVERITY_WARN);
        } else if (medicamentos.isEmpty()) {
            addMensagem("Falha ao gravar!", "Medicação não especificada.", FacesMessage.SEVERITY_WARN);
        } else {
            medicamentoDAO.salvar(prescricao, medicamentos);
            medicamentos.clear();
            prescricao.setPac("");
            prescricao.setNome("");
            prescricao.setApelido("");
            prescricao.setSexo("");
            prescricao.setContacto("");
            prescricao.setMorada("");

            addMensagem("Gravado!", "Operação gravada com sucesso.", FacesMessage.SEVERITY_INFO);
        }
    }

    public void cancelar(Prescricao prescricao) {
        if (prescricao == null) {

            medicamentos.clear();
            addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);
        } else if (prescricao.getNome() != null) {
            prescricao.setPac(null);
            prescricao.setNome(null);
            prescricao.setApelido(null);
            prescricao.setSexo(null);
            prescricao.setContacto(null);
            prescricao.setMorada(null);

            medicamentos.clear();
            addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            prescricao.setPac(null);
            prescricao.setNome(null);
            prescricao.setApelido(null);
            prescricao.setSexo(null);
            prescricao.setContacto(null);
            prescricao.setMorada(null);

            medicamentos.clear();
        }

    }

    public String removerMedicamento(Medicamento medicamento) {
        medicamentos.remove(medicamento);
        return null;
    }

    public String editarMedicamento(Medicamento medicamento) {
        medicamento.setCanEdit(true);
        return null;
    }

    public String salvarMedicamentos() {

        //set "canEdit" of all employees to false 
        for (Medicamento medicamento : medicamentos) {
            medicamento.setCanEdit(false);
        }
        return null;
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

    public void pesquisarPresc() {
        try {
            presc = medicacaoDAO.buscarPrescNome();
        } catch (ErroSistema ex) {
            Logger.getLogger(PrescricaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pesquisarPos() {
        try {
            pos = medicacaoDAO.buscarPosNome();
        } catch (ErroSistema ex) {
            Logger.getLogger(PrescricaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void novoRegistoX() {
        prescricao = new Prescricao();
        prescricao.setNovoRegisto(true);
    }

    public void cancelarRegistoX() {
        prescricao = new Prescricao();
        prescricao.setNovoRegisto(false);
    }

    public List<String> getPacs() {
        pesquisarPacs();
        return pacs;
    }

    public void setPacs(List<String> pacs) {
        this.pacs = pacs;
    }

    public List<String> getPresc() {
        pesquisarPresc();
        return presc;
    }

    public void setPresc(List<String> presc) {
        this.presc = presc;
    }

    public List<String> getPos() {
        pesquisarPos();
        return pos;
    }

    public void setPos(List<String> pos) {
        this.pos = pos;
    }

    public Integer getIdPrescricao() {
        return idPrescricao;
    }

    public void setIdPrescricao(Integer idPrescricao) {
        this.idPrescricao = idPrescricao;
    }

    public String getMedicament() {
        return medicament;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

}
