package com.example.employeeapp.repository;

import com.example.employeeapp.entity.Salary;
import com.example.employeeapp.entity.key.SalaryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, SalaryId> {
    Optional<Salary> findFirstByEmployeeEmpNoOrderByIdFromDateDesc(Integer empNo);
}
