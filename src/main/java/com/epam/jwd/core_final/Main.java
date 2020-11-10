package com.epam.jwd.core_final;

import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.context.UpdateManager;
import com.epam.jwd.core_final.context.menu.Menu;
import com.epam.jwd.core_final.context.menu.MenuContext;
import com.epam.jwd.core_final.domain.ApplicationProperties;
import com.epam.jwd.core_final.exception.InvalidStateException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        } catch (InvalidStateException | UnknownEntityException | IOException e) {
            System.out.println(e.getMessage());
        }

        //      MenuMissionServiceImpl.getInstance().createMission();

//        ArrayList<CrewMember> collection = (ArrayList<CrewMember>) NassaContext.getInstance().retrieveBaseEntityList(CrewMember.class);
//        System.out.println(collection.get(1));
//
//        CrewMemberCriteria criteria = new CrewMemberCriteria();
//        criteria.setName("Davey Bentley");
//        criteria.build();
//        System.out.println(criteria.build().toString());
//        CrewServiceImpl service = CrewServiceImpl.getInstance();
//        System.out.println(service.findCrewMemberByCriteria(criteria).get().toString());
//
        //  FlightMission mission = MissionServiceImpl.getInstance().createMission("test", LocalDate.now(), LocalDate.of(2020, 10, 7), 10000L);
//
//        String baseFile = "src/main/resources/output/mission.json";
//        File file = new File(baseFile);
//        Files.createDirectory(new Path())
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            mapper.writeValue(file, mission);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("json created!");


//
//        Test test = new Test(1,"A");
//        List<Test> list = new ArrayList<>();
//        list.add(test);
//        list.add(new Test(2,"B"));
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(new File("A"), list);

    }

    public static void createOutputDirectory() {
        Path outputDir = Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "output");
        if (!Files.exists(outputDir)) {
            try {
                Files.createDirectory(outputDir);
            } catch (IOException e) {
                log.debug("Trying to create existing directory");
            }
        }
    }
}


//class Test {
//    private int id;
//    private String name;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Test(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }
