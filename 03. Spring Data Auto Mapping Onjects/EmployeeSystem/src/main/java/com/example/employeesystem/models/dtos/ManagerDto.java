package com.example.employeesystem.models.dtos;

import java.util.List;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;

    public ManagerDto() {
    }

    public ManagerDto(String firstName, String lastName, List<EmployeeDto> employees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employees = employees;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName)
                .append(" ")
                .append(lastName)
                .append(" | Employees: ")
                .append(employees.size())
                .append(System.lineSeparator());

        for (EmployeeDto employee : employees) {
            sb.append("   - ")
                    .append(employee.getFirstName())
                    .append(" ")
                    .append(employee.getLastName())
                    .append(" ")
                    .append(employee.getSalary())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
