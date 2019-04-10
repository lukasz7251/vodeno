package com.vodeno.recruitmenttask.transaction.service

import java.math.BigDecimal

interface TransactionService {
    fun transferMoney(amount: BigDecimal, senderAccountNumber: Int, receiverAccountNumber: Int)
}