package com.cme.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Carlos Maemo
 */

@ManagedBean
@SessionScoped
public class DefinicaoBean implements Serializable {

    private String ano;

    public String getAno() throws ParseException {

        Calendar now = Calendar.getInstance();

        ano = String.valueOf(now.get(Calendar.YEAR));

        return ano;
    }

}
