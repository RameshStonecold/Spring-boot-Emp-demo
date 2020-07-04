package com.example.employeebe

import com.example.employeebe.controller.EmpController
import com.example.employeebe.model.EmployeeState
import com.example.employeebe.model.LoginDto
import com.example.employeebe.model.Role

import com.example.employeebe.repository.EmpMongoRepo
import com.example.employeebe.service.IEmpService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@SpringBootTest
class EmployeeIT {

    @Autowired
    lateinit var empRepo:EmpMongoRepo

    internal var empController :EmpController?=null

     lateinit var  empService:IEmpService

    @Test
    fun registerEmpTest(){

      // val emp = EmployeeState(UUID.randomUUID().toString(),"Ramesh","rameshshaka@gmail.com","ramesh123",Role.Super_Admin)
       val emp = EmployeeState(UUID.randomUUID().toString(),"Srilaxmi Angadi","srilaxmi742@gmail.com","srilaxmi22", Role.User)

        //val registerDto = RegisterDto(UUID.randomUUID().toString(),"Srilaxmi Angadi","srilaxmi742@gmail.com","srilaxmi22")

       // empController?.registerEmp(registerDto)
        empRepo.save(emp)

    }


    @Test
    fun loginTest(){

        val loginDto = LoginDto("srilaxmi742@gmail.com","srilaxmi22")

       val res = empService.loginEmp(loginDto)

        print(res)
    }
}
