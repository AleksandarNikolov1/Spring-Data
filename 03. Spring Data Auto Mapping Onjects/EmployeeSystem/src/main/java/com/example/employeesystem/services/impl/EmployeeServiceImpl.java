package com.example.employeesystem.services.impl;

import com.example.employeesystem.models.dtos.EmployeeDto;
import com.example.employeesystem.models.dtos.EmployeeWithManagerDto;
import com.example.employeesystem.models.dtos.ManagerDto;
import com.example.employeesystem.models.entities.Employee;
import com.example.employeesystem.repositories.EmployeeRepository;
import com.example.employeesystem.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public EmployeeDto findOne() {
        long randomId = ThreadLocalRandom.current()
                .nextLong(1, employeeRepository.count() + 1);

        Employee employee = employeeRepository.findById(randomId).orElse(null);
        EmployeeDto employeeDto = null;

        if (employee != null) {
            employeeDto = modelMapper.map(employee, EmployeeDto.class);
        }

        return employeeDto;
    }


    @Override
    public void seedEmployees() {
        if (employeeRepository.count() == 0){
            employeeRepository.saveAll(generateEmployees());
        }
    }

    @Override
    public List<ManagerDto> findAllManagers() {
        List<Employee> managers = employeeRepository.findAllByManagerIdIsNull();

        if(!managers.isEmpty()) {
            return managers
                    .stream()
                    .map(manager -> modelMapper.map(manager, ManagerDto.class))
                    .toList();
        }

        return null;
    }

    @Override
    public List<EmployeeWithManagerDto> findAllByBirthdayYearBefore(int birthdayYear) {
        return
                employeeRepository.findAllByBirthdayYearBefore(birthdayYear)
                        .stream()
                        .map(employee -> modelMapper.map(employee, EmployeeWithManagerDto.class))
                        .collect(Collectors.toList());
    }


    @Override
    public List<Employee> generateEmployees() {

            List<Employee> employees = new ArrayList<>();

            Employee manager1 = new Employee();
            manager1.setFirstName("John");
            manager1.setLastName("Doe");
            manager1.setSalary(new BigDecimal("120000"));
            manager1.setBirthday(LocalDate.of(1975, 5, 15));
            manager1.setAddress("123 Main St, New York, NY");
            manager1.setOnHoliday(false);
            employees.add(manager1);

            Employee manager2 = new Employee();
            manager2.setFirstName("Jane");
            manager2.setLastName("Smith");
            manager2.setSalary(new BigDecimal("110000"));
            manager2.setBirthday(LocalDate.of(1980, 7, 20));
            manager2.setAddress("456 Oak St, Los Angeles, CA");
            manager2.setOnHoliday(true);
            employees.add(manager2);

            Employee employee1 = new Employee();
            employee1.setFirstName("Alice");
            employee1.setLastName("Johnson");
            employee1.setSalary(new BigDecimal("70000"));
            employee1.setBirthday(LocalDate.of(1990, 11, 30));
            employee1.setAddress("789 Pine St, Chicago, IL");
            employee1.setOnHoliday(false);
            employee1.setManager(manager1);
            employees.add(employee1);

            Employee employee2 = new Employee();
            employee2.setFirstName("Bob");
            employee2.setLastName("Williams");
            employee2.setSalary(new BigDecimal("75000"));
            employee2.setBirthday(LocalDate.of(1985, 3, 12));
            employee2.setAddress("101 Maple St, Houston, TX");
            employee2.setOnHoliday(true);
            employee2.setManager(manager1);
            employees.add(employee2);

            Employee employee3 = new Employee();
            employee3.setFirstName("Charlie");
            employee3.setLastName("Brown");
            employee3.setSalary(new BigDecimal("65000"));
            employee3.setBirthday(LocalDate.of(1992, 9, 5));
            employee3.setAddress("202 Birch St, Miami, FL");
            employee3.setOnHoliday(false);
            employee3.setManager(manager2);
            employees.add(employee3);

            Employee employee4 = new Employee();
            employee4.setFirstName("David");
            employee4.setLastName("Davis");
            employee4.setSalary(new BigDecimal("68000"));
            employee4.setBirthday(LocalDate.of(1988, 4, 18));
            employee4.setAddress("303 Cedar St, Seattle, WA");
            employee4.setOnHoliday(false);
            employee4.setManager(manager2);
            employees.add(employee4);

            // Set the list of employees for each manager
            Set<Employee> manager1Employees = new HashSet<>();
            manager1Employees.add(employee1);
            manager1Employees.add(employee2);
            manager1.setEmployees(manager1Employees);

            Set<Employee> manager2Employees = new HashSet<>();
            manager2Employees.add(employee3);
            manager2Employees.add(employee4);
            manager2.setEmployees(manager2Employees);

            return employees;
        }
}
