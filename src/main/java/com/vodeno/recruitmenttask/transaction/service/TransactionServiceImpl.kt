package com.vodeno.recruitmenttask.transaction.service

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import com.vodeno.recruitmenttask.account.db.repository.AccountRepository
import com.vodeno.recruitmenttask.transaction.db.entity.TransactionEntity
import com.vodeno.recruitmenttask.transaction.db.repository.TransactionRepository
import com.vodeno.recruitmenttask.transaction.service.commision.CommissionCalculator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional
class TransactionServiceImpl(
        private val accountRepository: AccountRepository,
        private val commissionCalculator: CommissionCalculator,
        private val transactionRepository: TransactionRepository
) : TransactionService {

    override fun transferMoney(amount: BigDecimal, senderAccountNumber: Int, receiverAccountNumber: Int) {
        validateArguments(amount, senderAccountNumber, receiverAccountNumber)
        val (sender, receiver) = findAccounts(senderNumber = senderAccountNumber, receiverNumber = receiverAccountNumber)
        val commision = commissionCalculator.calculate(amount = amount, sender = sender, receiver = receiver)
        checkFunds(sender = sender, amount = amount, commision = commision)
        updateAccountBalances(sender = sender, amount = amount, commision = commision, receiver = receiver)
        saveTransaction(amount, commision, sender, receiver)
    }

    private fun validateArguments(amount: BigDecimal, senderNumber: Int, receiverNumber: Int) {
        require(amount > BigDecimal.ZERO) { "Amount must greater than zero" }
        require(amount.stripTrailingZeros().scale() <= 2) { "Maximum number of fractional digits is 2" }
        require(senderNumber > 0) { "Invalid account number" }
        require(receiverNumber > 0) { "Invalid account number" }
    }

    private fun findAccounts(senderNumber: Int, receiverNumber: Int): Pair<AccountEntity, AccountEntity> {
        val sender = accountRepository.findByIdOrNull(senderNumber)
                ?: throw IllegalArgumentException("Account $senderNumber not found")
        val receiver = accountRepository.findByIdOrNull(receiverNumber)
                ?: throw IllegalArgumentException("Account $senderNumber not found")
        return sender to receiver
    }

    private fun checkFunds(sender: AccountEntity, amount: BigDecimal, commision: BigDecimal) {
        check(sender.balance - amount - commision > BigDecimal.ZERO) { "Insufficient funds" }
    }

    private fun updateAccountBalances(sender: AccountEntity, amount: BigDecimal, commision: BigDecimal, receiver: AccountEntity) {
        sender.balance -= amount + commision
        receiver.balance += amount
        accountRepository.saveAll(listOf(sender, receiver))
    }

    private fun saveTransaction(amount: BigDecimal, commision: BigDecimal, sender: AccountEntity, receiver: AccountEntity) {
        val transfer = TransactionEntity().also {
            it.amount = amount
            it.commission = commision
            it.sender = sender
            it.receiver = receiver
        }
        transactionRepository.save(transfer)
    }

}