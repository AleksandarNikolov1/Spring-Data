package com.example.usersystem.services.impl;

import com.example.usersystem.models.Picture;
import com.example.usersystem.repositories.PictureRepository;
import com.example.usersystem.services.PictureService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void seedPictures() {
        if (pictureRepository.count() == 0) {
            pictureRepository.saveAll(generatePictures());
        }
    }

    public List<Picture> generatePictures(){
        List<Picture> pictures = new ArrayList<>();

        pictures.add(new Picture("Sunset", "Beautiful sunset over the mountains", "/images/sunset.jpg"));
        pictures.add(new Picture("Beach", "Relaxing day at the beach", "/images/beach.jpg"));
        pictures.add(new Picture("Cityscape", "Night view of the city lights", "/images/cityscape.jpg"));
        pictures.add(new Picture("Forest", "Lush green forest", "/images/forest.jpg"));
        pictures.add(new Picture("Desert", "Vast desert with sand dunes", "/images/desert.jpg"));
        pictures.add(new Picture("Mountain", "Snowy mountain peak", "/images/mountain.jpg"));
        pictures.add(new Picture("River", "Serene river flowing through the valley", "/images/river.jpg"));
        pictures.add(new Picture("Flower", "Close-up of a beautiful flower", "/images/flower.jpg"));
        pictures.add(new Picture("Wildlife", "A deer in the wild", "/images/wildlife.jpg"));
        pictures.add(new Picture("Aurora", "Northern lights in the night sky", "/images/aurora.jpg"));

        return pictures;
    }
}
