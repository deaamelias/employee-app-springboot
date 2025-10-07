package com.example.employeeapp.service;

import com.example.employeeapp.dto.DepartmentEmployeeDto;
import com.example.employeeapp.entity.DepartmentEmployee;
import com.example.employeeapp.entity.key.DepartmentEmployeeId;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.repository.DepartmentEmployeeRepository;
import com.example.employeeapp.repository.DepartmentRepository;
import com.example.employeeapp.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentEmployeeService {

    private final DepartmentEmployeeRepository repo;
    private final EmployeeRepository employeeRepo;
    private final DepartmentRepository departmentRepo;

    public DepartmentEmployeeService(DepartmentEmployeeRepository repo,
                                     EmployeeRepository employeeRepo,
                                     DepartmentRepository departmentRepo) {
        this.repo = repo;
        this.employeeRepo = employeeRepo;
        this.departmentRepo = departmentRepo;
    }

    public List<DepartmentEmployeeDto> findAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    public DepartmentEmployeeDto findById(Integer empNo, String deptNo) {
        DepartmentEmployeeId id = new DepartmentEmployeeId(empNo, deptNo);
        return repo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Department employee not found"));
    }

    private DepartmentEmployeeDto toDto(DepartmentEmployee e) {
        return DepartmentEmployeeDto.builder()
                .empNo(e.getId().getEmpNo())
                .deptNo(e.getId().getDeptNo())
                .fromDate(e.getFromDate())
                .toDate(e.getToDate())
                .build();
    }

    public DepartmentEmployeeDto create(DepartmentEmployeeDto dto) {
        var emp = employeeRepo.findById(dto.getEmpNo())
                .orElseThrow(() -> new NotFoundException("Employee not found"));
        var dept = departmentRepo.findById(dto.getDeptNo())
                .orElseThrow(() -> new NotFoundException("Department not found"));

        var entity = DepartmentEmployee.builder()
                .id(new DepartmentEmployeeId(dto.getEmpNo(), dto.getDeptNo()))
                .employee(emp)
                .department(dept)
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .build();

        return toDto(repo.save(entity));
    }

    public DepartmentEmployeeDto update(Integer empNo, String deptNo, DepartmentEmployeeDto dto) {
        DepartmentEmployeeId id = new DepartmentEmployeeId(empNo, deptNo);
        DepartmentEmployee existing = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Department employee not found"));


        existing.setFromDate(dto.getFromDate());
        existing.setToDate(dto.getToDate());

        return toDto(repo.save(existing));
    }

    public void delete(Integer empNo, String deptNo) {
        DepartmentEmployeeId id = new DepartmentEmployeeId(empNo, deptNo);
        if (!repo.existsById(id)) throw new NotFoundException("Department employee not found");
        repo.deleteById(id);
    }
}
