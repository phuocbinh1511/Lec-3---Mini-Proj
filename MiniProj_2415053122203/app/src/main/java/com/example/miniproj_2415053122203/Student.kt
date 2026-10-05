package com.example.miniproj_2415053122203

import java.io.Serializable

data class Student(
    val id: String,
    var name: String,
    var className: String,
    var gpa: Double
) : Serializable