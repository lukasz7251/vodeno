package com.vodeno.recruitmenttask.account.controller

import com.vodeno.recruitmenttask.account.service.AccountService
import com.vodeno.recruitmenttask.deposit.service.DepositService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@RestController
@RequestMapping("/accounts")
class AccountController(
        private val accountService: AccountService,
        private val depositService: DepositService
) {

    @RequestMapping(method = [RequestMethod.POST])
    fun create(@RequestBody request: CreateAccountRequest): Account {
        return accountService.create(email = request.email)
    }

    @RequestMapping(method = [RequestMethod.GET])
    fun findAll(): List<Account> {
        return accountService.findAll()
    }

    @RequestMapping("{accountNumber}", method = [RequestMethod.GET])
    fun findByAccountNumber(@PathVariable("accountNumber") accountNumber: Int): Account {
        return accountService.findByAccountNumber(accountNumber = accountNumber)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @RequestMapping("{accountNumber}/balance", method = [RequestMethod.GET])
    fun findBalanceByAccountNumber(@PathVariable("accountNumber") accountNumber: Int): BigDecimal {
        return accountService.findByAccountNumber(accountNumber = accountNumber)?.balance
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @RequestMapping("{accountNumber}/deposit/{amount}", method = [RequestMethod.PUT])
    fun depositFunds(@PathVariable("accountNumber") accountNumber: Int, @PathVariable("amount") amount: BigDecimal) {
        depositService.depositMoney(accountNumber = accountNumber, amount = amount)
    }

}

data class CreateAccountRequest(
        val email: String
)

data class Account(
        val number: String,
        val email: String,
        val balance: BigDecimal
)