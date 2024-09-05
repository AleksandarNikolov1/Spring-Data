package com.example.employeesystem.repositories;

import com.example.employeesystem.models.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByManagerIdIsNull();

    @Query("SELECT e FROM Employee e WHERE YEAR(e.birthday) < :birthdayYear")
    List<Employee> findAllByBirthdayYearBefore(@Param("birthdayYear")int birthdayYear);
}
