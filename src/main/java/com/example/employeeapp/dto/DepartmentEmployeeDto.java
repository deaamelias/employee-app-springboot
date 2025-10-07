package com.example.employeeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentEmployeeDto {
    private String deptNo;
    private Integer empNo;
    private LocalDate fromDate;
    private LocalDate toDate;
}
