package com.edisa.formacion.mayo2025;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.io.IOException;

public class Ejercicio1 {

    public static void main(String[] args){
        QRCodeWriter qrwriter = new QRCodeWriter();
        String qr=args[0];
        String ruta_destino=args[1];
        int width= 300;
        int height=300;
        try{
            BitMatrix bt=qrwriter.encode(qr,BarcodeFormat.QR_CODE,width,height);
            Path path = FileSystems.getDefault().getPath(ruta_destino);
            MatrixToImageWriter.writeToPath(bt,"JPG",path);
            System.out.println("QR creado en "+ruta_destino);
        }catch (WriterException | IOException e){
            e.printStackTrace();
        }
    }
}
