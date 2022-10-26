package com.example.kotlindemo.controller

import com.example.kotlindemo.model.Employee
import com.example.kotlindemo.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("employee")
class EmployeeController @Autowired constructor(
    val employeeService: EmployeeService
) {

    @GetMapping
    fun getTitle() : String{
        return "user contoller";
    }

    @PostMapping
    fun addEmployee(@RequestBody employee: Employee) : ResponseEntity<Any>{
        return ResponseEntity.ok(employeeService.addUser(employee));
    }


    @GetMapping("by-first-name")
    fun getEmployeeByFirstName(@RequestParam value : String) : ResponseEntity<Any>{
        return ResponseEntity.ok(employeeService.getEmpByName(value));
    }

    @GetMapping("async")
    fun printAsync() : ResponseEntity<Any>{
        return ResponseEntity.ok(employeeService.tryCoroutines());
    }

}
