package com.example.employeeapp.controller;

import com.example.employeeapp.dto.ApiResponse;
import com.example.employeeapp.dto.DepartmentManagerDto;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.service.DepartmentManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department-managers")
public class DepartmentManagerRestController {

    private final DepartmentManagerService service;

    public DepartmentManagerRestController(DepartmentManagerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentManagerDto>>> all() {
        List<DepartmentManagerDto> data = service.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
    }

    @GetMapping("/{deptNo}/{empNo}")
    public ResponseEntity<ApiResponse<DepartmentManagerDto>> get(@PathVariable String deptNo, @PathVariable Integer empNo) {
        try {
            DepartmentManagerDto data = service.findById(deptNo, empNo);
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentManagerDto>> create(@RequestBody DepartmentManagerDto dto) {
        DepartmentManagerDto created = service.create(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Created", created));
    }

    @PutMapping("/{deptNo}/{empNo}")
    public ResponseEntity<ApiResponse<DepartmentManagerDto>> update(@PathVariable String deptNo, @PathVariable Integer empNo, @RequestBody DepartmentManagerDto dto) {
        try {
            DepartmentManagerDto updated = service.update(deptNo, empNo, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Updated", updated));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{deptNo}/{empNo}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String deptNo, @PathVariable Integer empNo) {
        try {
            service.delete(deptNo, empNo);
            return ResponseEntity.status(204).body(new ApiResponse<>(204, "Deleted", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }
}
