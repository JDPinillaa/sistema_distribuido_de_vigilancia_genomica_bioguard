package org.JuanDiego.enums;

/**
 * Enum que representa el genero del paciente
 * @author Juan Diego
 * @since 20260221
 * @version 1.0
 */
public enum Gender {

    //////Parametros de genero disponibles
    Male('M'),
    Female('F');

    Gender(char abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * Atributo del Enum que guarda la sigla que representa el genero
     */
    private char abbreviation;

    /**
     * Metodo de acceso de la sigla del genero
     */
    public char getAbbreviation() {
        return abbreviation;
    }

    public static Gender fromAbbreviation(String value) {
        char c = value.trim().toUpperCase().charAt(0);
        switch (c) {
            case 'M': return Male;
            case 'F': return Female;
            default: throw new IllegalArgumentException("Genero invalido: " + value);
        }
    }
}
