package com.example.employeeapp.service;

import com.example.employeeapp.dto.TitleDto;
import com.example.employeeapp.entity.Employee;
import com.example.employeeapp.entity.Title;
import com.example.employeeapp.exception.NotFoundException;
import com.example.employeeapp.repository.EmployeeRepository;
import com.example.employeeapp.repository.TitleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleService {

    private final TitleRepository repo;
    private final EmployeeRepository employeeRepo;

    public TitleService(TitleRepository repo, EmployeeRepository employeeRepo) {
        this.repo = repo;
        this.employeeRepo = employeeRepo;
    }

    private TitleDto toDto(Title t) {
        return TitleDto.builder()
                .empNo(t.getEmployee().getEmpNo())
                .title(t.getTitle())
                .fromDate(t.getFromDate())
                .toDate(t.getToDate())
                .build();
    }

    private Title toEntity(TitleDto dto) {
        Employee emp = employeeRepo.findById(dto.getEmpNo())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        return Title.builder()
                .employee(emp)
                .title(dto.getTitle())
                .fromDate(dto.getFromDate())
                .toDate(dto.getToDate())
                .build();
    }

    public List<TitleDto> findAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public TitleDto findByEmpNo(Integer empNo) {
        return repo.findFirstByEmployeeEmpNoOrderByFromDateDesc(empNo)
                .map(this::toDto)
                .orElseThrow(() -> new NotFoundException("Title not found"));
    }

    public TitleDto create(TitleDto dto) {
        return toDto(repo.save(toEntity(dto)));
    }

    public TitleDto update(Integer empNo, TitleDto dto) {
        Title existing = repo.findFirstByEmployeeEmpNoOrderByFromDateDesc(empNo)
                .orElseThrow(() -> new NotFoundException("Title not found"));
        existing.setTitle(dto.getTitle());
        existing.setFromDate(dto.getFromDate());
        existing.setToDate(dto.getToDate());
        return toDto(repo.save(existing));
    }

    public void delete(Integer empNo) {
        Title existing = repo.findFirstByEmployeeEmpNoOrderByFromDateDesc(empNo)
                .orElseThrow(() -> new NotFoundException("Title not found"));
        repo.delete(existing);
    }
}
