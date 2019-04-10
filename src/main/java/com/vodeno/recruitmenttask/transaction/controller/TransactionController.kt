package com.vodeno.recruitmenttask.transaction.controller

import com.vodeno.recruitmenttask.transaction.service.TransactionService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/transactions")
class TransactionController(
        private val transactionService: TransactionService
) {

    @RequestMapping(method = [RequestMethod.POST])
    fun make(@RequestBody request: TransactionRequest) {
        transactionService.transferMoney(
                receiverAccountNumber = request.receiverAccountNumber,
                senderAccountNumber = request.senderAccountNumber,
                amount = request.amount
        )
    }

}

data class TransactionRequest(
        val senderAccountNumber: Int,
        val receiverAccountNumber: Int,
        val amount: BigDecimal
)