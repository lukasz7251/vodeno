package com.vodeno.recruitmenttask.deposit.service

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import com.vodeno.recruitmenttask.account.db.repository.AccountRepository
import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import com.vodeno.recruitmenttask.user.db.repository.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DepositServiceImplTest {

    @Autowired
    lateinit var depositService: DepositService

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var userRepository: UserRepository

    private val testUserEmail = "test@example.com"

    private var testAccountNumber: Int? = null

    @Before
    fun setUp() {
        val user = UserEntity()
        user.email = testUserEmail
        userRepository.save(user)

        val account = AccountEntity()
        account.user = user
        testAccountNumber = accountRepository.save(account).id
    }

    @Test
    fun depositMoney() {
        val amount = BigDecimal("999.99")
        depositService.depositMoney(amount, testAccountNumber!!)
        assertEquals(amount, accountRepository.findById(testAccountNumber!!).get().balance)
    }

    @Test(expected = IllegalArgumentException::class)
    fun negativeAmount() {
        depositService.depositMoney(BigDecimal("-999.99"), testAccountNumber!!)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invalidAccountNumber() {
        depositService.depositMoney(BigDecimal("1.00"), 99999)
    }

}