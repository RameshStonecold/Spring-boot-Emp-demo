package com.example.employeebe.service

import com.example.employeebe.config.ResponseWithError
import com.example.employeebe.model.LoginDto
import com.example.employeebe.model.RegisterDto


interface IEmpService {

    fun createEmp(registerDto: RegisterDto):ResponseWithError<*>

    fun getById(id:String):ResponseWithError<*>

    fun getByEmailId(emailId:String):ResponseWithError<*>

    fun getAllEmps():ResponseWithError<*>

    fun loginEmp(loginDto: LoginDto):ResponseWithError<*>



}
