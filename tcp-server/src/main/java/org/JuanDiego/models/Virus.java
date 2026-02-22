package org.JuanDiego.models;

import org.JuanDiego.enums.InfectionLevel;

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


    /**
     * Metodo que crea instancias de Virus desde archivos en formato fasta
     */
    public static Virus fromFasta(String firstLine, String secndLine){
        if(!firstLine.startsWith(">") && !firstLine.contains("|")){
            throw new IllegalArgumentException("La primera linea del archivo fasta no esta en el formato adecuado: >virusName | infectionLevel");
        }

        String[] data = firstLine.substring(1).split("//|");

        String name = data[0].trim();
        InfectionLevel infectionLevel = InfectionLevel.fromString(data[1].trim());
        String sequence = secndLine.trim().toUpperCase();

        return new Virus(name, infectionLevel, sequence);

    }
}
