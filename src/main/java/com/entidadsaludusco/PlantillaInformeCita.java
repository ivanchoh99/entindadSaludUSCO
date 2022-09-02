package com.entidadsaludusco;

import com.entidadsaludusco.models.entity.Cita;
import com.entidadsaludusco.models.entity.Medicamentos;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.FileSystemResource;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class PlantillaInformeCita {

    Cita cita;
    Document document;
    FileOutputStream archivo;

    Font bold = new Font(Font.FontFamily.HELVETICA, 14,Font.BOLD);

    public PlantillaInformeCita(Cita cita) {
        this.cita = cita;
        document = new Document();

    }

    public FileSystemResource crearRegistro(){

        try {

            archivo = new FileOutputStream(cita.getId()+"_"+cita.getPaciente().getDocumento()+".pdf");
            PdfWriter.getInstance(document, archivo);
            document.open();
            Paragraph titulo = new Paragraph("Cita #"+cita.getId()+" Paciente: "+cita.getPaciente().getDocumento(),bold);
            titulo.setAlignment(1);

            document.add(titulo);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("N Documento: ",bold));
            document.add(new Paragraph("    "+cita.getPaciente().getDocumento()));
            document.add(new Paragraph("Nombre: ",bold));
            document.add(new Paragraph("    "+cita.getPaciente().getNombre()));
            document.add(new Paragraph("Apellido: ",bold));
            document.add(new Paragraph("    "+cita.getPaciente().getApellido()));
            document.add(new Paragraph("Direccion: ",bold));
            document.add(new Paragraph("    "+cita.getPaciente().getDireccion()));
            document.add(new Paragraph("Celular: ",bold));
            document.add(new Paragraph("    "+cita.getPaciente().getCelular()));

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Fecha: ",bold));
            document.add(new Paragraph("    "+cita.getFechaHora()));
            document.add(Chunk.NEWLINE);

            Paragraph diagnostico = new Paragraph(cita.getDiagnostico());
            Paragraph recomendaciones = new Paragraph((cita.getRecomendaciones()));
            document.add(new Paragraph("DIAGNOSTICO",bold));
            document.add(new Paragraph("    "+diagnostico));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("RECOMENDACIONES",bold));
            document.add(new Paragraph("    "+recomendaciones));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Medicamentos a tomar:",bold));
            if(cita.getMedicamentos() != null)
                for (Medicamentos medicamento : cita.getMedicamentos()){
                    document.add(new Paragraph("    "+medicamento.getNombre()));
                }

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Doctor: ",bold));
            document.add(new Paragraph(cita.getMedico().getNombre()));
            document.add(new Paragraph(cita.getMedico().getApellido()));

            document.close();

        }catch (FileNotFoundException | DocumentException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
