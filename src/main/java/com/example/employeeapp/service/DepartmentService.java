package com.example.employeeapp.service;

import com.example.employeeapp.dto.DepartmentDto;
import com.example.employeeapp.entity.Department;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository repo;

    public DepartmentService(DepartmentRepository repo) {
        this.repo = repo;
    }

    private DepartmentDto toDto(Department d) {
        return DepartmentDto.builder()
                .deptNo(d.getDeptNo())
                .deptName(d.getDeptName())
                .build();
    }

    private Department toEntity(DepartmentDto dto) {
        return Department.builder()
                .deptNo(dto.getDeptNo())
                .deptName(dto.getDeptName())
                .build();
    }

    public List<DepartmentDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DepartmentDto findById(String id) {
        return repo.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Department not found: " + id));
    }

    public DepartmentDto create(DepartmentDto dto) {
        Department created = repo.save(toEntity(dto));
        return toDto(created);
    }

    public DepartmentDto update(String id, DepartmentDto dto) {
        Department dept = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found: " + id));
        dept.setDeptName(dto.getDeptName());
        return toDto(repo.save(dept));
    }

    public void delete(String id) {
        if (!repo.existsById(id)) throw new NotFoundException("Department not found: " + id);
        repo.deleteById(id);
    }
}
