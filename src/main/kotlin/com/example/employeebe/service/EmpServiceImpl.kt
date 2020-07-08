package com.example.employeebe.service

import com.example.employeebe.config.LoginTokenDetails
import com.example.employeebe.config.ResponseWithError
import com.example.employeebe.config.tokenprovider.JwtConfig
import com.example.employeebe.config.tokenprovider.TokenHelper
import com.example.employeebe.model.EmployeeState
import com.example.employeebe.model.LoginDto
import com.example.employeebe.model.RegisterDto
import com.example.employeebe.model.Role
import com.example.employeebe.repository.EmpMongoRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
@Service
class EmpServiceImpl:IEmpService {

    @Autowired
    private lateinit var empRepo: EmpMongoRepo

    @Autowired
    private lateinit var tokenHelper : TokenHelper

    @Autowired
    private lateinit var jwtConfig: JwtConfig

    override fun createEmp(registerDto: RegisterDto): ResponseWithError<*> {

        try {
            val empOptnl = empRepo.findByEmailId(registerDto.emailId ?: "")
            if (empOptnl.isPresent) {
                return ResponseWithError.ofError<String>("Employee already registered with email id")
            }else{
                if(registerDto.empFullName.isNullOrBlank() || registerDto.emailId.isNullOrBlank()||
                        registerDto.password.isNullOrBlank()){
                    return ResponseWithError.ofError<String>("All fields are required")
                }
                empRepo.save(EmployeeState("", registerDto.empFullName,
                        registerDto.emailId, registerDto.password,
                        Role.User))
                return ResponseWithError.of("Employee registered successfully")
            }
        } catch (e: Exception) {
            return ResponseWithError.ofError<String>("Error{}")
        }
    }

    override fun getById(id: String): ResponseWithError<*> {

      val empOptional =  empRepo.findById(id).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())
        return ResponseWithError.of(empOptional)
    }

    override fun getByEmailId(emailId: String): ResponseWithError<*> {
            val empOptional = empRepo.findByEmailId(emailId)
           if (empOptional.isPresent){
            return  ResponseWithError.of(empOptional.get());
           }
          return ResponseWithError.ofError<String>("Email Id not found")
    }

    override fun getAllEmps(): ResponseWithError<*> {
        val allEmps = empRepo.findAll()
        return ResponseWithError.of(allEmps)
    }

    override fun loginEmp(loginDto: LoginDto): ResponseWithError<*> {
        val empOptnl = empRepo.findByEmailId(loginDto.userNameOrEmailId)

        if (empOptnl.isPresent && empOptnl.get().password.equals(loginDto.password)) {
            return ResponseWithError.of("Login Success !")
        } else {
            return ResponseWithError.ofError<String>("Invalid email id or password entered")
        }
    }

    override fun login(loginDto: LoginDto): ResponseWithError<*> {
        val empOptnl = empRepo.findByEmailId(loginDto.userNameOrEmailId)
        if (empOptnl.isPresent){
            /*val grantedAuthorities = AuthorityUtils
                        .createAuthorityList(*roles.toTypedArray())*/
            var token=tokenHelper.generateToken(loginDto.userNameOrEmailId, emptyList())
            return ResponseWithError.of(LoginTokenDetails(loginDto.userNameOrEmailId,token))
        } else {
            return ResponseWithError.ofError<String>("Invalid email id or password entered")
        }
    }
}
