package com.cme.bean;

import com.cme.dao.DefinicaoDAO;
import com.cme.model.Usuario;
import com.cme.dao.UsuarioDAO;
import com.cme.exception.ErroSistema;
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
public class UsuarioBean implements Serializable {
private int countReload = 0;
    public Usuario usuario = new Usuario();

    private final DefinicaoDAO definicaoDao = new DefinicaoDAO();

    private List<Usuario> usuarios = new ArrayList<>();
    private final UsuarioDAO usuarioDao = new UsuarioDAO();

    @PostConstruct
    public void init() {
        try {
            listar();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logar() throws ClassNotFoundException, SQLException, IOException {

        try {

            if (usuarioDao.entrar(usuario) == false) {
                addMensagem("Dados incorrecto!", null, FacesMessage.SEVERITY_ERROR);
                return;
            }

            usuario.setConfNome(usuarioDao.verificarNomeCompleto(usuario));
            usuario.setConfNomeWenIsDelete(usuarioDao.verificarNomeCompleto(usuario));

            usuario.setConfNomeUsuario(usuarioDao.verificarNomeUsuario(usuario));
            usuario.setConfNomeUsuarioWenIsDelete(usuarioDao.verificarNomeUsuario(usuario));

            usuario.setConfPasswordUsuario(usuarioDao.verificarPasswordUsuario(usuario));
            usuario.setConfPasswordUsuarioWenIsDelete(usuarioDao.verificarPasswordUsuario(usuario));

            usuario.setConfSexo(usuarioDao.verificarSexo(usuario));
            usuario.setConfContacto(usuarioDao.verificarContacto(usuario));
            usuario.setConfMorada(usuarioDao.verificarMorada(usuario));
            
//            usuario.confEmailReceptor(usuarioDao.verificarEmailReceptor(usuario));
//            usuario.setConfEmailReceptorWenIsDelete(usuarioDao.verificarEmailReceptor(usuario));
//            
//            usuario.confEmailEmissor(usuarioDao.verificarEmailEmissor(usuario));
//            usuario.setConfEmailEmissorWenIsDelete(usuarioDao.verificarEmailEmissor(usuario));
//            
//            usuario.confPasswordEmailEmissor(usuarioDao.verificarPasswordEmailEmissor(usuario));
//            usuario.setConfEmailEmissorWenIsDelete(usuarioDao.verificarPasswordEmailEmissor(usuario));
            
            usuario.setConfAcesso(usuarioDao.verificarNivelAcesso(usuario));
            usuario.setConfIdUsuario(usuarioDao.verificarIdUsuario(usuario));

            usuario.setConfEmailEmissor(definicaoDao.verificarEmissor());
            usuario.setConfEmailEmissorWenIsDelete(definicaoDao.verificarEmissor());
            usuario.setConfPasswordEmailEmissor(definicaoDao.verificarEmissorPassword());
            usuario.setConfPasswordEmailEmissorWenIsDelete(definicaoDao.verificarEmissorPassword());
            usuario.setConfEmailReceptor(definicaoDao.verificarReceptor());
            usuario.setConfEmailReceptorWenIsDelete(definicaoDao.verificarReceptor());

            usuario.setIdUsuario(null);
            usuario.setNome("");
            usuario.setUsername("");
            usuario.setPassword("");
            usuario.setAcesso("");
            usuario.setContacto("");

            FacesContext.getCurrentInstance().getExternalContext().redirect("prescricao.jsf");

        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void adicionar() throws ClassNotFoundException, SQLException {

        if (usuario.getNome().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nome do Usuário' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (usuario.getUsername().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Usuário' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (usuario.getPassword().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Senha' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            try {
                usuarioDao.salvar(usuario);
                usuario.setIdUsuario(null);
                usuario.setNome("");
                usuario.setUsername("");
                usuario.setPassword("");
                usuario.setContacto("");
                usuario.setSexo("Masculino");
                usuario.setAcesso("Usuário");
                usuario.setMorada("");

                listar();

                addMensagem("Registrado!", "Usuário registrado com sucesso.", FacesMessage.SEVERITY_INFO);
            } catch (ErroSistema ex) {
                addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_FATAL);
            }
        }
    }

    public void cancelar() throws ClassNotFoundException, SQLException {

        if (usuario != null) {

            if (!"".equals(usuario.getNome()) || !"M".equals(usuario.getSexo()) || !"".equals(usuario.getContacto())
                    || !"".equals(usuario.getUsername()) || !"".equals(usuario.getPassword()) ||
                    !"Usuário".equals(usuario.getAcesso()) || !"".equals(usuario.getMorada())) {
                addMensagem("Cancelado!", "Operação cancelada com sucesso.", FacesMessage.SEVERITY_INFO);

                usuario.setIdUsuario(null);
                usuario.setNome("");
                usuario.setUsername("");
                usuario.setPassword("");
                usuario.setContacto("");
                usuario.setSexo("Masculino");
                usuario.setAcesso("Usuário");
                usuario.setMorada("");

                listar();

            } else {

                usuario.setIdUsuario(null);
                usuario.setNome("");
                usuario.setUsername("");
                usuario.setPassword("");
                usuario.setContacto("");
                usuario.setSexo("Masculino");
                usuario.setAcesso("Usuário");
                usuario.setMorada("");

                listar();
            }
        }
    }
    
    public void addDefinicoesUsuario(Integer idUsuario) throws ClassNotFoundException, SQLException {
        if (usuario.getConfNome().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Nome do Usuário' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else if (usuario.getConfPasswordUsuario().isEmpty()) {
            addMensagem("Campo vazio!", "O campo 'Senha' é obrigatório.", FacesMessage.SEVERITY_WARN);
        } else {
            try {

                usuarioDao.actualizar(usuario, idUsuario);

                addMensagem("Actualizado!", "Usuário actualizado com sucesso.", FacesMessage.SEVERITY_INFO);

            } catch (ErroSistema ex) {
                addMensagem("Falha!", ex.getCause().getMessage() + ".", FacesMessage.SEVERITY_WARN);
            }
        }

    }

    public void adicionarDefinicoes() throws ClassNotFoundException, SQLException, ErroSistema {

        if (usuario.getConfEmailReceptor().isEmpty()) {

            usuario.setConfEmailReceptor(usuario.getConfEmailReceptorWenIsDelete());

            addMensagem("Campo vazio!", "O campo 'Email Receptor' é obrigatório!", FacesMessage.SEVERITY_WARN);

        }
        else if (usuario.getConfEmailEmissor().isEmpty()) {

            usuario.setConfEmailEmissor(usuario.getConfEmailEmissorWenIsDelete());

            addMensagem("Campo vazio!", "O campo 'Email Emissor' é obrigatório!", FacesMessage.SEVERITY_WARN);

        } else if (usuario.getConfPasswordEmailEmissor().isEmpty()) {

            usuario.setConfPasswordEmailEmissor(usuario.getConfPasswordEmailEmissorWenIsDelete());

            addMensagem("Campo vazio!", "O campo 'Senha Email Emissor' é obrigatório!", FacesMessage.SEVERITY_WARN);

        } else {

        definicaoDao.salvar(usuario);

        addMensagem("Salvado!", "Definições gerais salvas com sucesso.", FacesMessage.SEVERITY_INFO);

        }
    }

    public void listar() throws ClassNotFoundException, SQLException {
        try {
            usuarios = usuarioDao.buscar();
            if (usuarios == null || usuarios.isEmpty()) {
                addMensagem("Nenhum cadastro encontrado!", "Não foi encontrado nehnum cadastro de usuários.", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletar(Usuario c) throws ClassNotFoundException, SQLException {
        try {
            usuarioDao.deletar(c.getIdUsuario());
            listar();
            addMensagem("Removido!", "Usuário removido com sucesso.", FacesMessage.SEVERITY_WARN);
        } catch (ErroSistema ex) {
            addMensagem(ex.getMessage(), ex.getCause().getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public String logOff() {
        FacesContext fc = FacesContext.getCurrentInstance();

        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.invalidate();
        return "index.jsf?faces-redirect=true";
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

    public void editar(Usuario c) {
        String nomeUs = usuario.getConfNomeUsuario();
        String acessoUs = usuario.getConfAcesso();
        String usernameUs = usuario.getConfNomeUsuario();
        int idUs = usuario.getConfIdUsuario();

        usuario = c;

        usuario.setConfNomeUsuario(nomeUs);
        usuario.setConfAcesso(acessoUs);
        usuario.setConfNomeUsuario(usernameUs);
        usuario.setConfIdUsuario(idUs);

    }

    public void addMensagem(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
