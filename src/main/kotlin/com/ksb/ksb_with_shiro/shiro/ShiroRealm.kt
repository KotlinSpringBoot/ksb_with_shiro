package com.ksb.ksb_with_shiro.shiro

import com.ksb.ksb_with_shiro.dao.UserRepository
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShiroRealm : AuthorizingRealm() {
    val logger = LoggerFactory.getLogger(this.javaClass)
    @Autowired
    lateinit var userRespository: UserRepository

    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(authenticationToken: AuthenticationToken): AuthenticationInfo? {
        logger.info("获取 Shiro权限认证信息 doGetAuthenticationInfo = $authenticationToken")

        val token = authenticationToken as UsernamePasswordToken
        val userName = token.username
        val user = userRespository.findByUserName(token.username)
        //设置用户session
        val session = SecurityUtils.getSubject().session
        session.setAttribute("user", user)
        return SimpleAuthenticationInfo(userName, user.password, name)
    }


    override fun doGetAuthorizationInfo(principalCollection: PrincipalCollection): AuthorizationInfo {
        logger.info("执行Shiro权限认证 doGetAuthorizationInfo = $principalCollection")

        val user = userRespository.findByUserName(principalCollection.primaryPrincipal as String)
        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().session.setAttribute(user.id.toString(), SecurityUtils.getSubject().principals)

        val simpleAuthorizationInfo = SimpleAuthorizationInfo()
        //赋予角色ROLE
        for (userRole in userRespository.findUserRoles(user.id)) {
            simpleAuthorizationInfo.addRole(userRole.roleName)
        }

        //赋予权限 Permission
        for (permission in userRespository.findUserPermissions(user.id)) {
            simpleAuthorizationInfo.addStringPermission(permission.permissionValue)
        }

        return simpleAuthorizationInfo
    }


}
