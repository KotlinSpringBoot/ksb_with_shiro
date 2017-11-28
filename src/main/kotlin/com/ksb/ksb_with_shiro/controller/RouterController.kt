package com.ksb.ksb_with_shiro.controller

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authz.AuthorizationException
import org.apache.shiro.authz.annotation.Logical
import org.apache.shiro.authz.annotation.RequiresAuthentication
import org.apache.shiro.authz.annotation.RequiresRoles
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest


@Controller
class RouterController {
    @RequestMapping("login")
    fun login(request: HttpServletRequest): String {
        return "login"
    }

    @RequestMapping("logout")
    fun logout(request: HttpServletRequest): String {
        SecurityUtils.getSubject().logout()
        return "redirect:/login"
    }

    @GetMapping(value = *arrayOf("", "/", "/index"))
    fun index(): String {
        return "/index"
    }


    @GetMapping("/main")
    fun main(): String {
        return "/main"
    }


    @GetMapping(value = *arrayOf("/admin"))
    fun admin(): String {
        return "/admin"
    }

    @GetMapping(value = *arrayOf("/user"))
    @RequiresAuthentication
    @RequiresRoles(value = *arrayOf("ROLE_ADMIN", "ROLE_USER"), logical = Logical.OR)
    @Throws(AuthorizationException::class)
    fun user(): String {
        return "/user"
    }

    @GetMapping(value = *arrayOf("/403"))
    fun error403(): String {
        return "/403"
    }

}
