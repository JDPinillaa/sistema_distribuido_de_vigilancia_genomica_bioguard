package org.JuanDiego.business.commands;


/**
 * Interfaz que es implementada por el factory del sistema, tiene un unico metodo getHandler, usado en el factory.
 */
public interface ICommandHandlerFactory {
    ICommandHandler getHandler(String command);
}
