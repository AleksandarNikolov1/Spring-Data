package com.example.usersystem.services.impl;

import com.example.usersystem.models.Album;
import com.example.usersystem.models.Picture;
import com.example.usersystem.models.User;
import com.example.usersystem.repositories.AlbumRepository;
import com.example.usersystem.repositories.PictureRepository;
import com.example.usersystem.repositories.UserRepository;
import com.example.usersystem.services.AlbumService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, UserRepository userRepository, PictureRepository pictureRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void seedAlbums() {
        if (albumRepository.count() == 0) {
            albumRepository.saveAll(generateAlbums());
        }
    }

    @Override
    public List<Album> generateAlbums() {
        List<Album> albums = new ArrayList<>();
        List<User> users = userRepository.findAll();
        List<Picture> pictures = pictureRepository.findAll();

        // Пример 1
        Album album1 = new Album();
        album1.setName("Summer Vacation");
        album1.setBackgroundColor("#FF5733"); // Оранжев цвят
        album1.setPublic(true);
        album1.setUser(users.get(0)); // Например първият потребител в списъка
        Set<Picture> album1Pictures = new HashSet<>();
        album1Pictures.add(pictures.get(0)); // Примерна снимка
        album1Pictures.add(pictures.get(1)); // Примерна снимка
        album1.setPictures(album1Pictures);
        albums.add(album1);

        // Пример 2
        Album album2 = new Album();
        album2.setName("City Adventures");
        album2.setBackgroundColor("#3498DB"); // Син цвят
        album2.setPublic(false);
        album2.setUser(users.get(1)); // Например вторият потребител в списъка
        Set<Picture> album2Pictures = new HashSet<>();
        album2Pictures.add(pictures.get(2)); // Примерна снимка
        album2Pictures.add(pictures.get(3)); // Примерна снимка
        album2.setPictures(album2Pictures);
        albums.add(album2);

        // Пример 3
        Album album3 = new Album();
        album3.setName("Nature Wonders");
        album3.setBackgroundColor("#2ECC71"); // Зелен цвят
        album3.setPublic(true);
        album3.setUser(users.get(2)); // Например трети потребител в списъка
        Set<Picture> album3Pictures = new HashSet<>();
        album3Pictures.add(pictures.get(4)); // Примерна снимка
        album3Pictures.add(pictures.get(5)); // Примерна снимка
        album3.setPictures(album3Pictures);
        albums.add(album3);

        // Пример 4
        Album album4 = new Album();
        album4.setName("Artistic Moments");
        album4.setBackgroundColor("#E74C3C"); // Червен цвят
        album4.setPublic(true);
        album4.setUser(users.get(3)); // Например четвърти потребител в списъка
        Set<Picture> album4Pictures = new HashSet<>();
        album4Pictures.add(pictures.get(6)); // Примерна снимка
        album4Pictures.add(pictures.get(7)); // Примерна снимка
        album4.setPictures(album4Pictures);
        albums.add(album4);

        // Пример 5
        Album album5 = new Album();
        album5.setName("Wildlife Safari");
        album5.setBackgroundColor("#F39C12"); // Жълт цвят
        album5.setPublic(false);
        album5.setUser(users.get(4)); // Например пети потребител в списъка
        Set<Picture> album5Pictures = new HashSet<>();
        album5Pictures.add(pictures.get(8)); // Примерна снимка
        album5Pictures.add(pictures.get(9)); // Примерна снимка
        album5.setPictures(album5Pictures);
        albums.add(album5);

        Album album6 = new Album();
        album6.setName("2000s");
        album6.setBackgroundColor("#FC12"); // Жълт цвят
        album6.setPublic(true);
        album6.setUser(users.get(4)); // Например пети потребител в списъка
        Set<Picture> album6Pictures = new HashSet<>();
        album6Pictures.add(pictures.get(8)); // Примерна снимка
        album6Pictures.add(pictures.get(9)); // Примерна снимка
        album6.setPictures(album6Pictures);
        albums.add(album6);

        return albums;
    }

}
