package com.example.employeeapp.controller;

import com.example.employeeapp.dto.ApiResponse;
import com.example.employeeapp.dto.TitleDto;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.service.TitleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/titles")
public class TitleRestController {

    private final TitleService service;

    public TitleRestController(TitleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TitleDto>>> all() {
        List<TitleDto> data = service.findAll();
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
    }

    @GetMapping("/{empNo}")
    public ResponseEntity<ApiResponse<TitleDto>> get(@PathVariable Integer empNo) {
        try {
            TitleDto data = service.findByEmpNo(empNo);
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", data));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TitleDto>> create(@RequestBody TitleDto dto) {
        TitleDto created = service.create(dto);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Created", created));
    }

    @PutMapping("/{empNo}")
    public ResponseEntity<ApiResponse<TitleDto>> update(@PathVariable Integer empNo, @RequestBody TitleDto dto) {
        try {
            TitleDto updated = service.update(empNo, dto);
            return ResponseEntity.ok(new ApiResponse<>(200, "Updated", updated));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/{empNo}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer empNo) {
        try {
            service.delete(empNo);
            return ResponseEntity.status(204).body(new ApiResponse<>(204, "Deleted", null));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }
}
