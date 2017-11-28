package com.ksb.ksb_with_shiro.dto

class UserRoleDto(
    var userId: Long,
    var roleId: Long,
    var username: String,
    var password: String,
    var roleName: String,
    var roleLevel: Int)
