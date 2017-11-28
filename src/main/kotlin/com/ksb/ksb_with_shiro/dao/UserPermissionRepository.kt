package com.ksb.ksb_with_shiro.dao

import com.ksb.ksb_with_shiro.entity.UserPermission
import org.springframework.data.jpa.repository.JpaRepository

interface UserPermissionRepository : JpaRepository<UserPermission, Long> {

}
