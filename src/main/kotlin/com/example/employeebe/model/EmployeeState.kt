package com.example.employeebe.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class EmployeeState (

    @Id
    var id :String="",
    var empFullName :String?=null,
    var emailId:String?=null,
    var password:String?=null,
    var role: Role

)
