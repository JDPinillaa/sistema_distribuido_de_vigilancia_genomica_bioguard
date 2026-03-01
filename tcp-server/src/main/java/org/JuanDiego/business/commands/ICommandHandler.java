package org.JuanDiego.business.commands;


/**
 * Interfaz que es implementada por los handlers de los comandos del sistema, incluye un metodo handle, que cada comando va a implementar a su manera
 *
 * @author Juan Diego
 * @since 20260301
 * @version 1.0
 */
public interface ICommandHandler {
    String handle(String payload);
}
