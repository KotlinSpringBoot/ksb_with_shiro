package com.ksb.ksb_with_shiro.controller

import com.ksb.ksb_with_shiro.util.MD5Util
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.UsernamePasswordToken
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam


@Controller
class LoginController {

    @RequestMapping(value = "/doLogin", method = arrayOf(RequestMethod.POST, RequestMethod.GET))
    fun login(@RequestParam(value = "username", required = true) userName: String,
              @RequestParam(value = "password", required = true) password: String,
              @RequestParam(value = "rememberMe", required = true, defaultValue = "off") rememberMe: String
    ): String {

        val subject = SecurityUtils.getSubject()

        // 需要注意的是这地方的 md5 加密，因为我们数据库中存的就是 md5 加密后的字符串
        val token = UsernamePasswordToken(userName, MD5Util.md5(password))
        token.isRememberMe = rememberMe != "off"
        try {
            subject.login(token)
        } catch (e: AuthenticationException) {
            e.printStackTrace()
            return "/login"
        }

        return "/main"
    }


}
