package com.ksb.ksb_with_shiro.dao

import com.ksb.ksb_with_shiro.dto.UserPermissionDto
import com.ksb.ksb_with_shiro.dto.UserRoleDto
import com.ksb.ksb_with_shiro.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository : JpaRepository<User, Long> {

    @Query("select u from User u where u.username = ?1")
    fun findByUserName(username: String): User

    @Query("""
        select

        new com.ksb.ksb_with_shiro.dto.UserRoleDto(
        ur.userId,
        ur.roleId,
        u.username,
        u.password,
        r.name,
        r.level
        )

        from User u
        left join UserRole ur on u.id = ur.userId
        left join Role     r  on r.id = ur.roleId
        where     u.id = ?1
        """)
    fun findUserRoles(userId: Long): List<UserRoleDto>


    @Query("""
        select

        new com.ksb.ksb_with_shiro.dto.UserPermissionDto(
        u.id,
        p.id,
        u.username,
        p.urlPattern,
        p.value,
        p.permOrder
        )

        from User u
        left join UserPermission up on up.userId = u.id
        left join Permission p on p.id = up.permissionId
        where u.id = ?1 order by p.permOrder
    """)
    fun findUserPermissions(userId: Long): List<UserPermissionDto>
}
