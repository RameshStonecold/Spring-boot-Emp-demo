package com.example.employeebe.controller

import com.example.employeebe.config.ResponseWithError
import com.example.employeebe.model.EmployeeState
import com.example.employeebe.model.LoginDto
import com.example.employeebe.model.RegisterDto
import com.example.employeebe.model.Role
import com.example.employeebe.repository.EmpMongoRepo
import com.example.employeebe.service.IEmpService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/emp")
@RestController
class EmpController {

    @Autowired
    private lateinit var empRepo: EmpMongoRepo


    @Autowired
    private lateinit var empService: IEmpService

    @CrossOrigin
    @PostMapping("/register")
    fun registerEmp(@RequestBody registerDto: RegisterDto): ResponseEntity<*> {

        try {
            val emp = empRepo.findByEmailId(registerDto.emailId ?: "")
            if (emp.emailId.isNullOrBlank()) {
                empRepo.save(EmployeeState(registerDto.id, registerDto.empFullName,
                        registerDto.emailId, registerDto.password,
                        Role.User))
                return ResponseEntity(ResponseWithError.of("Employee registered successfully"), HttpStatus.CREATED)
            }
            return ResponseEntity(ResponseWithError.ofError<String>("Employee already registered with email id"), HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            return ResponseEntity(ResponseWithError.ofError<String>("Error{}"), HttpStatus.BAD_REQUEST)
        }
    }


    @CrossOrigin
    @GetMapping("/getEmployee/{empId}")
    fun findById(@PathVariable("empId") empId:String):ResponseEntity<*> {

    return  empRepo.findById(empId).map {
        ResponseEntity.ok(ResponseWithError.of(it))
    }.orElse(ResponseEntity.notFound().build())
    }

    @CrossOrigin
    @GetMapping("/getEmps")
    fun findAllEmps():ResponseEntity<*> {

        return ResponseEntity(ResponseWithError.of(empRepo.findAll().filter { it.role.compareTo(Role.User)==0 }),
                HttpStatus.BAD_REQUEST)

        }

@GetMapping("/getEmpByEmailId/{emailId}")
fun findByEmailId(@PathVariable("emailId") emailId:String):ResponseEntity<*> {

    return try {
        val emp = empService.getByEmailId(emailId)
        ResponseEntity.ok(ResponseWithError.of(emp))
    }catch (e:Exception){
        ResponseEntity(ResponseWithError.ofError<String>("Email Id not found"),
                HttpStatus.BAD_REQUEST)
    }

 }

    @CrossOrigin
    @PostMapping("/login")
    fun createLoginCredentials(@RequestBody loginDto: LoginDto):ResponseEntity<*> {

        try {
            val result = empService.loginEmp(loginDto)
            if (result.isError) {
                return ResponseEntity(ResponseWithError.ofError<String>(result.errorMsg), HttpStatus.BAD_REQUEST)
            }
            return ResponseEntity(ResponseWithError.of(result.response), HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity(ResponseWithError.ofError<String>("Error{}"), HttpStatus.BAD_REQUEST)
        }

    }

}





