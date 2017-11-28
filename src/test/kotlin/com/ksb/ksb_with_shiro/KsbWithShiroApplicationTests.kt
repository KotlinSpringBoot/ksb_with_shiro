package com.ksb.ksb_with_shiro

import com.ksb.ksb_with_shiro.dao.PermissionRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class KsbWithShiroApplicationTests {
    @Autowired lateinit var permissionRepository: PermissionRepository

    @Test
    fun testPermissionRepositoryFindAll() {
        println(permissionRepository.findAll())
    }

}
