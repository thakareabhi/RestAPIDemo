package com.example.RestAPIDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@SpringBootApplication
public class RestApiDemoApplication {

    @Autowired
    private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestApiDemoApplication.class, args);
    }

    @GetMapping("/getAllEmployee")
    public List<Employee> getAllEmployee()  {
        return employeeRepository.findAll();
    }

    @GetMapping("/getEmployeeByID/{id}")
    public Optional<Employee> getAllEmployee(@PathVariable Integer id)  {
        return employeeRepository.findById(id);
    }

    @GetMapping("/getEmployee/{name}/{sal}")
    public List<Employee> getAllEmployee(@PathVariable("name") String name,@PathVariable("sal") Integer salary)  {
        System.out.println(name);
        System.out.println(salary);
        return employeeRepository.findAll().stream().filter(employee -> (name.equals(employee.getName()) && salary==employee.getSalary() )).toList();
    }

    @RequestMapping(value = {"/getEmployeeByIDNew/{id}","/getEmployeeByIDNew"})
    public List<Employee> getEmployee(@PathVariable(value = "id",required = false) Integer idv) {
        if (idv == null) {
            return employeeRepository.findAll();
        } else {
            return employeeRepository.findById(idv).stream().toList();
        }
    }
    @GetMapping("/getEmployeeByIDRequestParam")
    public Optional<Employee> getAllEmployeeUsingReqParm(@RequestParam(value = "idv",required = true) Integer id)  {
        return employeeRepository.findById(id);
    }

    @PostMapping("/addEmployee")
    public Employee getAllEmployee(@RequestBody Employee newEmployee)  {
        return employeeRepository.save(newEmployee);
    }

    @PutMapping("/updateEmployee/{id}")
    public Employee getAllEmployee(@RequestBody Employee newEmployee, @PathVariable Integer id)  {
        return employeeRepository.findById(id).map(
            employee-> {
                employee.setName(newEmployee.getName());
                employee.setDept(newEmployee.getDept());
                employee.setSalary(newEmployee.getSalary());
                return employeeRepository.save(employee);
            }).orElseGet(() -> {
            System.out.println("Error");
            return null;
        });
    }

    @DeleteMapping("/deleteEmployee")
    private void deleteEmployee(@RequestParam(value = "idd") Integer id){
        employeeRepository.deleteById(id);
    }


    @DeleteMapping("/deleteEmployee/{id}")
    private void deleteEmployeePv(@PathVariable Integer id){
        employeeRepository.deleteById(id);
    }

    @DeleteMapping("/deleteAllEmployee")
    private void deleteAllEmployee(){
        employeeRepository.deleteAll();
    }
}