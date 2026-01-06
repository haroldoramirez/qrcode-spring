package br.com.haroldo.qrcode.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StandardErrorDTO {
    private int code;
    private String message;
}
