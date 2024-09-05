package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.CarExportDto;
import com.example.cardealer.models.dtos.exports.CustomerWithSalesDto;
import com.example.cardealer.models.dtos.exports.CustomerTotalSalesDto;
import com.example.cardealer.models.dtos.exports.SaleExportDto;
import com.example.cardealer.models.dtos.imports.CustomerDto;
import com.example.cardealer.models.entities.Customer;
import com.example.cardealer.repositories.CustomerRepository;
import com.example.cardealer.services.CustomerService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, Gson gson, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public void seedCustomers() throws IOException {
        String fileContent = Files.readString(Path.of(IMPORT_FILES_PATH + CUSTOMERS_IMPORT_FILE));

        CustomerDto[] customersDto = gson.fromJson(fileContent, CustomerDto[].class);

        Arrays.stream(customersDto).map(dto -> {
            Customer customer = mapper.map(dto, Customer.class);

            customer.setBirthDate(LocalDateTime.parse(dto.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            return customer;
        }).forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = ThreadLocalRandom.current().nextLong(1, customerRepository.count() + 1);

        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportCustomersToJson() throws IOException {
        List<CustomerWithSalesDto> customerDtos =
                customerRepository.getAllByBirthdate()
                        .stream()
                        .map(customer -> {
                            CustomerWithSalesDto customerWithSalesDto = mapper.map(customer, CustomerWithSalesDto.class);
                            customerWithSalesDto.setSales(
                                    customer.getSales()
                                            .stream()
                                            .map(sale -> {
                                                SaleExportDto saleExportDto = mapper.map(sale, SaleExportDto.class);
                                                saleExportDto.setCarExportDto(mapper.map(sale.getCar(), CarExportDto.class));
                                                return saleExportDto;
                                            }).collect(Collectors.toSet()));

                                    return customerWithSalesDto;
                        })
                        .collect(Collectors.toList());

        String jsonContent = gson.toJson(customerDtos);

        Files.write(Path.of(EXPORT_FILES_PATH + ORDERED_CUSTOMERS_EXPORT_FILE),
                Collections.singleton(jsonContent));
    }

    @Override
    public void exportCustomerSalesToJson() throws IOException {
        List<CustomerTotalSalesDto> customerSalesDtos = customerRepository.getAllCustomerSales();

        String jsonContent = gson.toJson(customerSalesDtos);

        Files.write(Path.of(EXPORT_FILES_PATH + CUSTOMER_TOTAL_SALES_EXPORT_FILE),
                Collections.singleton(jsonContent));
    }

}
