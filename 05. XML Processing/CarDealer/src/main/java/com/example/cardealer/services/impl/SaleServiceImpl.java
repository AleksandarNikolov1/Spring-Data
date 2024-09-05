package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.CarDto;
import com.example.cardealer.models.dtos.exports.SaleViewDto;
import com.example.cardealer.models.dtos.exports.SalesDiscountViewRootDto;
import com.example.cardealer.models.entities.Car;
import com.example.cardealer.models.entities.Customer;
import com.example.cardealer.models.entities.Part;
import com.example.cardealer.models.entities.Sale;
import com.example.cardealer.repositories.SaleRepository;
import com.example.cardealer.services.api.CarService;
import com.example.cardealer.services.api.CustomerService;
import com.example.cardealer.services.api.SaleService;
import com.example.cardealer.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.FilePath.EXPORT_FILES_PATH;
import static com.example.cardealer.utils.FilePath.SALES_DISCOUNT_FILE_NAME;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final ModelMapper mapper;
    private final XmlParser xmlParser;

    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper mapper, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.mapper = mapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean areImported() {
        return saleRepository.count() > 0;
    }

    @Override
    public void seedSales() {
        if (!areImported()) {

            Set<String> sales = new HashSet<>();

            for (int i = 0; i < 20; i++) {
                Sale sale = new Sale();

                Car randomCar = carService.getRandomCar();
                Customer randomCustomer = customerService.getRandomCustomer();
                String key = randomCar.getId() + "-" + randomCustomer.getId();

                if (!sales.contains(key)) {
                    sale.setCar(randomCar);
                    sale.setCustomer(randomCustomer);
                    sale.setDiscount(getRandomDiscount());

                    sales.add(key);
                    saleRepository.save(sale);
                } else {
                    i--; // Ако комбинацията вече съществува, намаляваме брояча за да опитаме отново.
                }
            }
        }
    }

    @Override
    public Double getRandomDiscount() {
        Double[] discounts = new Double[]{0.0, 0.05, 0.1, 0.15, 0.2, 0.3, 0.5};

        int randomIndex = ThreadLocalRandom.current().nextInt(0, discounts.length);

        return discounts[randomIndex];
    }

    @Override
    public void exportSaleDetailsToXml() throws JAXBException {

        SalesDiscountViewRootDto salesDiscountViewRootDto = new SalesDiscountViewRootDto();

        salesDiscountViewRootDto.setSaleViewDtoList(
                saleRepository
                        .findAll()
                        .stream()
                        .map(sale -> {
                            SaleViewDto saleDto = new SaleViewDto();
                            saleDto.setCarDto(mapper.map(sale.getCar(), CarDto.class));
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
                        .collect(Collectors.toList()));


        xmlParser.writeToFile(EXPORT_FILES_PATH + SALES_DISCOUNT_FILE_NAME,
                salesDiscountViewRootDto);
    }
}
