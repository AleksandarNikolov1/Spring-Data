package com.example.usersystem;

import com.example.usersystem.services.AlbumService;
import com.example.usersystem.services.PictureService;
import com.example.usersystem.services.TownService;
import com.example.usersystem.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final TownService townService;
    private final PictureService pictureService;
    private final UserService userService;
    private final AlbumService albumService;

    public CommandLineRunnerImpl(TownService townService, PictureService pictureService, UserService userService, AlbumService albumService) {
        this.townService = townService;
        this.pictureService = pictureService;
        this.userService = userService;
        this.albumService = albumService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // TASK 1
        seedData();

        // TASK 2
        System.out.println("Enter email provider:");
        String emailProvider = sc.nextLine();
        userService.findAllUsernamesAndEmailsByEmailProvider(emailProvider)
                .forEach(System.out::println);

        // TASK 3
        String dateTime = "2024-07-24 14:09:05";
        userService.setIsDeletedFlagTrueForAllUsersWhoHaveNotBeenLoggedInAfterGivenDate(dateTime);

        System.out.println(userService.countAllUsersWhichDeletedFlagIsTrue() + " users are last logged before " + dateTime);

        // TASK 4
        System.out.println(userService.deleteAllUserWhichDeletedFlagIsTrue() + " are deleted");
    }

    private void seedData() {
        townService.seedTowns();
        pictureService.seedPictures();
        userService.seedUsers();
        albumService.seedAlbums();
    }
}
