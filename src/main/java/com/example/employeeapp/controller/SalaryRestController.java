package com.example.employeeapp.controller;

import com.example.employeeapp.dto.ApiResponse;
import com.example.employeeapp.dto.SalaryDto;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/salaries")
public class SalaryRestController {

    private final SalaryService service;

    public SalaryRestController(SalaryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SalaryDto>>> all() {
        List<SalaryDto> data = service.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
    }

    @GetMapping("/{empNo}/{fromDate}")
    public ResponseEntity<ApiResponse<SalaryDto>> get(
            @PathVariable Integer empNo,
            @PathVariable String fromDate
    ) {
        try {
            LocalDate from = LocalDate.parse(fromDate);
            SalaryDto data = service.findById(empNo, from);
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Invalid date format", null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SalaryDto>> create(@RequestBody SalaryDto dto) {
        SalaryDto created = service.create(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Created", created));
    }

    @PutMapping("/{empNo}/{fromDate}")
    public ResponseEntity<ApiResponse<SalaryDto>> update(
            @PathVariable Integer empNo,
            @PathVariable String fromDate,
            @RequestBody SalaryDto dto
    ) {
        try {
            LocalDate from = LocalDate.parse(fromDate);
            SalaryDto updated = service.update(empNo, from, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Updated", updated));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Invalid date format", null));
        }
    }

    @DeleteMapping("/{empNo}/{fromDate}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Integer empNo,
            @PathVariable String fromDate
    ) {
        try {
            LocalDate from = LocalDate.parse(fromDate);
            service.delete(empNo, from);
            return ResponseEntity.status(204).body(new ApiResponse<>(204, "Deleted", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "Invalid date format", null));
        }
    }
}