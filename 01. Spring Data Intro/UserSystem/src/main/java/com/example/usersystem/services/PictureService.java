package com.example.usersystem.services;

import com.example.usersystem.models.Picture;

import java.util.List;

public interface PictureService {
    void seedPictures();

    List<Picture> generatePictures();

}
