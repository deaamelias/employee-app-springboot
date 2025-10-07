package com.example.employeeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/employees")
    public String employeesPage(Model model) {
        return "employees";
    }

    @GetMapping("/departments")
    public String departmentsPage(Model model) {
        return "departments";
    }

    @GetMapping("/dept_emp")
    public String deptEmpPage(Model model) {
        return "dept_emp";
    }

    @GetMapping("/dept_manager")
    public String deptManagerPage(Model model) {
        return "dept_manager";
    }

    @GetMapping("/salaries")
    public String salariesPage(Model model) {
        return "salaries";
    }

    @GetMapping("/titles")
    public String titlesPage(Model model) {
        return "titles";
    }
}
