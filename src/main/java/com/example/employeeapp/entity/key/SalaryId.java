package com.example.employeeapp.entity.key;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class SalaryId implements Serializable {

    private Integer empNo;       // employee primary key
    private LocalDate fromDate;  // from_date as part of composite key

    public SalaryId() {}

    public SalaryId(Integer empNo, LocalDate fromDate) {
        this.empNo = empNo;
        this.fromDate = fromDate;
    }

    // Getters and Setters
    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // equals and hashCode - very important for composite keys!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SalaryId)) return false;
        SalaryId salaryId = (SalaryId) o;
        return Objects.equals(empNo, salaryId.empNo) &&
                Objects.equals(fromDate, salaryId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo, fromDate);
    }
}
