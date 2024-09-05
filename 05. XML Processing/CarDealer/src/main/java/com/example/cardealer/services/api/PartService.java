package com.example.cardealer.services.api;

import com.example.cardealer.models.entities.Part;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Set;

public interface PartService {

    Boolean areImported();
    void seedParts() throws JAXBException, FileNotFoundException;

    Set<Part> getRandomParts();
}
