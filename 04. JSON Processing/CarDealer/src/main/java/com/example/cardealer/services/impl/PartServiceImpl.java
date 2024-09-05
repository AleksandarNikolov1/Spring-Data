package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.imports.PartDto;
import com.example.cardealer.models.entities.Part;
import com.example.cardealer.repositories.PartRepository;
import com.example.cardealer.services.PartService;
import com.example.cardealer.services.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, Gson gson, ModelMapper mapper, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.supplierService = supplierService;
    }

    @Override
    public void seedParts() throws IOException {
        if (partRepository.count() == 0){
            String fileContent = Files.readString
                    (Path.of(IMPORT_FILES_PATH + PARTS_IMPORT_FILE));

            PartDto[] partsDto = gson.fromJson(fileContent, PartDto[].class);

            Arrays.stream(partsDto)
                    .map(dto -> {
                        Part part = mapper.map(dto, Part.class);
                        part.setSupplier(supplierService.getRandomSupplier());

                        return part;
                    })
                    .forEach(partRepository::save);
         }
    }

    @Override
    public Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();

        Random rnd = new Random();
        int partsCount = rnd.nextInt(3, 6);

        for (int i = 0; i < partsCount; i++) {
            long randomId = ThreadLocalRandom.current().nextLong(1, partRepository.count() + 1);
            partRepository.findById(randomId).ifPresent(parts::add);
        }

        return parts;
    }
}
