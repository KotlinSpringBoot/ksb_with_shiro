package com.ksb.ksb_with_shiro.dao

import com.ksb.ksb_with_shiro.dto.UserRoleDto
import com.ksb.ksb_with_shiro.entity.Role
import com.ksb.ksb_with_shiro.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {


}
