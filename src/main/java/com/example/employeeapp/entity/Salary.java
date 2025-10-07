package com.example.employeeapp.entity;

import com.example.employeeapp.entity.key.SalaryId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salary {

    @EmbeddedId
    private SalaryId id;

    @MapsId("empNo")  // Maps empNo attribute of embedded id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "to_date")
    private LocalDate toDate;

    // Convenience methods if needed
    public Integer getEmpNo() {
        return id != null ? id.getEmpNo() : null;
    }

    public LocalDate getFromDate() {
        return id != null ? id.getFromDate() : null;
    }
}
