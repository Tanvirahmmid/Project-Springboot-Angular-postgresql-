package com.example.employee.service;
import com.example.employee.model.EmployeeModel;

import java.util.List;

public interface EmployeeService {
    EmployeeModel createEmployee(EmployeeModel employee);
    EmployeeModel getEmployeeById(Long id);
    List<EmployeeModel> getAllEmployees();
    EmployeeModel updateEmployee(EmployeeModel employee);
    void deleteEmployee(Long id);
}
