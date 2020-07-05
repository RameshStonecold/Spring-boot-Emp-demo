package com.example.employeebe.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class EmployeeState (

    @Id
    var id :String="",
    var empFullName :String?="",
    var emailId:String?="",
    var password:String?="",
    var role: Role

)
