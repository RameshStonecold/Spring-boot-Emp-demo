package com.example.employeebe.repository

import com.example.employeebe.model.EmployeeState
import org.springframework.data.mongodb.repository.MongoRepository

interface EmpMongoRepo:MongoRepository<EmployeeState,String> {
}
