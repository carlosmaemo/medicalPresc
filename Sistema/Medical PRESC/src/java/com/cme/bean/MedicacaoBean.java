package com.cme.bean;

import com.cme.dao.DefinicaoDAO;
import com.cme.dao.MedicacaoDAO;
import com.cme.model.Paciente;
import com.cme.dao.PacienteDAO;
import com.cme.dao.PrescricaoDAO;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicacao;
import com.cme.model.Prescricao;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class MedicacaoBean implements Serializable {

    private int countReload = 0;
    private Medicacao medic = new Medicacao();

    private boolean editarPresc = false;
    private boolean modo_edicaoPresc = false;

    private boolean editarPos = false;
    private boolean modo_edicaoPos = false;

    private List<Medicacao> medicacoesPresc = new ArrayList<>();
    private List<Medicacao> medicacoesPos = new ArrayList<>();
    private final MedicacaoDAO medicacaoDao = new MedicacaoDAO();

    @PostConstruct
    public void init() {
        try {
            listarPresc();
            listarPos();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MedicacaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionarPresc() throws ClassNotFoundException, SQLException, ErroSistema {
        if (medic.getMedicacao().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Prescrição' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medic.getCodigoPresc().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicacaoDao.verificarPresc(medic) == true && editarPresc == false) {
            addMensagem("Código existente!", "O código já encontra-se registrado.", FacesMessage.SEVERITY_WARN);
            editarPresc = false;
            modo_edicaoPresc = false;
        } else if (medicacaoDao.verificarPresc(medic) == true && editarPresc == true) {
            editarPresc = false;
            modo_edicaoPresc = false;
            medicacaoDao.actualizarPresc(medic);
            medic.setIdMedicacao(null);
            medic.setMedicacao("");
            medic.setCodigoPresc("");
            listarPresc();
            addMensagem("Actualizado!", "Dados actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {
                editarPresc = false;
                modo_edicaoPresc = false;
                medicacaoDao.salvarPresc(medic);
                medic.setIdMedicacao(null);
                medic.setMedicacao("");
                medic.setCodigoPresc("");
                listarPresc();
                addMensagem("Registrado!", "Prescrição registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void adicionarPos() throws ClassNotFoundException, SQLException, ErroSistema {
        if (medic.getPosologia().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Posologia' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medic.getCodigoPos().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Código' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (medicacaoDao.verificarPos(medic) == true && editarPos == false) {
            addMensagem("Código existente!", "O código já encontra-se registrado.", FacesMessage.SEVERITY_WARN);
            editarPos = false;
            modo_edicaoPos = false;
        } else if (medicacaoDao.verificarPos(medic) == true && editarPos == true) {
            editarPos = false;
            modo_edicaoPos = false;
            medicacaoDao.actualizarPos(medic);
            medic.setIdPosologia(null);
            medic.setPosologia("");
            medic.setCodigoPos("");
            listarPos();
            addMensagem("Actualizado!", "Dados actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
        } else {
            try {
                editarPos = false;
                modo_edicaoPos = false;
                medicacaoDao.salvarPos(medic);
                medic.setIdPosologia(null);
                medic.setPosologia("");
                medic.setCodigoPos("");
                listarPos();
                addMensagem("Registrado!", "Posologia registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelarPresc() throws ClassNotFoundException, SQLException {
        if (medic != null) {
            if (!"".equals(medic.getCodigoPresc()) || !"".equals(medic.getMedicacao()) || modo_edicaoPresc == true) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);
                editarPresc = false;
                modo_edicaoPresc = false;
                medic.setIdMedicacao(null);
                medic.setMedicacao("");
                medic.setCodigoPresc("");
                listarPresc();
            } else {
                editarPresc = false;
                modo_edicaoPresc = false;
                medic.setIdMedicacao(null);
                medic.setMedicacao("");
                medic.setCodigoPresc("");
                listarPresc();
            }
        }
    }

    public void cancelarPos() throws ClassNotFoundException, SQLException {
        if (medic != null) {
            if (!"".equals(medic.getCodigoPos()) || !"".equals(medic.getPosologia()) || modo_edicaoPos == true) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);
                editarPos = false;
                modo_edicaoPos = false;
                medic.setIdPosologia(null);
                medic.setPosologia("");
                medic.setCodigoPos("");
                listarPos();
            } else {
                editarPos = false;
                modo_edicaoPos = false;
                medic.setIdPosologia(null);
                medic.setPosologia("");
                medic.setCodigoPos("");
                listarPos();
            }
        }
    }
    
    public void listarPresc() throws ClassNotFoundException, SQLException {
        editarPresc = false;
        try {
            medicacoesPresc = medicacaoDao.buscarPresc();

        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void listarPos() throws ClassNotFoundException, SQLException {
        editarPos = false;
        try {
            medicacoesPos = medicacaoDao.buscarPos();
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletarPresc(Medicacao c) throws ClassNotFoundException, SQLException {
        try {
            medicacaoDao.deletarPresc(c.getIdMedicacao());
            listarPresc();
            addMensagem("Removido!", "Dados removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletarPos(Medicacao c) throws ClassNotFoundException, SQLException {
        try {
            medicacaoDao.deletarPos(c.getIdPosologia());
            listarPos();
            addMensagem("Removido!", "Dados removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editarPresc(Medicacao c) {
        medic = c;
        editarPresc = true;
        modo_edicaoPresc = true;
    }

    public void editarPos(Medicacao c) {
        medic = c;
        editarPos = true;
        modo_edicaoPos = true;
    }

    public void reload() {
        try {
            countReload++;
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = request.getSession(false);
            session.invalidate();
            FacesContext temp = FacesContext.getCurrentInstance();
            temp.getExternalContext().redirect("index.jsf");
        } catch (IOException ex) {
        }
    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public Medicacao getMedic() {
        return medic;
    }

    public void setMedic(Medicacao medic) {
        this.medic = medic;
    }

    public boolean isEditarPresc() {
        return editarPresc;
    }

    public void setEditarPresc(boolean editarPresc) {
        this.editarPresc = editarPresc;
    }

    public boolean isModo_edicaoPresc() {
        return modo_edicaoPresc;
    }

    public void setModo_edicaoPresc(boolean modo_edicaoPresc) {
        this.modo_edicaoPresc = modo_edicaoPresc;
    }

    public boolean isEditarPos() {
        return editarPos;
    }

    public void setEditarPos(boolean editarPos) {
        this.editarPos = editarPos;
    }

    public boolean isModo_edicaoPos() {
        return modo_edicaoPos;
    }

    public void setModo_edicaoPos(boolean modo_edicaoPos) {
        this.modo_edicaoPos = modo_edicaoPos;
    }

    public List<Medicacao> getMedicacoesPresc() {
        return medicacoesPresc;
    }

    public void setMedicacoesPresc(List<Medicacao> medicacoesPresc) {
        this.medicacoesPresc = medicacoesPresc;
    }

    public List<Medicacao> getMedicacoesPos() {
        return medicacoesPos;
    }

    public void setMedicacoesPos(List<Medicacao> medicacoesPos) {
        this.medicacoesPos = medicacoesPos;
    }

}
