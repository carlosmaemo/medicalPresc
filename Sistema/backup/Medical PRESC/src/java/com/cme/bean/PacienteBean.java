package com.cme.bean;

import com.cme.dao.DefinicaoDAO;
import com.cme.model.Paciente;
import com.cme.dao.PacienteDAO;
import com.cme.dao.PrescricaoDAO;
import com.cme.exception.ErroSistema;
import com.cme.model.Prescricao;
import com.cme.report.Relatorio;
import java.io.IOException;
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
public class PacienteBean {
private int countReload = 0;
    private Paciente paciente = new Paciente();
    private Relatorio relatorio = new Relatorio();
    private boolean editar = false;

    private final DefinicaoDAO definicaoDao = new DefinicaoDAO();

    private List<Paciente> pacientes = new ArrayList<>();
    private final PacienteDAO pacienteDao = new PacienteDAO();
    
    @PostConstruct
    public void init() {
        try {
            listar();
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PacienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adicionar() throws ClassNotFoundException, SQLException, ErroSistema {

        if (paciente.getPac().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'PAC' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getNome().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nome' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getApelido().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Apelido' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getIdade().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Idade' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getContacto().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Contacto' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getNrDoc().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nrº de documento' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (paciente.getMorada().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Morada' é obrigatório.", FacesMessage.SEVERITY_WARN);
        }
        else {
            if(pacienteDao.verificar(paciente) == true && editar == false) {
                     addMensagem("Paciente existente!", "O paciente já encontra-se registrado.", FacesMessage.SEVERITY_WARN);
                     editar = false;
            }
            else if(pacienteDao.verificar(paciente) == true && editar == true) {
                pacienteDao.actualizar(paciente);
                paciente.setIdPaciente(null);
                paciente.setPac("");
                paciente.setNome("");
                paciente.setApelido("");
                paciente.setIdade("");
                paciente.setSexo("Masculino");
                paciente.setGestante("Não");
                paciente.setMorada("");
                paciente.setContacto("");
                paciente.setPeso("");
                paciente.setTipoDoc("BI");
                paciente.setNrDoc("");

                listar();
                addMensagem("Actualizado!", "Dados actualizado com sucesso.", FacesMessage.SEVERITY_INFO);
            }
            else {
            try {
                pacienteDao.salvar(paciente);
                paciente.setIdPaciente(null);
                paciente.setPac("");
                paciente.setNome("");
                paciente.setApelido("");
                paciente.setIdade("");
                paciente.setSexo("Masculino");
                paciente.setGestante("Não");
                paciente.setMorada("");
                paciente.setContacto("");
                paciente.setPeso("");
                paciente.setTipoDoc("BI");
                paciente.setNrDoc("");
                
                listar();

                addMensagem("Registrado!", "Paciente registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
            }
        }
    }

    public void listar() throws ClassNotFoundException, SQLException {
        
                editar = false;
                
        try {
            pacientes = pacienteDao.buscar();
            if (pacientes == null || pacientes.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de pacientes.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Paciente c) throws ClassNotFoundException, SQLException {
        try {
            pacienteDao.deletar(c.getIdPaciente());
            listar();
            addMensagem("Removido!", "Paciente removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
        
    public void editar(Paciente c) {
        paciente = c;
        editar = true;
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

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

}
