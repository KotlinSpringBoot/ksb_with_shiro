package com.ksb.ksb_with_shiro.dao

import com.ksb.ksb_with_shiro.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository : JpaRepository<UserRole, Long> {


}

