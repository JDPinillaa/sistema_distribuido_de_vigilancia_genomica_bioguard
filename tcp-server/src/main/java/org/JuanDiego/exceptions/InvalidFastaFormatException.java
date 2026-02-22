package org.JuanDiego.exceptions;

/**
 * Excepcion personalizada que se lanza cuando un formato fasta no esta en el formato esperado
 * @author Juan Diego
 * @since 20260222
 * @version 1.0
 */
public class InvalidFastaFormatException extends Exception{
    public InvalidFastaFormatException(String message) {
        super(message);
    }
}
