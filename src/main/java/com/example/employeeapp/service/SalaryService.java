package com.example.employeeapp.service;

import com.example.employeeapp.dto.SalaryDto;
import com.example.employeeapp.entity.Employee;
import com.example.employeeapp.entity.Salary;
import com.example.employeeapp.entity.key.SalaryId;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.repository.EmployeeRepository;
import com.example.employeeapp.repository.SalaryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaryService {

    private final SalaryRepository salaryRepo;
    private final EmployeeRepository employeeRepo;

    public SalaryService(SalaryRepository salaryRepo, EmployeeRepository employeeRepo) {
        this.salaryRepo = salaryRepo;
        this.employeeRepo = employeeRepo;
    }

    private SalaryDto toDto(Salary salary) {
        return SalaryDto.builder()
                .empNo(salary.getEmployee().getEmpNo())
                .salary(salary.getSalary())
                .fromDate(salary.getId().getFromDate())
                .toDate(salary.getToDate())
                .build();
    }

    private Salary toEntity(SalaryDto dto) {
        Employee employee = employeeRepo.findById(dto.getEmpNo())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        return Salary.builder()
                .id(new SalaryId(dto.getEmpNo(), dto.getFromDate()))
                .employee(employee)
                .salary(dto.getSalary())
                .toDate(dto.getToDate())
                .build();
    }

    public List<SalaryDto> findAll() {
        return salaryRepo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SalaryDto findById(Integer empNo, LocalDate fromDate) {
        SalaryId id = new SalaryId(empNo, fromDate);
        Salary salary = salaryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Salary not found"));
        return toDto(salary);
    }

    public SalaryDto create(SalaryDto dto) {
        Salary salary = toEntity(dto);
        Salary saved = salaryRepo.save(salary);
        return toDto(saved);
    }

    public SalaryDto update(Integer empNo, LocalDate fromDate, SalaryDto dto) {
        SalaryId id = new SalaryId(empNo, fromDate);
        Salary existing = salaryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Salary not found"));

        existing.setSalary(dto.getSalary());
        existing.setToDate(dto.getToDate());
        return toDto(salaryRepo.save(existing));
    }

    public void delete(Integer empNo, LocalDate fromDate) {
        SalaryId id = new SalaryId(empNo, fromDate);
        Salary existing = salaryRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Salary not found"));
        salaryRepo.delete(existing);
    }
}
