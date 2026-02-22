package exceptions;

/**
 * Excepción personalizada que es lanzada cuando se intenta registrar un usuario ya existente
 */
public class DuplicatedPatientException extends Exception{
    public DuplicatedPatientException(String message) {
        super(message);
    }
}
