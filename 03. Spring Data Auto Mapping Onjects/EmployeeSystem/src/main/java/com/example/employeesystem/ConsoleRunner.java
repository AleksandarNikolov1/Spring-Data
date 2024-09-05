package com.example.employeesystem;

import com.example.employeesystem.models.dtos.EmployeeDto;
import com.example.employeesystem.services.EmployeeService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class ConsoleRunner implements CommandLineRunner {
    private final EmployeeService employeeService;

    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        employeeService.seedEmployees();

        // TASK 1 - Simple Mapping
        EmployeeDto employeeDto = employeeService.findOne();
        System.out.println(employeeDto);


        // TASK 2 - Advanced Mapping
        employeeService.findAllManagers()
                .forEach(System.out::println);

        // TASK 3 - Projection
        employeeService.findAllByBirthdayYearBefore(1990)
                .forEach(System.out::println);
    }
}