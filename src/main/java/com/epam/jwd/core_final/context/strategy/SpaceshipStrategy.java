package com.epam.jwd.core_final.context.strategy;

import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.EntityCreationException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.entity.impl.SpaceshipServiceImpl;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class SpaceshipStrategy implements Strategy {

    private static SpaceshipStrategy spaceshipStrategy;
    private static final Logger logger = Logger.getLogger(SpaceshipStrategy.class);

    private SpaceshipStrategy() {
    }

    public static SpaceshipStrategy getInstance() {
        if (spaceshipStrategy == null) {
            return spaceshipStrategy = new SpaceshipStrategy();
        } else return spaceshipStrategy;
    }

    @Override
    public void execute(ApplicationProperties properties) throws InvalidStateException {
        FileReader spaceshipFile;
        String fileName = "src" + File.separator + "main" + File.separator + "resources" + File.separator + properties.getInputRootDir() + File.separator + properties.getSpaceshipsFileName();

        try {
            spaceshipFile = new FileReader(fileName);
        } catch (FileNotFoundException e) {
            logger.fatal("FileNotFoundException while parsing spaceship input file: " + e.getMessage());
            throw new InvalidStateException(fileName);
        }

        Scanner spaceshipScan = new Scanner(spaceshipFile);

        List<String> spaceshipPrototypes = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            spaceshipScan.nextLine();
        }

        while (spaceshipScan.hasNextLine()) {
            spaceshipPrototypes.add(spaceshipScan.nextLine());
        }

        SpaceshipServiceImpl spaceshipService = SpaceshipServiceImpl.getInstance();

        for (String prototype : spaceshipPrototypes) {

            String[] args = prototype.split(";");
            args[2] = args[2].subSequence(1, args[2].length() - 1).toString();

            try {
                spaceshipService.createSpaceship(args[0],
                        Long.parseLong(args[1]),
                        getCapacity(args[2]));
            } catch (StorageException | EntityCreationException e){
                logger.fatal("Storage Exception while parsing spaceship input file");
                System.out.println(e.getMessage());
            }
        }
    }

    private Map<Role, Short> getCapacity(String capacityPrototype) {
        Map<Role, Short> capacity = new HashMap<>();

        for (String value : capacityPrototype.split(",")) {
            try {
                capacity.put(Role.resolveRoleById(Integer.parseInt(value.split(":")[0])),
                        Short.parseShort(value.split(":")[1]));
            } catch (UnknownEntityException e){
                logger.error(e.getMessage());
            }
        }
        return capacity;
    }
}
