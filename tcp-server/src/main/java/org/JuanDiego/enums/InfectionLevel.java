package org.JuanDiego.enums;

/**
 * Enum que representa el nivel de infeccion de un virus
 * @author Juan Diego
 * @since 20260221
 * @version 1.1
 */
public enum InfectionLevel {

    ////// Niveles de infeccion

    Nivel_bajo("Poco infeccioso"),
    Nivel_medio("Medianamente infeccioso"),
    Nivel_Alto("Altamente infeccioso");


    InfectionLevel(String description) {
        this.description = description;
    }

    /**
     * Da una descripcion de cada nivel de infeccion
     */
    private String description;


    /**
     * Metodo de acceso para description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Metodo que crea una instancia de este Enum obteniendo su descripción desde una cadena
     */
    public static InfectionLevel fromString(String text){
        for(InfectionLevel il : InfectionLevel.values()){
            if(il.description.equalsIgnoreCase(text.trim())){
                return il;
            }
        }
        throw new IllegalArgumentException("Nivel de infeccion del virus no valido");
    }
}
