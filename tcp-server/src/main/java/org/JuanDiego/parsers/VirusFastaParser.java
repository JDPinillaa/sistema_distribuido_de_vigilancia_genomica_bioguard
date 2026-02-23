package org.JuanDiego.parsers;

import org.JuanDiego.enums.InfectionLevel;
import org.JuanDiego.exceptions.InvalidFastaFormatException;
import org.JuanDiego.models.Virus;


/**
 * Parser que parsea la informacion de los virus, ya sea para a partir de las lineas de un archivo fasta crear uno, o para generar las dos lineas del formato fasta a partir del virus
 * @author Juan Diego
 * @since 20260222
 * @version 1.0
 */
public class VirusFastaParser {
    private VirusFastaParser() {
    }

    /**
     * Parsea dos lineas de un archivo fasta y construye un Virus
     */
    public static Virus parse(String firstLine, String secondLine) throws InvalidFastaFormatException {
        if (firstLine == null || secondLine == null) {
            throw new InvalidFastaFormatException("El archivo fasta esta incompleto");
        }
        if (!firstLine.startsWith(">") || !firstLine.contains("|")) {
            throw new InvalidFastaFormatException("La primera linea del archivo fasta no esta en el formato adecuado: >virusName | infectionLevel");
        }

        String[] data = firstLine.substring(1).split("\\|");
        if (data.length < 2) {
            throw new InvalidFastaFormatException("La primera linea del archivo fasta no esta en el formato adecuado: >virusName | infectionLevel");
        }

        String name = data[0].trim();
        InfectionLevel infectionLevel;
        try {
            infectionLevel = InfectionLevel.fromString(data[1].trim());
        } catch (IllegalArgumentException e) {
            throw new InvalidFastaFormatException("Nivel de infeccion del virus no valido");
        }
        String sequence = secondLine.trim().toUpperCase();

        return new Virus(name, infectionLevel, sequence);
    }

    /**
     * Genera las dos lineas en formato fasta a partir de un Virus
     */
    public static String[] toFastaLines(Virus virus) {
        String header = ">" + virus.getName() + "|" + virus.getInfectionLevel().getDescription();
        String sequence = virus.getGeneticSequence();
        return new String[]{header, sequence};
    }
}
