package com.ksb.ksb_with_shiro.dao

import com.ksb.ksb_with_shiro.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository

interface PermissionRepository : JpaRepository<Permission, Long> {
}
