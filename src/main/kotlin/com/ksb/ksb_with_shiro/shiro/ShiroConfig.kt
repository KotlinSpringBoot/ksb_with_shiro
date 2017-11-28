package com.ksb.ksb_with_shiro.shiro

import com.ksb.ksb_with_shiro.dao.PermissionRepository
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.authc.LogoutFilter
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import java.util.*
import javax.servlet.Filter


@Configuration
class ShiroConfig {
    val log = LoggerFactory.getLogger(this.javaClass)

    @Autowired lateinit var permissionRepository: PermissionRepository
    @Autowired lateinit var shiroRealm: ShiroRealm


    /**
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
     * 防止密码在数据库里明码保存，当然在登陆认证的时候，
     * 这个类也负责对form里输入的密码进行编码。
     */
    @Bean(name = arrayOf("hashedCredentialsMatcher"))
    fun hashedCredentialsMatcher(): HashedCredentialsMatcher {
        val credentialsMatcher = HashedCredentialsMatcher()
        credentialsMatcher.hashAlgorithmName = "MD5"
        credentialsMatcher.hashIterations = 2
        credentialsMatcher.isStoredCredentialsHexEncoded = true
        return credentialsMatcher
    }


    /**
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     */
    @Bean(name = arrayOf("securityManager"))
    fun securityManager(): DefaultWebSecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(shiroRealm)
        return securityManager
    }

    /**
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     */
    @Bean(name = arrayOf("shiroFilter"))
    fun shiroFilterFactoryBean(): ShiroFilterFactoryBean {
        val shiroFilterFactoryBean = ShiroFilterFactoryBean()
        shiroFilterFactoryBean.securityManager = securityManager()

        val filters = LinkedHashMap<String, Filter>()
        val logoutFilter = LogoutFilter()

        logoutFilter.redirectUrl = "/login"
        shiroFilterFactoryBean.filters = filters
        val filterChainDefinitionManager = LinkedHashMap<String, String>()

        // 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
        filterChainDefinitionManager.put("/logout", "logout")

        try {
            println("permissionRepository.findAll() = ${permissionRepository.findAll()}")
            permissionRepository.findAll().forEach {
                println("permissionRepository ===> ${it.urlPattern}, ${it.value}")
                log.info("filterChainDefinitionManager.put ===>${it.urlPattern}, ${it.value}")
                filterChainDefinitionManager.put(it.urlPattern, it.value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("filterChainDefinitionManager ===> ${filterChainDefinitionManager}")

        shiroFilterFactoryBean.filterChainDefinitionMap = filterChainDefinitionManager

        shiroFilterFactoryBean.loginUrl = "/login"
        shiroFilterFactoryBean.successUrl = "/"
        shiroFilterFactoryBean.unauthorizedUrl = "/403"
        return shiroFilterFactoryBean
    }


    // Spring Boot 开启Shiro注解支持， 这样就可以在方法上面使用 Shiro 注解
    @Bean
    @DependsOn("securityManager")
    fun authorizationAttributeSourceAdvisor(securityManager: org.apache.shiro.mgt.SecurityManager): AuthorizationAttributeSourceAdvisor {
        val authorizationAttributeSourceAdvisor = AuthorizationAttributeSourceAdvisor()
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager())
        return authorizationAttributeSourceAdvisor
    }

    @Bean
    @ConditionalOnMissingBean
    fun defaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val defaultAAP = DefaultAdvisorAutoProxyCreator()
        defaultAAP.isProxyTargetClass = true
        return defaultAAP
    }

}
