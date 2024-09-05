package com.example.usersystem.services;

import com.example.usersystem.models.Album;

import java.util.List;

public interface AlbumService {
    void seedAlbums();
    List<Album> generateAlbums();
}
