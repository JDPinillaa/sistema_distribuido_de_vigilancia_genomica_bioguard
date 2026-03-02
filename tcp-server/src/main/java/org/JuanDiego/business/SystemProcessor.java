package org.JuanDiego.business;

import org.JuanDiego.business.commands.CommandHandlerFactory;
import org.JuanDiego.business.commands.ICommandHandler;
import org.JuanDiego.business.commands.ICommandHandlerFactory;
import org.JuanDiego.persistence.patientRepository.IPatientRepository;
import org.JuanDiego.persistence.sampleRepository.ISampleRepository;
import org.JuanDiego.persistence.virusRepository.IVirusRepository;

/**
 * Esta clase procesa los mensajes entrantes del cliente
 * @author Juan Diego
 * @since 20260223
 * @version 1.1
 */
public class SystemProcessor implements IMessageProcessor {


    /**
     * Se instancia el factory, que es quien se sabe los comandos que permite el sistema en esta implementación
     */
    private final ICommandHandlerFactory handlerFactory;

    public SystemProcessor(IPatientRepository patientRepository, ISampleRepository sampleRepository, IVirusRepository virusRepository, DNAAnalizer dnaAnalizer, ReportGenerator reportGenerator) {
        this.handlerFactory = new CommandHandlerFactory(patientRepository, sampleRepository, virusRepository, dnaAnalizer, reportGenerator);
    }


    /**
     * procesa el mensaje del cliente por comando :: info del paciente, delega la ejecucion de la logica al handler especifico del comando dado por el cliente
     */
    @Override
    public String process(String message) {
        try{
            String[] parts = message.split("::", 2);
            String command = parts[0].trim().toUpperCase();
            String payload = parts.length > 1 ? parts[1].trim() : "";

            ICommandHandler handler = handlerFactory.getHandler(command);
            if (handler == null) {
                return "El comando no fue reconocido";
            }
            return handler.handle(payload);
        }catch (Exception e){
            return "Hubo un error: " + e.getMessage();
        }
    }
}
