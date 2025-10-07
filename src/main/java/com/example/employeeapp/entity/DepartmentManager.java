package com.example.employeeapp.entity;

import com.example.employeeapp.entity.key.DepartmentManagerId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "dept_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentManager {

    @EmbeddedId
    private DepartmentManagerId id;

    @ManyToOne
    @MapsId("deptNo")
    @JoinColumn(name = "dept_no")
    private Department department;

    @ManyToOne
    @MapsId("empNo")
    @JoinColumn(name = "emp_no")
    private Employee employee;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;
}
