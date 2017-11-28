package com.ksb.ksb_with_shiro.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class UserRole {

    var userId: Long = -1
    var roleId: Long = -1

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtCreate = Date()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtModified = Date()


}
