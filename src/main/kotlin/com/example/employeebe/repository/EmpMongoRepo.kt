package com.example.employeebe.repository

import com.example.employeebe.model.EmployeeState
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface EmpMongoRepo:MongoRepository<EmployeeState,String> {

    fun findByEmailId(emailId: String?):Optional<EmployeeState>
}
