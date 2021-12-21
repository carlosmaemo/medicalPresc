package com.cme.bean;

import com.cme.dao.MedicamentoDAO;
import com.cme.model.Prescricao;
import com.cme.dao.PrescricaoDAO;
import com.cme.exception.ErroSistema;
import com.cme.model.Medicamento;
import com.cme.model.Paciente;
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
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 * @author Carlos Maemo
 */
@ManagedBean
@SessionScoped
public class MedicamentoBean implements Serializable {

    private final MedicamentoDAO medicamentoDAO = new MedicamentoDAO();
    //private Medicamento medicamento = new Medicamento();
    private Paciente paciente = new Paciente();
    private Prescricao prescricao = new Prescricao();
//
//    public Medicamento getMedicamento() {
//        return medicamento;
//    }
//
//    public void setMedicamento(Medicamento medicamento) {
//        this.medicamento = medicamento;
//    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Prescricao getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao) {
        this.prescricao = prescricao;
    }
   
    public void deletar(Medicamento med) throws ClassNotFoundException, SQLException {
        try {
            medicamentoDAO.deletar(med.getIdMedicamento());
            //listar();
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

}
