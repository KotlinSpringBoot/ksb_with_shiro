package com.ksb.ksb_with_shiro.entity

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*
import javax.persistence.*

@Entity
class Role {

    @Column(unique = true,length = 100)
    lateinit var name: String
    lateinit var description: String
    var level: Int = -1
    @Id
    var id: Long = -1
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtCreate = Date()
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var gmtModified = Date()


}
