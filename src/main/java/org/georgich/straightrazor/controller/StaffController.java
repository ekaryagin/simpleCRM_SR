package org.georgich.straightrazor.controller;

import org.georgich.straightrazor.domain.Employee;
import org.georgich.straightrazor.domain.User;
import org.georgich.straightrazor.storages.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class StaffController {

    @Autowired
    public EmployeeRepository employeeRepo;

    @GetMapping("/staff")
    public String staff(@AuthenticationPrincipal User user, Map<String, Object> model) {

        Iterable<Employee> staff = employeeRepo.findAllByAuthor(user);
        model.put("staff", staff);

        return "staff";
    }

    // adding an employee
    @PostMapping("/staff")
    public String staffAdd(@AuthenticationPrincipal User user,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam String position, Map<String, Object> model) {

        Employee employee = new Employee(name, surname, position, user);
        employeeRepo.saveAndFlush(employee);

        Iterable<Employee> staff = employeeRepo.findAllByAuthor(user);
        model.put("staff", staff);

        return "staff";
    }
}
