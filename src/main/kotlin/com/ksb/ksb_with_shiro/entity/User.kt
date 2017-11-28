package com.ksb.ksb_with_shiro.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity
class User {

    @Column(unique = true,length = 50)
    lateinit var username: String
    @JsonIgnore
    lateinit var password: String
    @Id
    var id: Long = -1
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtCreate = Date()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtModified = Date()
}
