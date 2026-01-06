package br.com.haroldo.qrcode.api.exceptions;

import java.io.Serial;

public class BusinessRuleException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessRuleException(String msg) {
        super(msg);
    }

}