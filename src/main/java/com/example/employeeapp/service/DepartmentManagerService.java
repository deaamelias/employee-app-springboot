package com.example.employeeapp.service;

import com.example.employeeapp.dto.DepartmentManagerDto;
import com.example.employeeapp.entity.DepartmentManager;
import com.example.employeeapp.entity.key.DepartmentManagerId;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.repository.DepartmentManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentManagerService {

    private final DepartmentManagerRepository repo;

    public DepartmentManagerService(DepartmentManagerRepository repo) {
        this.repo = repo;
    }

    private DepartmentManagerDto toDto(DepartmentManager e) {
        return DepartmentManagerDto.builder()
                .deptNo(e.getId().getDeptNo())
                .empNo(e.getId().getEmpNo())
                .fromDate(e.getFromDate())
                .toDate(e.getToDate())
                .build();
    }

    private DepartmentManager toEntity(DepartmentManagerDto dto) {
        return DepartmentManager.builder()
                .id(new DepartmentManagerId(dto.getDeptNo(), dto.getEmpNo()))
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .build();
    }

    public List<DepartmentManagerDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public DepartmentManagerDto findById(String deptNo, Integer empNo) {
        DepartmentManagerId id = new DepartmentManagerId(deptNo, empNo);
        return repo.findById(id).map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Manager not found"));
    }

    public DepartmentManagerDto create(DepartmentManagerDto dto) {
        return toDto(repo.save(toEntity(dto)));
    }

    public DepartmentManagerDto update(String deptNo, Integer empNo, DepartmentManagerDto dto) {
        DepartmentManagerId id = new DepartmentManagerId(deptNo, empNo);
        DepartmentManager existing = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager not found"));
        existing.setFromDate(dto.getFromDate());
        existing.setToDate(dto.getToDate());
        return toDto(repo.save(existing));
    }

    public void delete(String deptNo, Integer empNo) {
        DepartmentManagerId id = new DepartmentManagerId(deptNo, empNo);
        if (!repo.existsById(id)) throw new NotFoundException("Manager not found");
        repo.deleteById(id);
    }
}
