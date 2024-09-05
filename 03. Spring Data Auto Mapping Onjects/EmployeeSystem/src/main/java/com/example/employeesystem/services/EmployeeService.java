package com.example.employeesystem.services;

import com.example.employeesystem.models.dtos.EmployeeDto;
import com.example.employeesystem.models.dtos.EmployeeWithManagerDto;
import com.example.employeesystem.models.dtos.ManagerDto;
import com.example.employeesystem.models.entities.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeDto findOne();
    List<Employee> generateEmployees();
    void seedEmployees();
    List<ManagerDto> findAllManagers();

    List<EmployeeWithManagerDto> findAllByBirthdayYearBefore(int birthdayYear);

}
