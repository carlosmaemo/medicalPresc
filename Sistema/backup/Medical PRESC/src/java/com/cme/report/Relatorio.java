package com.cme.report;

import com.cme.exception.ErroSistema;
import com.cme.model.Prescricao;
import com.cme.model.Usuario;
import com.cme.util.Conecxao;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import javax.activation.DataSource;
import javax.mail.*;
import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Relatorio {

    JasperPrint imprimirJ;
    private final HttpServletResponse response;
    private final FacesContext context;
    private ByteArrayOutputStream baos;
    private InputStream steam;

    private String emissor;
    private String password;
    private String receptor;

    public Relatorio() {

        this.context = FacesContext.getCurrentInstance();
        this.response = (HttpServletResponse) context.getExternalContext().getResponse();
    }

    public void getRelatorio() throws IOException, ErroSistema {

        steam = this.getClass().getResourceAsStream("report3.jasper");
        Map<String, Object> params = new HashMap<>();
        baos = new ByteArrayOutputStream();

        try {

            Connection conexao = Conecxao.getConexao();
            JasperReport report = (JasperReport) JRLoader.loadObject(steam);
            JasperPrint print = JasperFillManager.fillReport(report, params, conexao);
            JasperExportManager.exportReportToPdfStream(print, baos);

            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=Relatório1.pdf");
            
            response.getOutputStream().write(baos.toByteArray());
            
            response.getOutputStream().flush();
            response.getOutputStream().close();

            context.responseComplete();

            Conecxao.fecharConexao();

        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getRelatorioFiltro(Prescricao paciente) throws IOException, ErroSistema {

        steam = this.getClass().getResourceAsStream("report1.jasper");
        Map<String, Object> params = new HashMap<>();
        //params.put("dataInicial", paciente.getDataInicial());
        //params.put("dataFinal", paciente.getDataFinal());
        baos = new ByteArrayOutputStream();

        try {

            Connection conexao = Conecxao.getConexao();
            JasperReport report = (JasperReport) JRLoader.loadObject(steam);
            JasperPrint print = JasperFillManager.fillReport(report, params, conexao);
            JasperExportManager.exportReportToPdfStream(print, baos);

            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=Relatório1.pdf");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            context.responseComplete();

            Conecxao.fecharConexao();

        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getRelatorioIndividual(Prescricao p, Usuario i) throws ErroSistema, IOException {

        steam = this.getClass().getResourceAsStream("report2.jasper");
        Map<String, Object> params = new HashMap<>();
        //params.put("registoPaciente", p.getNrRegistro());
        params.put("registoUsuario", i.getConfIdUsuario());
        baos = new ByteArrayOutputStream();

        try {

            Connection conexao = Conecxao.getConexao();

            JasperReport report = (JasperReport) JRLoader.loadObject(steam);
            JasperPrint print = JasperFillManager.fillReport(report, params, conexao);
            JasperExportManager.exportReportToPdfStream(print, baos);

            response.reset();
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            response.setHeader("Content-disposition", "inline; filename=Relatório 2.pdf");

            //File file = new File("D:/Relatórios/" + p.getNome() + "/" + i.getConfNome() + "/");
            //file.mkdirs();

            //JasperExportManager.exportReportToPdfFile(print, file.getAbsolutePath() + "/Relatório.pdf");

            response.getOutputStream().write(baos.toByteArray());

            EnviarEmail(p, i);

            response.getOutputStream().flush();
            response.getOutputStream().close();

            context.responseComplete();

            Conecxao.fecharConexao();

        } catch (JRException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void EnviarEmail(Prescricao p, Usuario u) {

        
        if (u.getConfEmailEmissor().isEmpty()) {
            emissor = "vigilanciaep.cembondeiro@gmail.com";
        } else {
            emissor = u.getConfEmailEmissor();
        }
        
        if (u.getConfPasswordEmailEmissor().isEmpty()) {
            password = "Embondeiro18";
        } else {
            password = u.getConfPasswordEmailEmissor();
        }
        
        if (u.getConfEmailReceptor().isEmpty()) {
            receptor = "khaizer.bagus@cmembondeiro.com";
        } else {
            receptor = u.getConfEmailReceptor();
        }

        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emissor, password);
            }
        });
                
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emissor));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receptor));
            message.setSubject("Relatório de Paciente");

            //File file = new File("D:/Relatórios/" + p.getNome() + "/" + u.getConfNome() + "/");
            //file.mkdirs();

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            //messageBodyPart.setText(u.getConfNome() + "\n" + u.getConfFuncao() + "\n\n" + "Nome do Paciente: " + p.getNome());

            multipart.addBodyPart(messageBodyPart);

            BodyPart attachmentPart = new MimeBodyPart();

            String fileName = "Relatório.pdf";
            //DataSource source = new FileDataSource(file.getAbsolutePath() + "/Relatório.pdf");
            //attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(fileName);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);
                
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
