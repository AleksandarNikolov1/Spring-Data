package com.example.employeesystem.models.dtos;

import java.math.BigDecimal;

public class EmployeeWithManagerDto {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private String managerLastName;

    public EmployeeWithManagerDto() {
    }

    public EmployeeWithManagerDto(String firstName, String lastName, BigDecimal salary, String managerLastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerLastName = managerLastName;
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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    @Override
    public String toString() {
        if (managerLastName == null) {
            return String.format("%s %s %.2f - Manager: [no manager]",
                    firstName, lastName, salary);
        }

        else {
            return String.format("%s %s %.2f - Manager: %s",
                    firstName, lastName, salary, managerLastName);
        }
    }
}
