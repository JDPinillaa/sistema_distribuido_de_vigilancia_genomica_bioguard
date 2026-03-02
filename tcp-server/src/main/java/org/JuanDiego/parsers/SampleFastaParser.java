package org.JuanDiego.parsers;

import org.JuanDiego.exceptions.InvalidFastaFormatException;
import org.JuanDiego.models.DNASample;


/**
 * Parser que parsea la información de las muestras de ADN, ya sea para modelar una muestra mediante las lineas de un archivo fasta, o a partir de una instancia de una muestra, crear su formato para ponerla en un archivo fasta
 * @author Juan Diego
 * @since 20260222
 * @version 1.0
 */
public class SampleFastaParser {


    /**
     * Parsea dos lineas de un archivo fasta y construye una DNASample
     */
    public static DNASample parse(String firstLine, String secondLine) throws InvalidFastaFormatException {
        if (firstLine == null || secondLine == null) {
            throw new InvalidFastaFormatException("El archivo fasta esta incompleto");
        }
        if (!firstLine.startsWith(">") || !firstLine.contains("|")) {
            throw new InvalidFastaFormatException("La primera linea del formato fasta no esta en el formato adecuado: >documento | fecha");
        }

        String[] data = firstLine.substring(1).split("\\|");
        if (data.length < 2) {
            throw new InvalidFastaFormatException("La primera linea del formato fasta no esta en el formato adecuado: >documento | fecha");
        }

        String patientId = data[0].trim();
        String date = data[1].trim();
        String sequence = secondLine.trim().toUpperCase();

        return new DNASample(patientId, date, sequence);
    }

    /**
     * Parsea el contenido completo de un archivo fasta y construye una DNASample
     */
    public static DNASample parse(String fastaContent) throws InvalidFastaFormatException {
        if (fastaContent == null) {
            throw new InvalidFastaFormatException("El contenido fasta es nulo");
        }
        String normalized = fastaContent.replace("\\n", "\n");
        String[] lines = normalized.split("\\R", 3);
        if (lines.length < 2) {
            throw new InvalidFastaFormatException("El contenido fasta debe tener al menos 2 lineas");
        }
        return parse(lines[0], lines[1]);
    }

    /**
     * Genera las dos lineas en formato fasta a partir de una DNASample
     */
    public static String[] toFastaLines(DNASample sample) {
        String header = ">" + sample.getPatientId() + "|" + sample.getDate();
        String sequence = sample.getSequence();
        return new String[]{header, sequence};
    }
}
