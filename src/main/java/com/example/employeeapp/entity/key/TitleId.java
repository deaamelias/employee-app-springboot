package com.example.employeeapp.entity.key;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class TitleId implements Serializable {
    private Integer employee;  // employee empNo
    private LocalDate fromDate;

    public TitleId() {
    }

    public TitleId(Integer employee, LocalDate fromDate) {
        this.employee = employee;
        this.fromDate = fromDate;
    }

    // getters, setters

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // equals and hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TitleId)) return false;
        TitleId titleId = (TitleId) o;
        return Objects.equals(employee, titleId.employee) &&
                Objects.equals(fromDate, titleId.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, fromDate);
    }
}
