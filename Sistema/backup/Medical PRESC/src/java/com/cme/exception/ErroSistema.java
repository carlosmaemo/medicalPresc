package com.cme.exception;

public class ErroSistema extends Exception {

    public ErroSistema(String mensagem) {

        super(mensagem);

    }

    public ErroSistema(String mensagem, Throwable causa) {

        super(mensagem, causa);

    }

}
