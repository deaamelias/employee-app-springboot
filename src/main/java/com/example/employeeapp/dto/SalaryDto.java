package com.example.employeeapp.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryDto {
    private Integer empNo;
    private Integer salary;
    private LocalDate fromDate;
    private LocalDate toDate;
}
