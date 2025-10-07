package com.example.employeeapp.controller;

import com.example.employeeapp.dto.ApiResponse;
import com.example.employeeapp.dto.DepartmentEmployeeDto;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.service.DepartmentEmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department-employees")
public class DepartmentEmployeeRestController {

    private final DepartmentEmployeeService service;

    public DepartmentEmployeeRestController(DepartmentEmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentEmployeeDto>>> all() {
        List<DepartmentEmployeeDto> data = service.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
    }

    @GetMapping("/{empNo}/{deptNo}")
    public ResponseEntity<ApiResponse<DepartmentEmployeeDto>> get(@PathVariable("empNo") Integer empNo, @PathVariable("deptNo") String deptNo) {
        try {
            DepartmentEmployeeDto data = service.findById(empNo, deptNo);
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentEmployeeDto>> create(@RequestBody DepartmentEmployeeDto dto) {
        DepartmentEmployeeDto created = service.create(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Created", created));
    }

    @PutMapping("/{empNo}/{deptNo}")
    public ResponseEntity<ApiResponse<DepartmentEmployeeDto>> update(@PathVariable("empNo") Integer empNo, @PathVariable("deptNo") String deptNo, @RequestBody DepartmentEmployeeDto dto) {
        try {
            DepartmentEmployeeDto updated = service.update(empNo, deptNo, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Updated", updated));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{empNo}/{deptNo}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("empNo") Integer empNo, @PathVariable("deptNo") String deptNo) {
        try {
            service.delete(empNo, deptNo);
            return ResponseEntity.status(204).body(new ApiResponse<>(204, "Deleted", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }
}
