package com.example.employeeapp.controller;

import com.example.employeeapp.dto.ApiResponse;
import com.example.employeeapp.dto.DepartmentDto;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {

    private final DepartmentService service;

    public DepartmentRestController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> all() {
        List<DepartmentDto> data = service.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> get(@PathVariable("id") String id) {
        try {
            DepartmentDto data = service.findById(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentDto>> create(@RequestBody DepartmentDto dto) {
        DepartmentDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/departments/" + created.getDeptNo()))
                .body(new ApiResponse<>(201, "Created", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDto>> update(@PathVariable("id")  String id, @RequestBody DepartmentDto dto) {
        try {
            DepartmentDto updated = service.update(id, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Updated", updated));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") String id) {
        try {
            service.delete(id);
            return ResponseEntity.status(204).body(new ApiResponse<>(204, "Deleted", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }
}
