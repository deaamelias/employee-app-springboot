package com.example.employeeapp.controller;

import com.example.employeeapp.dto.ApiResponse;
import com.example.employeeapp.dto.EmployeeDto;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    private final EmployeeService service;

    public EmployeeRestController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> all() {
        List<EmployeeDto> employees = service.findAll();
        ApiResponse<List<EmployeeDto>> response = new ApiResponse<>(200, "Success", employees);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> get(@PathVariable("id") Integer id) {
        try {
            EmployeeDto employee = service.findById(id);
            ApiResponse<EmployeeDto> response = new ApiResponse<>(200, "Success", employee);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            ApiResponse<EmployeeDto> response = new ApiResponse<>(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            ApiResponse<EmployeeDto> response = new ApiResponse<>(500, "Internal server error", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> create(@RequestBody EmployeeDto dto) {
        EmployeeDto created = service.create(dto);
        ApiResponse<EmployeeDto> response = new ApiResponse<>(201, "Employee created successfully", created);
        URI location = URI.create("/api/employees/" + created.getEmpNo());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> update(
            @PathVariable("id") Integer id,
            @RequestBody EmployeeDto dto) {
        try {
            EmployeeDto updated = service.update(id, dto);
            ApiResponse<EmployeeDto> response = new ApiResponse<>(200, "Employee updated successfully", updated);
            return ResponseEntity.ok(response);
        } catch (NotFoundException e) {
            ApiResponse<EmployeeDto> response = new ApiResponse<>(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            ApiResponse<EmployeeDto> response = new ApiResponse<>(500, "Internal server error", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") Integer id) {
        try {
            service.delete(id);
            ApiResponse<Void> response = new ApiResponse<>(204, "Employee deleted successfully", null);
            return ResponseEntity.status(204).body(response);
        } catch (NotFoundException e) {
            ApiResponse<Void> response = new ApiResponse<>(404, e.getMessage(), null);
            return ResponseEntity.status(404).body(response);
        } catch (Exception e) {
            ApiResponse<Void> response = new ApiResponse<>(500, "Internal server error", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}