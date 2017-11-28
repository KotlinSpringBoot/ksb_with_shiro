package com.ksb.ksb_with_shiro

import com.ksb.ksb_with_shiro.dao.*
import com.ksb.ksb_with_shiro.entity.*
import com.ksb.ksb_with_shiro.util.MD5Util
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.support.beans

@SpringBootApplication
class KsbWithShiroApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder().initializers(
            beans {
                bean {
                    ApplicationRunner {
                        try {
                            val PermissionRepository = ref<PermissionRepository>()
                            val UserPermissionRepository = ref<UserPermissionRepository>()
                            val RoleRepository = ref<RoleRepository>()
                            val UserRepository = ref<UserRepository>()
                            val UserRoleRepository = ref<UserRoleRepository>()

                            initShiroData(UserRoleRepository, UserPermissionRepository, UserRepository, RoleRepository, PermissionRepository)


                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
    )
        .sources(KsbWithShiroApplication::class.java)
        .run(*args)
}


@Throws(Exception::class)
private fun initShiroData(UserRoleRepository: UserRoleRepository, UserPermissionRepository: UserPermissionRepository, UserRepository: UserRepository, RoleRepository: RoleRepository, PermissionRepository: PermissionRepository) {
    UserRoleRepository.deleteAll()
    UserPermissionRepository.deleteAll()


    // 方便测试，初始化 User, Role, Permission 权限数据
    val user = User()
    user.id = 1
    user.username = "admin"
    user.password = MD5Util.md5("admin")
    UserRepository.save(user)

    user.id = 2
    user.username = "jack"
    user.password = MD5Util.md5("123456")
    UserRepository.save(user)

    val role = Role()
    role.id = 10
    role.name = "ROLE_ADMIN"
    role.description = "超级管理员"
    RoleRepository.save(role)

    role.id = 11
    role.name = "ROLE_MANAGER"
    role.description = "管理员"
    RoleRepository.save(role)

    role.id = 12
    role.name = "ROLE_USER"
    role.description = "普通用户"
    RoleRepository.save(role)

    /** 添加 UserRole 关联关系 */
    val userRole = UserRole()
    userRole.userId = 1

    userRole.roleId = 10
    UserRoleRepository.save(userRole)
    userRole.roleId = 11
    UserRoleRepository.save(userRole)
    userRole.roleId = 12
    UserRoleRepository.save(userRole)

    userRole.userId = 2
    userRole.roleId = 12
    UserRoleRepository.save(userRole)


    val permission = Permission()
    permission.id = 100
    permission.urlPattern = "/**/*.jpg"
    permission.value = "anon"
    permission.permOrder = 1
    PermissionRepository.save(permission)

    permission.id = 101
    permission.urlPattern = "/**/*.png"
    permission.value = "anon"
    permission.permOrder = 2
    PermissionRepository.save(permission)

    permission.id = 102
    permission.urlPattern = "/**/*.gif"
    permission.value = "anon"
    permission.permOrder = 3
    PermissionRepository.save(permission)

    permission.id = 103
    permission.urlPattern = "/**/*.bmp"
    permission.value = "anon"
    permission.permOrder = 4
    PermissionRepository.save(permission)

    permission.id = 104
    permission.urlPattern = "/**/*.js"
    permission.value = "anon"
    permission.permOrder = 5
    PermissionRepository.save(permission)

    permission.id = 105
    permission.urlPattern = "/**/*.css"
    permission.value = "anon"
    permission.permOrder = 6
    PermissionRepository.save(permission)

    permission.id = 106
    permission.urlPattern = "/static/**"
    permission.value = "anon"
    permission.permOrder = 7
    PermissionRepository.save(permission)

    permission.id = 107
    permission.urlPattern = "/**/*.ttf"
    permission.value = "anon"
    permission.permOrder = 8
    PermissionRepository.save(permission)

    permission.id = 108
    permission.urlPattern = "/**/*.woff"
    permission.value = "anon"
    permission.permOrder = 9
    PermissionRepository.save(permission)

    permission.id = 109
    permission.urlPattern = "/**/*.woff2"
    permission.value = "anon"
    permission.permOrder = 10
    PermissionRepository.save(permission)


    //用户为 ROLE_USER 角色可以访问。由用户角色 Role 作访问控制
    // authc 表示需要认证(登录)才能使用，没有参数
    // 表示用户必需已通过认证,并拥有 ROLE_ADMIN 角色才可以正常发起 '/admin' 请求
    permission.id = 110
    permission.urlPattern = "/admin/**"
    permission.value = "authc,roles[ROLE_ADMIN]"
    permission.permOrder = 11
    PermissionRepository.save(permission)

    // roles[],参数可以写多个，多个时必须加上引号
    permission.id = 111
    permission.urlPattern = "/main/**"
    permission.value = "authc,roles[ROLE_USER]"
    permission.permOrder = 12
    PermissionRepository.save(permission)

    // anno代表不需要授权即可访问，对于静态资源，访问权限都设置为anno
    permission.id = 112
    permission.urlPattern = "/**"
    permission.value = "anon"
    permission.permOrder = 13
    PermissionRepository.save(permission)

    /** 添加 UserPermission 关联关系 */
    for (permissionId in 100L..112) {
        val UserPermission = UserPermission()
        UserPermission.userId = 1
        UserPermission.permissionId = permissionId
        UserPermissionRepository.save(UserPermission)

        // userId = 2 的用户不配置 permissionId = 110 这个权限： "authc,roles[ROLE_ADMIN]"
        if (permissionId.compareTo(110) != 0) {
            val UserPermission2 = UserPermission()
            UserPermission2.userId = 2
            UserPermission2.permissionId = permissionId
            UserPermissionRepository.save(UserPermission2)
        }

    }

}
