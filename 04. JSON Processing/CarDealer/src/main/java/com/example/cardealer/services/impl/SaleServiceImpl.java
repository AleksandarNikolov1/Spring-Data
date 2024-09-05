package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.CarSaleDiscountDto;
import com.example.cardealer.models.dtos.exports.SaleDiscountDto;
import com.example.cardealer.models.entities.Part;
import com.example.cardealer.models.entities.Sale;
import com.example.cardealer.repositories.SaleRepository;
import com.example.cardealer.services.CarService;
import com.example.cardealer.services.CustomerService;
import com.example.cardealer.services.SaleService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper mapper;
    private final Gson gson;

    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper mapper, Gson gson) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.mapper = mapper;
        this.gson = gson;
    }

    @Override
    public void seedSales() {
        if (saleRepository.count() == 0) {
            for (int i = 0; i < 20; i++) {
                Sale sale = new Sale();
                sale.setCar(carService.getRandomCar());
                sale.setCustomer(customerService.getRandomCustomer());
                sale.setDiscount(getRandomDiscount());

                if (sale.getCustomer().getYoungDriver())
                    sale.setDiscount(sale.getDiscount() + 0.05);

                saleRepository.save(sale);
            }
        }
    }

    @Override
    public void exportSaleDetailsToJson() throws IOException {
       List<SaleDiscountDto> saleDiscountDtoList =
               saleRepository
                .findAll()
                .stream()
                .map(sale -> {
                    SaleDiscountDto saleDto = new SaleDiscountDto();
                    saleDto.setCar(mapper.map(sale.getCar(), CarSaleDiscountDto.class));
                    saleDto.setCustomerName(sale.getCustomer().getName());
                    saleDto.setDiscount(sale.getDiscount());

                    saleDto.setPrice(sale
                            .getCar()
                            .getParts()
                            .stream()
                            .map(Part::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add));

                    saleDto.setPriceWithDiscount(saleDto
                            .getPrice()
                            .multiply(BigDecimal.valueOf(1.0d - saleDto.getDiscount())));

                    return saleDto;
                })
                .collect(Collectors.toList());

        String jsonContent = gson.toJson(saleDiscountDtoList);

        Files.write(Path.of(EXPORT_FILES_PATH + SALES_DISCOUNTS_EXPORT_FILE),
                Collections.singleton(jsonContent));
    }

    private Double getRandomDiscount(){
        Random rnd = new Random();
        Double[] availableDiscounts = new Double[]{0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.4, 0.5};

        int randomIndex = rnd.nextInt(0, availableDiscounts.length);

        return availableDiscounts[randomIndex];
    }


}
