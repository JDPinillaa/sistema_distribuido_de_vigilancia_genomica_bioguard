package org.JuanDiego.exceptions;

/**
 * Excepción personalizada que es lanzada cuando se intenta registrar un usuario ya existente
 * @author Juan Diego
 * @since 20260221
 * @version 1.0
 */
public class DuplicatedPatientException extends Exception{
    public DuplicatedPatientException(String message) {
        super(message);
    }
}
