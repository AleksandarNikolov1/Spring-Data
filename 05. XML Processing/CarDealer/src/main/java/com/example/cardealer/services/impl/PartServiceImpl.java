package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.imports.PartSeedRootDto;
import com.example.cardealer.models.entities.Part;
import com.example.cardealer.repositories.PartRepository;
import com.example.cardealer.services.api.PartService;
import com.example.cardealer.services.api.SupplierService;
import com.example.cardealer.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;
    private final SupplierService supplierService;

    public PartServiceImpl(PartRepository partRepository, XmlParser xmlParser, ModelMapper mapper, SupplierService supplierService) {
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
        this.supplierService = supplierService;
    }


    @Override
    public Boolean areImported() {
        return partRepository.count() > 0;
    }

    @Override
    public void seedParts() throws JAXBException, FileNotFoundException {
        if (!areImported()){

            PartSeedRootDto partSeedRootDto =
                    xmlParser.fromFile(IMPORT_FILES_PATH + PARTS_IMPORT_FILE_NAME,
                            PartSeedRootDto.class);

            partSeedRootDto.getPartSeedDtoList()
                    .stream()
                    .map(partSeedDto -> {
                        Part part = mapper.map(partSeedDto, Part.class);
                        part.setSupplier(supplierService.getRandomSupplier());

                        return part;
                    })
                    .forEach(partRepository::save);
        }
    }

    @Override
    public Set<Part> getRandomParts() {
        Set<Part> parts = new HashSet<>();

        long randomId = ThreadLocalRandom.current()
                .nextLong(1, partRepository.count() + 1);


        for (int i = 10; i <= 20 ; i++) {
            Part part = partRepository.findById(randomId).orElse(null);
            parts.add(part);
        }

        return parts;
    }
}
