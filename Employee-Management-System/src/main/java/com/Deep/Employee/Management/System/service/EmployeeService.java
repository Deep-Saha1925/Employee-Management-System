package com.Deep.Employee.Management.System.service;

import com.Deep.Employee.Management.System.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    void deleteEmployeeById(Long id);

    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployeeById(Long id);

//    Page<Employee> findPaginated(int pageNo, int pageSize);

    Page<Employee> findPaginated(int pageNo, int PageSize, String sortField, String sortDirection);
}
