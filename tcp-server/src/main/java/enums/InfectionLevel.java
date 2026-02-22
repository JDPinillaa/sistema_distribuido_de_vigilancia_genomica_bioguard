package enums;

/**
 * Enum que representa el nivel de infeccion de un virus
 * @author Juan Diego
 * @since 20260221
 * @version 1.0
 */
public enum InfectionLevel {

    ////// Niveles de infeccion

    Nivel_bajo("Nivel de infeccion poco infeccioso"),
    Nivel_medio("Nivel de infeccion medianamente infeccioso"),
    Nivel_Alto("Nivel de infeccion altamente infeccioso");


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
}
