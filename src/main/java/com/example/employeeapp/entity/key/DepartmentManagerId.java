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
public class DepartmentManagerId implements Serializable {
    private String deptNo;
    private Integer empNo;
}
