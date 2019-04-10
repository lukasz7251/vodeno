package com.vodeno.recruitmenttask.account.service

import com.vodeno.recruitmenttask.account.controller.Account

interface AccountService {

    fun create(email: String): Account
    fun findAll(): List<Account>
    fun findByAccountNumber(accountNumber: Int): Account?

}