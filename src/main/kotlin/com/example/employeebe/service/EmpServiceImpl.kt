package com.example.employeebe.service

import com.example.employeebe.config.ResponseWithError
import com.example.employeebe.model.EmployeeState
import com.example.employeebe.model.LoginDto
import com.example.employeebe.model.RegisterDto
import com.example.employeebe.model.Role
import com.example.employeebe.repository.EmpMongoRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
@Service
class EmpServiceImpl:IEmpService {

    @Autowired
    private lateinit var empRepo: EmpMongoRepo

    override fun createEmp(registerDto: RegisterDto): ResponseWithError<*> {

        val empOptnl = empRepo.findByEmailId(registerDto.emailId?:"")
        if ( empOptnl.emailId==null){
            empRepo.save(EmployeeState(registerDto.id, registerDto.empFullName,
                    registerDto.emailId,registerDto.password,
                    Role.User))

            return ResponseWithError.of("Register successfully")
        }
        return ResponseWithError.ofError<String>("Email Id already registered")

    }

    override fun getById(id: String): ResponseWithError<*> {

      val empOptional =  empRepo.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
        return ResponseWithError.of(empOptional)
    }

    override fun getByEmailId(emailId: String): ResponseWithError<*> {

        return try {
            val emp = empRepo.findByEmailId(emailId)
            ResponseWithError.of(emp);
        }catch (e:Exception){
            ResponseWithError.ofError<String>("Email Id not found")
        }
    }

    override fun getAllEmps(): ResponseWithError<*> {
        val allEmps = empRepo.findAll()
        return ResponseWithError.of(allEmps)
    }

    override fun loginEmp(loginDto: LoginDto): ResponseWithError<*> {
        val emp = empRepo.findByEmailId(loginDto.userNameOrEmailId?.trim()?:"")
        if(emp.emailId!!.isNotBlank() && emp.password!!.equals(loginDto.password?.trim()) ){

            return ResponseWithError.of("Login Success !")
        }
        return ResponseWithError.ofError<String>("Invalid email id or password entered")

    }

}
