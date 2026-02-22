package org.JuanDiego.models;

import java.util.Date;

/**
 * Modelo que representa una muestra de ADN
 * @author Juan Diego
 * @since 20260221
 * @version 1.1
 */
public class DNASample {
    ////// Atributos

    /**
     * Representa el numero de identificacion del paciente
     */
    private String patientId;

    /**
     * Representa la fecha de envio de la muestra
     */
    private String date;

    /**
     * Representa la secuencia ACTG de la muestra
     */
    private String sequence;


    ////// Metodo constructor
    public DNASample(String patientId, String date, String sequence) {
        this.patientId = patientId;
        this.date = date;
        this.sequence = sequence;
    }

    ////// Metodos de acceso

    public String getPatientId() {
        return patientId;
    }

    public String getDate() {
        return date;
    }

    public String getSequence() {
        return sequence;
    }


    public static DNASample fromFasta(String firstLine, String scndLine){
        if(!firstLine.startsWith(">") && !firstLine.contains("|")){
            throw new IllegalArgumentException("La primera linea del formato fasta no esta en el formato adecuado: >documento | fecha");
        }

        String[] data = firstLine.substring(1).split("//|");

        String patientId = data[0].trim();
        String date = data[1].trim();
        String sequence = scndLine.trim().toUpperCase();

        return new DNASample(patientId, date, sequence);

    }
}
