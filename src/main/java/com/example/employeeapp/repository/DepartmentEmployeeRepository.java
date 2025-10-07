package com.example.employeeapp.repository;

import com.example.employeeapp.entity.DepartmentEmployee;
import com.example.employeeapp.entity.key.DepartmentEmployeeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentEmployeeRepository extends JpaRepository<DepartmentEmployee, DepartmentEmployeeId> {
}
