package com.example.employeeapp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empNo;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate hireDate;
}
