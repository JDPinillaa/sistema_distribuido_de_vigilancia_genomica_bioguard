package org.JuanDiego.business.commands;

import org.JuanDiego.business.DNAAnalizer;
import org.JuanDiego.business.ReportGenerator;
import org.JuanDiego.business.commands.consultPatient.ConsultPatientHandler;
import org.JuanDiego.business.commands.loadVirusAndCatalog.LoadVirusHandler;
import org.JuanDiego.business.commands.loadVirusAndCatalog.ShowVirusCatalogHandler;
import org.JuanDiego.business.commands.registerPatient.RegisterPatientHandler;
import org.JuanDiego.persistence.patientRepository.IPatientRepository;
import org.JuanDiego.persistence.sampleRepository.ISampleRepository;
import org.JuanDiego.persistence.virusRepository.IVirusRepository;

import java.util.HashMap;
import java.util.Map;


/**
 * Esta clase que implementa ICommandHandlerFactory, contiene todos los comandos que puede manejar el cliente con el sistema mediante un map (comando : handler)
 *
 * @author Juan Diego
 * @since 20260228
 * @version 1.0
 */
public class CommandHandlerFactory implements ICommandHandlerFactory {
    private final Map<String, ICommandHandler> handlers = new HashMap<>();

    public CommandHandlerFactory(IPatientRepository patientRepository, ISampleRepository sampleRepository, IVirusRepository virusRepository, DNAAnalizer dnaAnalizer, ReportGenerator reportGenerator) {
        handlers.put("REGISTERPATIENT", new RegisterPatientHandler(patientRepository));
        handlers.put("CONSULTPATIENT", new ConsultPatientHandler(patientRepository));
        handlers.put("LOADVIRUS", new LoadVirusHandler(virusRepository));
        handlers.put("SHOWVIRUSCATALOG", new ShowVirusCatalogHandler(virusRepository));
        handlers.put("LOADANDANALIZESAMPLE", new org.JuanDiego.business.commands.LoadAndAnalyzeSampleHandler(sampleRepository, virusRepository, dnaAnalizer, reportGenerator, patientRepository));
    }

    /**
     * Se encaraga de obtener el comando que envió el usuario, buscandolo en el map
     */
    @Override
    public ICommandHandler getHandler(String command) {
        return handlers.get(command);
    }
}
