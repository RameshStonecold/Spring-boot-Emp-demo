package com.example.employeebe.model

import org.springframework.data.annotation.Id

data class RegisterDto(

     var id :String="",
     var empFullName :String?=null,
     var emailId:String?=null,
     var password:String?=null

)
