package com.example.usersystem.services.impl;

import com.example.usersystem.models.Town;
import com.example.usersystem.models.User;
import com.example.usersystem.repositories.AlbumRepository;
import com.example.usersystem.repositories.TownRepository;
import com.example.usersystem.repositories.UserRepository;
import com.example.usersystem.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TownRepository townRepository;
    private final AlbumRepository albumRepository;

    public UserServiceImpl(UserRepository userRepository, TownRepository townRepository, AlbumRepository albumRepository) {
        this.userRepository = userRepository;
        this.townRepository = townRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public void seedUsers() {
        if (userRepository.count() == 0) {
            userRepository.saveAll(generateUsers());
        }
    }


    @Override
    public List<String> findAllUsernamesAndEmailsByEmailProvider(String emailProvider) {
        if (userRepository.existsByEmailEndingWith(emailProvider)) {
           return userRepository.findAllByEmailEndingWith(emailProvider)
                    .stream()
                    .map(u -> u.getUsername() + " " + u.getEmail())
                    .collect(Collectors.toList());
        } else {
            System.out.println("No users found with email domain " + emailProvider);
            return null;
        }
    }

    @Override
    public void setIsDeletedFlagTrueForAllUsersWhoHaveNotBeenLoggedInAfterGivenDate(String date) {
        try {
            LocalDateTime lastTimeLoggedIn = LocalDateTime.parse(date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            userRepository.findAllByLastTimeLoggedInIsBefore(lastTimeLoggedIn)
                    .forEach(user -> {
                        user.setDeleted(true);
                        userRepository.save(user);});
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format.");
        }
    }

    @Override
    public int countAllUsersWhichDeletedFlagIsTrue() {
        return userRepository.countAllByIsDeletedIsTrue();
    }

    @Override
    @Transactional
    public int deleteAllUserWhichDeletedFlagIsTrue() {
        List<User> usersToDelete = userRepository.findAllByIsDeletedTrue();

        for (User user : usersToDelete) {
            albumRepository.deleteAll(user.getAlbums());

            for (User friend : user.getFriends()) {
                friend.getFriends().remove(user);
                userRepository.save(friend);
            }

            user.getAlbums().clear();
            user.getFriends().clear();
        }

        return userRepository.deleteAllByIsDeletedTrue();
    }

    public List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        List<Town> towns = townRepository.findAll();

        User user1 = new User();
        user1.setUsername("john_doe");
        user1.setPassword("Password1!");
        user1.setEmail("john.doe@gmail.com");
        user1.setRegisteredOn(LocalDateTime.now().minusDays(100));
        user1.setLastTimeLoggedIn(LocalDateTime.now().minusDays(10));
        user1.setAge(28);
        user1.setBornTown(towns.get(0)); // Например Sofia
        user1.setCurrentTown(towns.get(1)); // Например Plovdiv
        user1.setDeleted(false);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new User();
        user2.setUsername("jane_smith");
        user2.setPassword("SecurePass2@");
        user2.setEmail("jane.smith@example.com");
        user2.setRegisteredOn(LocalDateTime.now().minusDays(200));
        user2.setLastTimeLoggedIn(LocalDateTime.now().minusDays(5));
        user2.setAge(35);
        user2.setBornTown(towns.get(2)); // Например Varna
        user2.setCurrentTown(towns.get(3)); // Например Burgas
        user2.setDeleted(false);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        User user3 = new User();
        user3.setUsername("alice_wonder");
        user3.setPassword("Wonder3*");
        user3.setEmail("alice.wonderland@gmail.com");
        user3.setRegisteredOn(LocalDateTime.now().minusDays(150));
        user3.setLastTimeLoggedIn(LocalDateTime.now().minusDays(20));
        user3.setAge(25);
        user3.setBornTown(towns.get(4)); // Например Ruse
        user3.setCurrentTown(towns.get(5)); // Например Stara Zagora
        user3.setDeleted(false);
        user3.setFirstName("Alice");
        user3.setLastName("Wonderland");

        User user4 = new User();
        user4.setUsername("bob_builder");
        user4.setPassword("BuildIt5&");
        user4.setEmail("bob.builder@example.com");
        user4.setRegisteredOn(LocalDateTime.now().minusDays(300));
        user4.setLastTimeLoggedIn(LocalDateTime.now().minusDays(15));
        user4.setAge(42);
        user4.setBornTown(towns.get(6)); // Например Pleven
        user4.setCurrentTown(towns.get(7)); // Например Veliko Tarnovo
        user4.setDeleted(false);
        user4.setFirstName("Bob");
        user4.setLastName("Builder");

        User user5 = new User();
        user5.setUsername("charlie_brown");
        user5.setPassword("Charlie6#");
        user5.setEmail("charlie.brown@example.com");
        user5.setRegisteredOn(LocalDateTime.now().minusDays(50));
        user5.setLastTimeLoggedIn(LocalDateTime.now().minusDays(3));
        user5.setAge(30);
        user5.setBornTown(towns.get(8)); // Например Blagoevgrad
        user5.setCurrentTown(towns.get(9)); // Например Vidin
        user5.setDeleted(false);
        user5.setFirstName("Charlie");
        user5.setLastName("Brown");

        // Създаване на приятелства
        Set<User> user1Friends = new HashSet<>();
        user1Friends.add(user2);
        user1Friends.add(user3);
        user1.setFriends(user1Friends);

        Set<User> user2Friends = new HashSet<>();
        user2Friends.add(user1);
        user2Friends.add(user4);
        user2.setFriends(user2Friends);

        Set<User> user3Friends = new HashSet<>();
        user3Friends.add(user1);
        user3Friends.add(user5);
        user3.setFriends(user3Friends);

        Set<User> user4Friends = new HashSet<>();
        user4Friends.add(user2);
        user4.setFriends(user4Friends);

        Set<User> user5Friends = new HashSet<>();
        user5Friends.add(user3);
        user5.setFriends(user5Friends);

        // Добавяне на потребители в списъка
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);

        return users;
    }
}
