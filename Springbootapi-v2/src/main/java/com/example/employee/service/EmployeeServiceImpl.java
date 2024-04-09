package com.example.employee.service;

import com.example.employee.entity.EmployeeEntity;
import com.example.employee.model.EmployeeModel;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeModel createEmployee(EmployeeModel employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        return mapToModel(employeeEntity);
    }

    @Override
    public EmployeeModel getEmployeeById(Long id) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(this::mapToModel).orElse(null);
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeeRepository.findAll();
        return employeeEntities.stream().map(this::mapToModel).collect(Collectors.toList());
    }

    @Override
    public EmployeeModel updateEmployee(EmployeeModel employee) {
        EmployeeEntity employeeEntity = employeeRepository.findByEmail(employee.getEmail());
        if (employeeEntity == null) {
            return null;
        }
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        return mapToModel(employeeEntity);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeModel mapToModel(EmployeeEntity employeeEntity) {
        EmployeeModel employeeModel = new EmployeeModel();
        BeanUtils.copyProperties(employeeEntity, employeeModel);
        return employeeModel;
    }
}