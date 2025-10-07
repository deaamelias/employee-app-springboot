package com.example.employeeapp.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Integer empNo;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate hireDate;
}
