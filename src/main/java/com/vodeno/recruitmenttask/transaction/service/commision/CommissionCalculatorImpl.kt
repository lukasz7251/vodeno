package com.vodeno.recruitmenttask.transaction.service.commision

import com.vodeno.recruitmenttask.account.db.entity.AccountEntity
import com.vodeno.recruitmenttask.transaction.db.repository.TransactionRepository
import com.vodeno.recruitmenttask.transaction.service.billingmonth.BillingMonthCalculator
import com.vodeno.recruitmenttask.user.db.entity.UserEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

@Service
@Transactional
class CommissionCalculatorImpl(
        private val transactionRepository: TransactionRepository,
        private val billingMonthCalculator: BillingMonthCalculator
) : CommissionCalculator {

    private val noCommission = BigDecimal("0.00")
    private val standardCommissionMultiplicand = BigDecimal("0.02")
    private val maxFreeTransferCount = 5

    override fun calculate(amount: BigDecimal, sender: AccountEntity, receiver: AccountEntity): BigDecimal {
        require(sender.id != receiver.id) { "Sender and receiver accounts must be different" }
        return when {
            isSameUser(sender, receiver) -> noCommission
            hasFreeTransferAvailable(sender.user) -> noCommission
            else -> standardCommission(amount)
        }
    }

    private fun isSameUser(sender: AccountEntity, receiver: AccountEntity) =
            sender.user.id == receiver.user.id

    private fun hasFreeTransferAvailable(user: UserEntity): Boolean {
        val currentBillingMonthStartDate = billingMonthCalculator.calculateStartOfCurrent(user.createDate)
        val freeTransfersInThisBillingMonth = transactionRepository.countCommissionFreeInThisBillingMonthWhereReceiverIsDifferentUser(user, currentBillingMonthStartDate)
        return freeTransfersInThisBillingMonth < maxFreeTransferCount
    }

    private fun standardCommission(amount: BigDecimal): BigDecimal =
            amount.multiply(standardCommissionMultiplicand, MathContext.DECIMAL64)
                    .setScale(2, RoundingMode.HALF_UP)

}