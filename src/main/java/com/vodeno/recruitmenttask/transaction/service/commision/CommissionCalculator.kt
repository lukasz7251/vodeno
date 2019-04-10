package com.vodeno.recruitmenttask.transaction.service.commision

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import java.math.BigDecimal

interface CommissionCalculator {

    fun calculate(amount: BigDecimal, sender: AccountEntity, receiver: AccountEntity): BigDecimal

}