package com.edisa.formacion.mayo2025.dropwizard;

import com.google.zxing.*;

public class CodeBar {
    protected String contenido;
    protected BarcodeFormat formatoBarcode;
    protected long timestamp;

    public CodeBar(Result resultado){
        setContenido(resultado.getText());
        setFormatoBarcode(resultado.getBarcodeFormat());
        setTimestamp(resultado.getTimestamp());
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setFormatoBarcode(BarcodeFormat formatoBarcode) {
        this.formatoBarcode = formatoBarcode;
    }

    public BarcodeFormat getFormatoBarcode() {
        return formatoBarcode;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
