package com.edisa.formacion.mayo2025.dropwizard;

import com.google.zxing.*;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class Recursos {

    @GET
    @Path("/codabar/generar")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarCodigoBarras(@QueryParam("texto") String texto,
            @QueryParam("formato_codigo_barras") String formatoCodigoBarras){
        String qr=texto;
        String formato =formatoCodigoBarras;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        int width= 300,height=300;
        try{
            BitMatrix bt=new MultiFormatWriter().encode(qr, BarcodeFormat.valueOf(formato.toUpperCase()),width,height);
            BufferedImage imagenQR = MatrixToImageWriter.toBufferedImage(bt);

            ImageIO.write(imagenQR,"jpg",bout);
            return Response.ok(bout.toByteArray()).type("image/jpg").build();
        }catch (WriterException | IOException e){
            e.printStackTrace();
            return Response.serverError().entity("Error generado QR"+e.getMessage()).build();
        }
    }

    @POST
    @Path("/qr_to_json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response imageToJSON(@FormDataParam("file")InputStream file){
        try {
            ArrayList<CodeBar> codeList= new ArrayList<>();

            BufferedImage imagen = ImageIO.read(file);

            LuminanceSource fuente = new BufferedImageLuminanceSource(imagen);
            BinaryBitmap bt = new BinaryBitmap(new HybridBinarizer(fuente));

            QRCodeMultiReader reader = new QRCodeMultiReader();
            Result[] resultados = reader.decodeMultiple(bt);

            for(Result resultado : resultados){
                CodeBar codeBar = new CodeBar(resultado);
                codeList.add(codeBar);
            }
            return Response.ok(codeList).build();
        } catch (NotFoundException e){
            System.err.println("No se encontro un codigo de barras legible en la imagen");
            return Response.status(403).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}