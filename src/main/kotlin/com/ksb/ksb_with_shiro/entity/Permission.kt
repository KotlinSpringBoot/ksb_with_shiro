package com.ksb.ksb_with_shiro.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Permission {

    lateinit var name: String
    @Column(unique = true, length = 100)
    lateinit var urlPattern: String
    lateinit var value: String
    var permOrder: Int = -1

    @Id
    var id: Long = -1
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtCreate = Date()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtModified = Date()

}
