package com.example.employeeapp.entity;

import com.example.employeeapp.entity.key.TitleId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "titles")
@IdClass(TitleId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Title {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Id
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "to_date")
    private LocalDate toDate;
}
