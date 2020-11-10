package com.epam.jwd.core_final.context.strategy;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.service.entity.impl.CrewServiceImpl;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CrewStrategy implements Strategy {

    private static CrewStrategy crewStrategy;
    private static final Logger logger = Logger.getLogger(CrewStrategy.class);

    private CrewStrategy() {
    }

    public static CrewStrategy getInstance() {
        if (crewStrategy == null) {
            return crewStrategy = new CrewStrategy();
        } else return crewStrategy;
    }

    public void execute(ApplicationProperties properties) throws InvalidStateException {
        FileReader crewFile;
        String fileName = "src" + File.separator + "main" + File.separator + "resources" + File.separator + properties.getInputRootDir() + File.separator + properties.getCrewFileName();

        try {
            crewFile = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            logger.fatal("FileNotFoundException while parsing crew input file: " + e.getMessage());
            throw new InvalidStateException(fileName);

        }

        Scanner crewScan = new Scanner(crewFile);
        crewScan.useDelimiter(";");

        List<String> crewPrototypes = new ArrayList<>();
        crewScan.nextLine();
        while (crewScan.hasNext()) {
            crewPrototypes.add(crewScan.next());
        }

        CrewServiceImpl crewService = CrewServiceImpl.getInstance();

        for (String prototype : crewPrototypes) {
            String[] args = prototype.split(",");

            try {
                crewService.createCrewMember(Integer.parseInt(args[0]),
                        args[1],
                        Integer.parseInt(args[2]));
            } catch (StorageException | EntityCreationException e) {
                logger.fatal("Storage exception while parsing input crew file");
                System.out.println(e.getMessage());
            } catch (RuntimeException e) {
                logger.fatal("Runtime exception while parsing input crew file: " + e.getMessage());
            }
        }
    }
}
