package com.vodeno.recruitmenttask.account.service

import com.vodeno.recruitmenttask.account.controller.Account
import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import com.vodeno.recruitmenttask.account.db.repository.AccountRepository
import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import com.vodeno.recruitmenttask.user.db.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AccountServiceImpl(
        private val accountRepository: AccountRepository,
        private val userRepository: UserRepository
) : AccountService {

    override fun create(email: String): Account {
        val userEntity = getOrCreateUser(email)
        val accountEntity = AccountEntity().also { it.user = userEntity }
        return accountRepository.save(accountEntity).toDomain()
    }

    override fun findAll(): List<Account> {
        return accountRepository.findAll().map { it.toDomain() }
    }

    override fun findByAccountNumber(accountNumber: Int): Account? {
        return accountRepository.findByIdOrNull(accountNumber)?.toDomain()
    }

    private fun getOrCreateUser(email: String): UserEntity {
        val optional = userRepository.findByEmail(email)
        return optional ?: userRepository.save(UserEntity().also { it.email = email })
    }

}

private fun AccountEntity.toDomain(): Account = Account(
        email = user.email,
        number = "$id",
        balance = balance
)