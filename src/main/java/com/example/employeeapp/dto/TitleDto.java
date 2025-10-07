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
public class TitleDto {
    private Integer empNo;
    private String title;
    private LocalDate fromDate;
    private LocalDate toDate;
}
