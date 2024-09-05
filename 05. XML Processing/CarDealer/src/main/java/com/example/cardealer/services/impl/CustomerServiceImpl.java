package com.example.cardealer.services.impl;

import com.example.cardealer.models.dtos.exports.CustomerTotalSalesViewRootDto;
import com.example.cardealer.models.dtos.exports.CustomerViewDto;
import com.example.cardealer.models.dtos.exports.CustomerViewRootDto;
import com.example.cardealer.models.dtos.imports.CustomerSeedRootDto;
import com.example.cardealer.models.entities.Customer;
import com.example.cardealer.repositories.CustomerRepository;
import com.example.cardealer.services.api.CustomerService;
import com.example.cardealer.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.cardealer.utils.FilePath.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper mapper) {
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.mapper = mapper;
    }

    @Override
    public Boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public void seedCustomers() throws JAXBException, FileNotFoundException {
        if (!areImported()) {
            CustomerSeedRootDto customerSeedRootDto =
                    xmlParser.fromFile(IMPORT_FILES_PATH + CUSTOMERS_IMPORT_FILE_NAME,
                            CustomerSeedRootDto.class);

            customerSeedRootDto.getCustomerSeedDtoList()
                    .stream()
                    .map(customerSeedDto -> {
                        Customer customer = mapper.map(customerSeedDto, Customer.class);
                        customer.setBirthDate(LocalDateTime.parse(customerSeedDto.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                        return customer;
                    })
                    .forEach(customerRepository::save);
        }
    }

    @Override
    public Customer getRandomCustomer() {
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, customerRepository.count() + 1);

        return customerRepository.findById(randomId).orElse(null);
    }

    @Override
    public void exportOrderedCustomersToXml() throws JAXBException {
        CustomerViewRootDto customerViewRootDto = new CustomerViewRootDto();

        customerViewRootDto.setCustomerViewDtoList(
                customerRepository.getAllByBirthdate()
                        .stream()
                        .map(customer -> mapper.map(customer, CustomerViewDto.class))
                        .collect(Collectors.toList()));

        xmlParser.writeToFile(EXPORT_FILES_PATH + ORDERED_CUSTOMERS_FILE_NAME,
                customerViewRootDto);
    }

    @Override
    public void exportCustomerTotalSalesToXml() throws JAXBException {
        CustomerTotalSalesViewRootDto customerTotalSalesViewRootDto = new CustomerTotalSalesViewRootDto();

        customerTotalSalesViewRootDto.setCustomerTotalSalesViewDtoList(
                customerRepository.getAllCustomerSales()
        );

        xmlParser.writeToFile(EXPORT_FILES_PATH + CUSTOMERS_TOTAL_SALES_FILE_NAME,
                customerTotalSalesViewRootDto);
    }
}
