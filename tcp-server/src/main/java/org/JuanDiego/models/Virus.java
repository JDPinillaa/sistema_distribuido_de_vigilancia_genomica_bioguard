package org.JuanDiego.models;

import enums.InfectionLevel;

/**
 * Modelo que representa un virus
 * @author Juan Diego
 * @since 20260221
 * @version 1.0
 */
public class Virus {

    ////// Atributos

    /**
     * Representa el nombre del virus
     */
    private String name;

    /**
     * Representa el nivel de infeccion del virus con el Enum InfectionLevel
     */
    private InfectionLevel infectionLevel;

    /**
     * Representa la secuencia genetica del virus
     */
    private String geneticSequence;


    ////// Metodo constructor
    public Virus(String name, InfectionLevel infectionLevel, String geneticSequence) {
        this.name = name;
        this.infectionLevel = infectionLevel;
        this.geneticSequence = geneticSequence;
    }



    ////// Metodos de acceso

    public String getName() {
        return name;
    }

    public InfectionLevel getInfectionLevel() {
        return infectionLevel;
    }

    public String getGeneticSequence() {
        return geneticSequence;
    }
}
