package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.SupplierViewRootDto;
import com.example.cardealer.models.dtos.imports.SupplierSeedRootDto;
import com.example.cardealer.models.entities.Supplier;
import com.example.cardealer.repositories.SupplierRepository;
import com.example.cardealer.services.api.SupplierService;
import com.example.cardealer.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.cardealer.utils.FilePath.*;


@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, XmlParser xmlParser, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public void seedSuppliers() throws JAXBException, FileNotFoundException {
        if (!areImported()) {
            SupplierSeedRootDto rootDto =
                    xmlParser.fromFile(IMPORT_FILES_PATH + SUPPLIERS_IMPORT_FILE_NAME,
                            SupplierSeedRootDto.class);

            rootDto.getSupplierSeedDtoList()
                    .stream()
                    .map(supplierSeedDto -> mapper.map(supplierSeedDto, Supplier.class))
                    .forEach(supplierRepository::save);
        }
    }

    @Override
    public Supplier getRandomSupplier(){
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, supplierRepository.count() + 1);

        return supplierRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportLocalSuppliersToXml() throws JAXBException {
        SupplierViewRootDto supplierViewRootDto = new SupplierViewRootDto();

        supplierViewRootDto.setSupplierViewDtoList(supplierRepository.getLocalSuppliers());

        xmlParser.writeToFile(EXPORT_FILES_PATH + LOCAL_SUPPLIERS_FILE_NAME,
                supplierViewRootDto);
    }

    @Override
    public Boolean areImported() {
        return supplierRepository.count() > 0;
    }
}
