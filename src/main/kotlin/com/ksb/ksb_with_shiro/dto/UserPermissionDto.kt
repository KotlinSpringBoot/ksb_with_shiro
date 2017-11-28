package com.ksb.ksb_with_shiro.dto

class UserPermissionDto(
    var userId: Long,
    var permissionId: Long,
    var username: String,
    var permissionUrlPattern: String,
    var permissionValue: String,
    var permissionOrder:Int
    )
