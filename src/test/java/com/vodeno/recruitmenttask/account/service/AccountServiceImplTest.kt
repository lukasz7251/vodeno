package com.vodeno.recruitmenttask.account.service

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AccountServiceImplTest {

    @Autowired
    lateinit var accountService: AccountService

    @Test
    fun create() {
        // GIVEN
        val email = "email@example.com"
        // WHEN
        val account = accountService.create(email)
        assertEquals(email, account.email)
    }

}