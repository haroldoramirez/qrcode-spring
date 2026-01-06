package br.com.haroldo.qrcode.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Parameters {

    private String titulo;
    private String tituloPagina;
    private String textoQrCode;

}