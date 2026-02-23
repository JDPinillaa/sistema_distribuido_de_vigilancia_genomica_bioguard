package org.JuanDiego.models;

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


}
