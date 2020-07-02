package com.example.employeebe.controller

import com.example.employeebe.model.EmployeeState
import com.example.employeebe.model.RegisterDto
import com.example.employeebe.model.Role
import com.example.employeebe.repository.EmpMongoRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/emp")
@RestController
class EmpController {

    @Autowired
    private lateinit var empRepo:EmpMongoRepo

@CrossOrigin
@PostMapping("/register")
fun registerEmp(@RequestBody registerDto: RegisterDto):EmployeeState =

        empRepo.save(EmployeeState(registerDto.id, registerDto.empFullName,
                registerDto.emailId,registerDto.password,
        Role.User))


    @CrossOrigin
    @GetMapping("/getEmployee/{empId}")
    fun findById(@PathVariable("empId") empId:String):ResponseEntity<*> {

    return  empRepo.findById(empId).map {
        ResponseEntity.ok(it)
    }.orElse(ResponseEntity.notFound().build())
    }

    @CrossOrigin
    @GetMapping("/getEmps")
    fun findAllEmps():ResponseEntity<*> {

        return  ResponseEntity.ok(empRepo.findAll().filter { it.role.compareTo(Role.User)==0 })
        }
    }

