package com.example.employeeapp.repository;

import com.example.employeeapp.entity.DepartmentManager;
import com.example.employeeapp.entity.key.DepartmentManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentManagerRepository extends JpaRepository<DepartmentManager, DepartmentManagerId> {
}
