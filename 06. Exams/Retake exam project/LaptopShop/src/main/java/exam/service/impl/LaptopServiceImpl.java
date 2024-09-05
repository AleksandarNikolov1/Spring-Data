package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.LaptopSeedDto;
import exam.model.entities.Laptop;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class LaptopServiceImpl implements LaptopService {

    private static final String LAPTOPS_FILE_PATH = "src/main/resources/files/json/laptops.json";
    private final LaptopRepository laptopRepository;
    private final ShopService shopService;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopService shopService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.laptopRepository = laptopRepository;
        this.shopService = shopService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(Path.of(LAPTOPS_FILE_PATH));
    }

    @Override
    public String importLaptops() throws IOException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()) {
            LaptopSeedDto[] laptopSeedDtos = gson
                    .fromJson(readLaptopsFileContent(), LaptopSeedDto[].class);

            Arrays.stream(laptopSeedDtos)
                    .filter(laptopSeedDto -> {
                        boolean isValid = validationUtil.isValid(laptopSeedDto)
                                && !laptopRepository.existsByMacAddress(laptopSeedDto.getMacAddress())
                                && (laptopSeedDto.getWarrantyType().equals("BASIC")
                                || laptopSeedDto.getWarrantyType().equals("PREMIUM")
                                || laptopSeedDto.getWarrantyType().equals("LIFETIME"));

                        if (isValid) {
                            sb.append(String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                                    laptopSeedDto.getMacAddress(), laptopSeedDto.getCpuSpeed(),
                                    laptopSeedDto.getRam(), laptopSeedDto.getStorage()));
                        } else {
                            sb.append("Invalid Laptop");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(laptopSeedDto -> {
                        Laptop laptop = modelMapper.map(laptopSeedDto, Laptop.class);
                        laptop.setShop(shopService.findByName(laptopSeedDto.getShopNameDto().getName()));

                        return laptop;
                    })
                    .forEach(laptopRepository::save);

        }
        return sb.toString();
    }

    @Override
    public String exportBestLaptops() {
        StringBuilder sb = new StringBuilder();

        laptopRepository
                .findAllByOrderByCpuSpeedDescRamDescStorageDescMacAddressAsc()
                .forEach(l -> {
                    sb.append(String.format("Laptop - %s\n", l.getMacAddress()));
                    sb.append(String.format("*Cpu speed - %.2f\n", l.getCpuSpeed()));
                    sb.append(String.format("**Ram - %d\n", l.getRam()));
                    sb.append(String.format("***Storage - %d\n", l.getStorage()));
                    sb.append(String.format("****Price - %.2f\n", l.getPrice()));
                    sb.append(String.format("#Shop name - %s\n", l.getShop().getName()));
                    sb.append(String.format("##Town - %s\n", l.getShop().getTown().getName()));
                    sb.append(System.lineSeparator());
                });

        return sb.toString();
    }
}
