package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class UpdateManager {

    private static Path crewPath;
    private static LocalDateTime crewLastChecked;
    private static Long crewLastSize;
    private static Path spaceshipPath;
    private static LocalDateTime shipLastChecked;
    private static Long spaceshipLastSize;

    private static UpdateManager instance;


    private UpdateManager(ApplicationProperties properties) {
        try {
            crewPath = Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + properties.getInputRootDir() + File.separator + properties.getCrewFileName());
            crewLastChecked = LocalDateTime.now();
            crewLastSize = Files.size(crewPath);
            spaceshipPath = Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + properties.getInputRootDir() + File.separator + properties.getSpaceshipsFileName());
            shipLastChecked = LocalDateTime.now();
            spaceshipLastSize = Files.size(spaceshipPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UpdateManager getInstance(ApplicationProperties properties) {
        if (instance == null) {
            return instance = new UpdateManager(properties);
        } else return instance;
    }

    public boolean needReInit() throws IOException {
        if (crewLastChecked
                .plusMinutes(ApplicationProperties
                        .getInstance()
                        .getFileRefreshRate())
                .isAfter(LocalDateTime.now()) || shipLastChecked.plusMinutes(ApplicationProperties
                .getInstance()
                .getFileRefreshRate())
                .isAfter(LocalDateTime.now())) {
            crewLastChecked = LocalDateTime.now();
            shipLastChecked = LocalDateTime.now();
            if (crewLastSize != Files.size(crewPath) || spaceshipLastSize != Files.size(spaceshipPath)) {
                crewLastSize = Files.size(crewPath);
                spaceshipLastSize = Files.size(spaceshipPath);
                return true;
            } else return false;
        } else return false;
    }


}
