package com.example.employeeapp.service;

import com.example.employeeapp.dto.EmployeeDto;
import com.example.employeeapp.entity.Employee;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repo;


    public EmployeeService(EmployeeRepository repo) { this.repo = repo; }

    private EmployeeDto toDto(Employee e) {
        return EmployeeDto.builder()
                .empNo(e.getEmpNo())
                .birthDate(e.getBirthDate())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .gender(e.getGender())
                .hireDate(e.getHireDate())
                .build();
    }

    private Employee toEntity(EmployeeDto d) {
        return Employee.builder()
                .empNo(d.getEmpNo())
                .birthDate(d.getBirthDate())
                .firstName(d.getFirstName())
                .lastName(d.getLastName())
                .gender(d.getGender())
                .hireDate(d.getHireDate())
                .build();
    }

    public List<EmployeeDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public EmployeeDto findById(Integer id) {
        return repo.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));
    }

    public EmployeeDto create(EmployeeDto dto) {
        Employee saved = repo.save(toEntity(dto));
        return toDto(saved);
    }

    public EmployeeDto update(Integer id, EmployeeDto dto) {
        Employee e = repo.findById(id).orElseThrow(() -> new NotFoundException("Employee not found: " + id));
        e.setBirthDate(dto.getBirthDate());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setGender(dto.getGender());
        e.setHireDate(dto.getHireDate());
        return toDto(repo.save(e));
    }

    public void delete(Integer id) {
        if (!repo.existsById(id)) throw new NotFoundException("Employee not found: " + id);
        repo.deleteById(id);
    }
}
