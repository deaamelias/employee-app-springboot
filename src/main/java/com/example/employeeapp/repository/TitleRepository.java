package com.example.employeeapp.repository;

import com.example.employeeapp.entity.Title;
import com.example.employeeapp.entity.key.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleRepository extends JpaRepository<Title, TitleId> {

    Optional<Title> findFirstByEmployeeEmpNoOrderByFromDateDesc(Integer empNo);

}
