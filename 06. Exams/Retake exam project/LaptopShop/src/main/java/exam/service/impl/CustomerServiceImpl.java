package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.CustomerSeedDto;
import exam.model.entities.Customer;
import exam.repository.CustomerRepository;
import exam.service.CustomerService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMERS_FILE_PATH = "src/main/resources/files/json/customers.json";
    private final CustomerRepository customerRepository;
    private final TownService townService;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, TownService townService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.townService = townService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        return Files.readString(Path.of(CUSTOMERS_FILE_PATH));
    }

    @Override
    public String importCustomers() throws IOException {
        StringBuilder sb = new StringBuilder();

        if (!areImported()){
            CustomerSeedDto[] customerSeedDtos = gson
                    .fromJson(readCustomersFileContent(), CustomerSeedDto[].class);

            Arrays.stream(customerSeedDtos)
                    .filter(customerSeedDto -> {
                        boolean isValid = validationUtil.isValid(customerSeedDto)
                                && !customerRepository.existsByEmail(customerSeedDto.getEmail());

                        if (isValid) {
                            sb.append(String.format("Successfully imported Customer %s %s - %s",
                                    customerSeedDto.getFirstName(), customerSeedDto.getLastName(),
                                    customerSeedDto.getEmail()));
                        } else {
                            sb.append("Invalid Customer");
                        }

                        sb.append(System.lineSeparator());

                        return isValid;
                    })
                    .map(customerSeedDto -> {
                        Customer customer = modelMapper.map(customerSeedDto, Customer.class);
                        customer.setTown(townService.findTownByName(customerSeedDto.getTownNameDto().getName()));

                        return customer;
                    })
                    .forEach(customerRepository::save);

        }
        return sb.toString();
    }
}
