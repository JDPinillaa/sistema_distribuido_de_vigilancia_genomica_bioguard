package org.JuanDiego.models;


/**
 * Modelo que representa el diagnostico de la muestra de ADN (@DNASample) de un paciente
 * @author Juan Diego
 * @since 20260221
 * @version 1.1
 */
public class Diagnostic {

    ////// Atributos

    /**
     * Representa el virus detectado en el diagnostico
     */
    private Virus detectedVirus;

    /**
     * Representa la posición de inicio donde existen cambios en la secuencia de ADN
     */
    private int firstPosition;

    /**
     * Representa la posición final donde existen cambios en la secuencia de ADN
     */
    private int lastPosition;


    ////// Metodo constructor
    public Diagnostic(Virus detectedVirus, int firstPosition, int lastPosition) {
        this.detectedVirus = detectedVirus;
        this.firstPosition = firstPosition;
        this.lastPosition = lastPosition;
    }


    ////// Metodos de acceso

    public Virus getDetectedVirus() {
        return detectedVirus;
    }

    public int getFirstPosition() {
        return firstPosition;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    /**
     * Metodo que retorna los datos del diagnostico en un renglon listo para pasarse a un CSV
     */
    public String toCSV(){
        return String.join(",", detectedVirus.getName(), String.valueOf(firstPosition), String.valueOf(lastPosition));
    }
}
