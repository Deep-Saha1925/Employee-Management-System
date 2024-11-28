package com.Deep.Employee.Management.System.controller;

import com.Deep.Employee.Management.System.model.Employee;
import com.Deep.Employee.Management.System.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String viewHomePage(Model model){
        return findPaginated(1, model, "firstName", "asc");
    }

    @GetMapping("/newEmployee")
    public String newEmployee(Model model){
        Employee employee = new Employee();
        model.addAttribute("employee", employee);

        return "newEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee){
        employeeService.saveEmployee(employee);

        return "redirect:/";
    }

    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model){

        Employee employee = employeeService.getEmployeeById(id);

        if(employee == null){
            throw new RuntimeException("Employee not found");
        }else{
            model.addAttribute("employee", employee);
        }

        return "updateEmployee";
    }


    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id){
        Employee employee = employeeService.getEmployeeById(id);

        if(employee == null){
            throw new RuntimeException("Employee not found");
        }else{
            employeeService.deleteEmployeeById(id);
        }
        return "redirect:/";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable int pageNo, Model model, @RequestParam String sortField, @RequestParam String sortDir){
        int pageSize = 5;

        Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Employee> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);

        return "index";
    }
}
