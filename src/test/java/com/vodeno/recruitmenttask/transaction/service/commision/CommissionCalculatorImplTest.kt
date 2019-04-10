package com.vodeno.recruitmenttask.transaction.service.commision

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import com.vodeno.recruitmenttask.account.db.repository.AccountRepository
import com.vodeno.recruitmenttask.transaction.db.entity.TransactionEntity
import com.vodeno.recruitmenttask.transaction.db.repository.TransactionRepository
import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import com.vodeno.recruitmenttask.user.db.repository.UserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.*

@RunWith(SpringRunner::class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CommissionCalculatorImplTest {

    @Autowired
    lateinit var commissionCalculator: CommissionCalculator

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Autowired
    lateinit var userRepository: UserRepository

    private var accountOne: Int? = null
    private var accountTwo: Int? = null
    private var accountThree: Int? = null

    @Before
    @Transactional
    fun setUp() {
        val user1 = UserEntity().let {
            it.email = "twoaccounts@example.com"
            it.createDate = Date(2016, 10, 15)
            return@let userRepository.save(it)
        }

        val account1 = AccountEntity().let {
            it.user = user1
            val saved = accountRepository.save(it)
            accountOne = saved.id
            return@let saved
        }

        val account2 = AccountEntity().let {
            it.user = user1
            val saved = accountRepository.save(it)
            accountTwo = saved.id
            return@let saved
        }

        val user2 = UserEntity().let {
            it.email = "oneaccount@example.com"
            it.createDate = Date(2018, 1, 1)
            return@let userRepository.save(it)
        }

        val account3 = AccountEntity().let {
            it.user = user2
            val saved = accountRepository.save(it)
            accountThree = saved.id
            return@let saved
        }

        // Free internal transfers
        repeat(5) {
            TransactionEntity().let {
                it.sender = account1
                it.receiver = account2
                it.amount = BigDecimal.TEN
                it.commission = BigDecimal.ZERO
                transactionRepository.save(it)
            }
        }
        // Free external transfers
        repeat(5) {
            TransactionEntity().let {
                it.sender = account1
                it.receiver = account3
                it.amount = BigDecimal.TEN
                it.commission = BigDecimal.ZERO
                transactionRepository.save(it)
            }
        }
        // Apid external transfers
        repeat(5) {
            TransactionEntity().let {
                it.sender = account3
                it.receiver = account2
                it.amount = BigDecimal.TEN
                it.commission = BigDecimal.ONE
                transactionRepository.save(it)
            }
        }
    }

    @Test
    fun sameUser() {
        val commission = commissionCalculator.calculate(
                amount = BigDecimal.TEN,
                sender = accountRepository.findByIdOrNull(accountOne)!!,
                receiver = accountRepository.findByIdOrNull(accountTwo)!!
        )
        Assert.assertEquals(BigDecimal("0.00"), commission)
    }

    @Test
    fun differentUserWithFreeTransfers() {
        val commission = commissionCalculator.calculate(
                amount = BigDecimal.TEN,
                sender = accountRepository.findByIdOrNull(accountThree)!!,
                receiver = accountRepository.findByIdOrNull(accountTwo)!!
        )
        Assert.assertEquals(BigDecimal("0.00"), commission)
    }

    @Test
    fun differentUserWithoutFreeTransfers() {
        val commission = commissionCalculator.calculate(
                amount = BigDecimal("100.00"),
                sender = accountRepository.findByIdOrNull(accountOne)!!,
                receiver = accountRepository.findByIdOrNull(accountThree)!!
        )
        Assert.assertEquals(BigDecimal("2.00"), commission)
    }

}