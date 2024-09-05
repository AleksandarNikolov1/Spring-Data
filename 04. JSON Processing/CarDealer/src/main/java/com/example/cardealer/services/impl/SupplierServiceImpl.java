package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.LocalSupplierDto;
import com.example.cardealer.models.dtos.imports.SupplierDto;
import com.example.cardealer.models.entities.Supplier;
import com.example.cardealer.repositories.SupplierRepository;
import com.example.cardealer.services.SupplierService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, Gson gson, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (supplierRepository.count() == 0){

            String fileContent = Files.readString
                    (Path.of(IMPORT_FILES_PATH + SUPPLIERS_IMPORT_FILE));

            SupplierDto[] suppliersDto = gson.fromJson(fileContent, SupplierDto[].class);

            Arrays.stream(suppliersDto)
                    .map(dto -> mapper.map(dto, Supplier.class))
                    .forEach(supplierRepository::save);
        }
    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = ThreadLocalRandom
                .current().nextLong(1, supplierRepository.count() + 1);

        return supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportLocalSuppliersToJson() throws IOException {
        List<LocalSupplierDto> localSupplierDtos = supplierRepository.getLocalSuppliers();

        String jsonContent = gson.toJson(localSupplierDtos);

        Files.write(Path.of(EXPORT_FILES_PATH + LOCAL_SUPPLIERS_EXPORT_FILE),
                Collections.singleton(jsonContent));
    }
}
