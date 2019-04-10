package com.vodeno.recruitmenttask.deposit.service

import java.math.BigDecimal

interface DepositService {
    fun depositMoney(amount: BigDecimal, accountNumber: Int)
}