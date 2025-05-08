package com.edisa.formacion.mayo2025.qr_code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;


public class Ejercicio2 {

    public static void main(String[] args){
        String qr=args[0];
        String ruta_destino=args[1];
        String formato =args[2];
        Path ruta = Paths.get(formato);
        int width= 300;
        int height=300;
        try{
            Files.createDirectories(ruta);
            BitMatrix bt=new MultiFormatWriter().encode(qr,BarcodeFormat.valueOf(formato.toUpperCase()),width,height);

            Path path = FileSystems.getDefault().getPath(formato+"/"+ruta_destino);
            MatrixToImageWriter.writeToPath(bt,"JPG",path);
            System.out.println("QR creado en "+ruta_destino);
        }catch (WriterException | IOException e){
            e.printStackTrace();
        }
    }
}
