package com.example.employeeapp.service;

import com.example.employeeapp.entity.Employee;
import com.example.employeeapp.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeServiceTest {
    @Test
    void findAllReturnsList() {
        EmployeeRepository repo = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repo.findAll()).thenReturn(List.of(Employee.builder().empNo(1).firstName("A").lastName("B").build()));
        EmployeeService s = new EmployeeService(repo);
        assertThat(s.findAll()).hasSize(1);
    }
}
