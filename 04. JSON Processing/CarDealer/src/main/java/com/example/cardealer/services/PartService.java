package com.example.cardealer.services;

import com.example.cardealer.models.entities.Part;

import java.io.IOException;
import java.util.Set;

public interface PartService {
    void seedParts() throws IOException;
    Set<Part> getRandomParts();
}
