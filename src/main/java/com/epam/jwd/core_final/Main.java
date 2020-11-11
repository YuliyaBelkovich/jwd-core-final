package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.UpdateManager;
import com.epam.jwd.core_final.context.menu.Menu;
import com.epam.jwd.core_final.context.menu.MenuContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;


public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        createOutputDirectory();

        ApplicationMenu menuApp;
        try {
            menuApp = Application.start();
            UpdateManager update = UpdateManager.getInstance(ApplicationProperties.getInstance());

            menuApp.printAvailableOptions(Menu.START);
            Integer input = menuApp.handleUserInput();
            Menu menu = MenuContext.getInstance().execute(Menu.START, input);

            while (!menu.equals(Menu.EXIT)) {
                menuApp.printAvailableOptions(menu);
                Integer input1 = menuApp.handleUserInput();
                menu = MenuContext.getInstance().execute(menu, input1);
                if (update.needReInit()) {
                    Application.start();
                }
            }
            menuApp.printAvailableOptions(Menu.EXIT);
        } catch (InvalidStateException | UnknownEntityException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createOutputDirectory() {
        Path outputDir = Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "output");
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectory(outputDir);
            } catch (IOException e) {
                log.warn("Trying to create existing directory");
            }
        }
    }
}
