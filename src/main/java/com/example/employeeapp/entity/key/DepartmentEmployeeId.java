package com.example.employeeapp.entity.key;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeeId implements Serializable {
    private Integer empNo;
    private String deptNo;
}
