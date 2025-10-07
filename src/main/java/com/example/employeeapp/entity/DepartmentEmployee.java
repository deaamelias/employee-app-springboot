package com.example.employeeapp.entity;

import com.example.employeeapp.entity.key.DepartmentEmployeeId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dept_emp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentEmployee {

    @EmbeddedId
    private DepartmentEmployeeId id;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
