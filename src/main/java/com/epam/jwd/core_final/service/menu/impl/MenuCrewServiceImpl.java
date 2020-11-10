package com.epam.jwd.core_final.service.menu.impl;

import com.epam.jwd.core_final.criteria.CrewMemberCriteria;
import com.epam.jwd.core_final.domain.CrewMember;
import com.epam.jwd.core_final.domain.Rank;
import com.epam.jwd.core_final.domain.Role;
import com.epam.jwd.core_final.exception.StorageException;
import com.epam.jwd.core_final.exception.UnknownEntityException;
import com.epam.jwd.core_final.service.entity.impl.CrewServiceImpl;
import com.epam.jwd.core_final.service.menu.MenuCrewService;

import java.util.List;
import java.util.Scanner;

public class MenuCrewServiceImpl implements MenuCrewService {

    private static MenuCrewServiceImpl instance;
    private CrewMember entity;

    private MenuCrewServiceImpl() {
    }

    public static MenuCrewServiceImpl getInstance() {
        if (instance == null) {
            return instance = new MenuCrewServiceImpl();
        } else return instance;
    }

    public void searchCrewMemberById() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id\n");
        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .id(scanner.nextLong())
                .build();

        this.entity = CrewServiceImpl.getInstance().findCrewMemberByCriteria(criteria).orElseThrow(() -> new StorageException("Crew member not found"));
        System.out.println(entity.toString());
    }

    public void searchCrewMemberByName() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name\n");

        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .name(scanner.nextLine())
                .build();

        this.entity = CrewServiceImpl.getInstance().findCrewMemberByCriteria(criteria).orElseThrow(() -> new StorageException("Crew member not found"));
        System.out.println(entity.toString());
    }

    public void searchAllCrewMembersByRank() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the rank id\n");

        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .rankId(scanner.nextInt())
                .build();
        try {
            List<CrewMember> result = CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(criteria);
            if (result.size() == 0) {
                throw new StorageException("Crew members not found");
            }
            for (CrewMember crewMember : result) {
                System.out.println(crewMember.toString() + "\n");
            }
        } catch (UnknownEntityException e) {
            System.out.println(e.getMessage());
        }

    }

    public void searchAllCrewMembersByRole() throws StorageException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the role id\n");

        CrewMemberCriteria criteria = CrewMemberCriteria
                .builder()
                .roleId(scanner.nextInt())
                .build();
        try {
            List<CrewMember> result = CrewServiceImpl.getInstance().findAllCrewMembersByCriteria(criteria);
            if (result.size() == 0) {
                throw new StorageException("Crew members not found");
            }
            for (CrewMember crewMember : result) {
                System.out.println(crewMember.toString() + "\n");
            }
        } catch (UnknownEntityException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCrewMembersName() {
        if (this.entity.isReadyForNextMissions()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new crew member's name\n");

            this.entity.setName(scanner.nextLine());
            System.out.println("Name updated!");
        } else {
            System.out.println("Sorry, this crew member is not available to update!\n");
        }
    }

    public void updateCrewMembersRole() {
        if (this.entity.isReadyForNextMissions()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new role id\n");
            this.entity.setRole(Role.resolveRoleById(scanner.nextInt()));
            System.out.println("Role updated!");
        } else {
            System.out.println("Sorry, this crew member is not available to update!\n");
        }
    }

    public void updateCrewMembersRank() {
        if (this.entity.isReadyForNextMissions()) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the new rank id\n");
            this.entity.setRank(Rank.resolveRankById(scanner.nextInt()));
            System.out.println("Rank updated!");
        } else {
            System.out.println("Sorry, this crew member is not available to update!\n");
        }
    }

}
