package com.vodeno.recruitmenttask.deposit.service

import com.vodeno.recruitmenttask.account.db.repository.AccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class DepositServiceImpl(
        private val accountRepository: AccountRepository
) : DepositService {

    override fun depositMoney(amount: BigDecimal, accountNumber: Int) {
        require(amount > BigDecimal.ZERO) { "Amount must greater than zero" }
        require(accountNumber > 0) { "Invalid account number" }
        val account = accountRepository.findByIdOrNull(accountNumber)
                ?: throw IllegalArgumentException("Account $accountNumber not found")
        accountRepository.save(account.also { it.balance += amount })
    }

}